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

package org.dromara.hmily.tac.sqlcompute.impl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.dromara.hmily.tac.sqlparser.model.common.segment.dml.column.HmilyColumnSegment;
import org.dromara.hmily.tac.sqlparser.model.common.segment.dml.expr.HmilyBinaryOperationExpression;
import org.dromara.hmily.tac.sqlparser.model.common.segment.dml.expr.HmilyExpressionSegment;
import org.dromara.hmily.tac.sqlparser.model.common.segment.dml.expr.complex.HmilyCommonExpressionSegment;
import org.dromara.hmily.tac.sqlparser.model.common.segment.dml.expr.simple.HmilyLiteralExpressionSegment;
import org.dromara.hmily.tac.sqlparser.model.common.segment.dml.expr.simple.HmilyParameterMarkerExpressionSegment;
import org.dromara.hmily.tac.sqlparser.model.common.segment.dml.item.HmilyExpressionProjectionSegment;

import java.util.List;

/**
 * Expression utility.
 *
 * @author zhaojun
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExpressionHandler {
    
    /**
     * Get expression value.
     * 
     * @param parameters SQL parameters
     * @param expressionSegment expression segment
     * @return expression value
     */
    public static Object getValue(final List<Object> parameters, final HmilyExpressionSegment expressionSegment) {
        if (expressionSegment instanceof HmilyCommonExpressionSegment) {
            String value = ((HmilyCommonExpressionSegment) expressionSegment).getText();
            return "null".equals(value) ? null : value;
        }
        if (expressionSegment instanceof HmilyParameterMarkerExpressionSegment) {
            return parameters.get(((HmilyParameterMarkerExpressionSegment) expressionSegment).getParameterMarkerIndex());
        }
        if (expressionSegment instanceof HmilyExpressionProjectionSegment) {
            String value = ((HmilyExpressionProjectionSegment) expressionSegment).getText();
            return "null".equals(value) ? null : value;
        }
        if (expressionSegment instanceof HmilyBinaryOperationExpression) {
            Object left = getValue(parameters, ((HmilyBinaryOperationExpression) expressionSegment).getLeft());
            Object right = getValue(parameters, ((HmilyBinaryOperationExpression) expressionSegment).getRight());
            return String.format("%s %s %s", left, ((HmilyBinaryOperationExpression) expressionSegment).getOperator(), right);
        }
        if (expressionSegment instanceof HmilyColumnSegment) {
            return ((HmilyColumnSegment) expressionSegment).getQualifiedName();
        }
        // TODO match result type with metadata
        return ((HmilyLiteralExpressionSegment) expressionSegment).getLiterals();
    }
}
