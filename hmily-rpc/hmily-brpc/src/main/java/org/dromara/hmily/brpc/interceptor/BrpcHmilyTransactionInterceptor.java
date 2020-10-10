package org.dromara.hmily.brpc.interceptor;

import java.lang.reflect.Type;

import com.baidu.brpc.RpcContext;
import org.dromara.hmily.common.utils.IdWorkerUtils;
import org.dromara.hmily.repository.spi.entity.HmilyInvocation;

import com.baidu.brpc.exceptions.RpcException;
import com.baidu.brpc.interceptor.AbstractInterceptor;
import com.baidu.brpc.interceptor.InterceptorChain;
import com.baidu.brpc.protocol.Request;
import com.baidu.brpc.protocol.Response;
import org.dromara.hmily.annotation.Hmily;
import org.dromara.hmily.common.enums.HmilyActionEnum;
import org.dromara.hmily.common.enums.HmilyRoleEnum;
import org.dromara.hmily.common.exception.HmilyRuntimeException;
import org.dromara.hmily.common.utils.LogUtil;
import org.dromara.hmily.core.context.HmilyContextHolder;
import org.dromara.hmily.core.context.HmilyTransactionContext;
import org.dromara.hmily.core.holder.HmilyTransactionHolder;
import org.dromara.hmily.core.mediator.RpcMediator;
import org.dromara.hmily.repository.spi.entity.HmilyParticipant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;

/**
 * Create by liu·yu
 * Date is 2020-09-25
 * Description is : brpc 拦截器
 */
public class BrpcHmilyTransactionInterceptor extends AbstractInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(BrpcHmilyTransactionInterceptor.class);

    @Override
    public void aroundProcess(final Request request, final Response response, final InterceptorChain chain) throws RpcException {
        final HmilyTransactionContext context = HmilyContextHolder.get();
        if (Objects.isNull(context)) {
            chain.intercept(request, response);
            return;
        }
        Method method = request.getRpcMethodInfo().getMethod();
        try {
            Hmily hmily = method.getAnnotation(Hmily.class);
            if (Objects.isNull(hmily)) {
                chain.intercept(request, response);
                return;
            }
        } catch (Exception ex) {
            LogUtil.error(LOGGER, "hmily find method error {} ", ex::getMessage);
            chain.intercept(request, response);
            return;
        }
        Long participantId = context.getParticipantId();
        HmilyParticipant hmilyParticipant = buildParticipant(context, request);
        Optional.ofNullable(hmilyParticipant).ifPresent(participant -> context.setParticipantId(participant.getParticipantId()));
        if (context.getRole() == HmilyRoleEnum.PARTICIPANT.getCode()) {
            context.setParticipantRefId(participantId);
        }
        RpcMediator.getInstance().transmit(RpcContext.getContext()::setRequestKvAttachment, context);
        if (request.getKvAttachment() == null) {
            request.setKvAttachment(RpcContext.getContext().getRequestKvAttachment());
        } else {
            request.getKvAttachment().putAll(RpcContext.getContext().getRequestKvAttachment());
        }
        try {
            chain.intercept(request, response);
            if (context.getRole() == HmilyRoleEnum.PARTICIPANT.getCode()) {
                HmilyTransactionHolder.getInstance().registerParticipantByNested(participantId, hmilyParticipant);
            } else {
                HmilyTransactionHolder.getInstance().registerStarterParticipant(hmilyParticipant);
            }
        } catch (Exception e) {
            throw new HmilyRuntimeException("rpc invoke exception{}", e);
        }
    }

    private HmilyParticipant buildParticipant(HmilyTransactionContext context, Request request) {
        if (HmilyActionEnum.TRYING.getCode() != context.getAction()) {
            return null;
        }
        HmilyParticipant hmilyParticipant = new HmilyParticipant();
        hmilyParticipant.setParticipantId(IdWorkerUtils.getInstance().createUUID());
        hmilyParticipant.setTransId(context.getTransId());
        hmilyParticipant.setTransType(context.getTransType());

        Class<?> clazz = request.getRpcMethodInfo().getMethod().getDeclaringClass();
        String methodName = request.getRpcMethodInfo().getMethodName();
        Class[] converter = converterParamsClass(request.getRpcMethodInfo().getInputClasses());
        Object[] args = request.getArgs();

        HmilyInvocation invocation = new HmilyInvocation(clazz, methodName, converter, args);

        hmilyParticipant.setConfirmHmilyInvocation(invocation);
        hmilyParticipant.setCancelHmilyInvocation(invocation);

        return hmilyParticipant;
    }

    private Class[] converterParamsClass(Type[] types) {
        Class[] classes = new Class[types.length];
        for (int i = 0; i < types.length; i++) {
            classes[i] = (Class) types[i];
        }
        return classes;
    }

}
