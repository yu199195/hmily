/*
 * Copyright 2017-2021 Dromara.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.hmily.core.mediator;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.dromara.hmily.common.constant.CommonConstant;
import org.dromara.hmily.common.utils.GsonUtils;
import org.dromara.hmily.common.utils.StringUtils;
import org.dromara.hmily.core.context.HmilyTransactionContext;

/**
 * The type RpcMediator.
 *
 * @author xiaoyu(Myth)
 */
public class RpcMediator {

    private static final RpcMediator INSTANCE = new RpcMediator();

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static RpcMediator getInstance() {
        return INSTANCE;
    }

    /**
     * Transmit.
     *
     * @param rpcTransmit the rpc mediator
     * @param context     the context
     */
    public void transmit(final RpcTransmit rpcTransmit, final HmilyTransactionContext context) {
        if (Objects.nonNull(context)) {
            rpcTransmit.transmit(CommonConstant.HMILY_TRANSACTION_CONTEXT, GsonUtils.getInstance().toJson(context));
        }
    }

    /**
     * Transmit.
     *
     * @param <T>         the type parameter
     * @param rpcTransmit the rpc transmit
     * @param context     the context
     */
    public <T> void transmit(final RpcTransmit rpcTransmit, final T context) {
        if (Objects.nonNull(context)) {
            rpcTransmit.transmit(CommonConstant.HMILY_TRANSACTION_CONTEXT, GsonUtils.getInstance().toJson(context));
        }
    }

    /**
     * Acquire hmily transaction context.
     *
     * @param rpcAcquire the rpc acquire
     * @return the hmily transaction context
     */
    public HmilyTransactionContext acquire(final RpcAcquire rpcAcquire) {
        return acquire(rpcAcquire, HmilyTransactionContext.class);
    }

    /**
     * Acquire hmily transaction context.
     *
     * @param <T>        the type parameter
     * @param rpcAcquire the rpc acquire
     * @param clazz      the clazz
     * @return the hmily transaction context
     */
    public <T> T acquire(final RpcAcquire rpcAcquire, final Class<T> clazz) {
        T hmilyTransactionContext = null;
        final String context = rpcAcquire.acquire(CommonConstant.HMILY_TRANSACTION_CONTEXT);
        if (StringUtils.isNoneBlank(context)) {
            hmilyTransactionContext = GsonUtils.getInstance().fromJson(context, clazz);
        }
        return hmilyTransactionContext;
    }

    /**
     * Gets and set.
     *
     * @param function   the function
     * @param biConsumer the bi consumer
     */
    public void getAndSet(final Function<String, Object> function, final BiConsumer<String, Object> biConsumer) {
        Object result = function.apply(CommonConstant.HMILY_TRANSACTION_CONTEXT);
        if (Objects.nonNull(result)) {
            biConsumer.accept(CommonConstant.HMILY_TRANSACTION_CONTEXT, result);
        }
    }
}
