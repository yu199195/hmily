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

import com.google.common.base.Joiner;
import lombok.RequiredArgsConstructor;
import org.dromara.hmily.repository.spi.entity.tuple.HmilySQLManipulation;
import org.dromara.hmily.repository.spi.entity.tuple.HmilySQLTuple;
import org.dromara.hmily.tac.metadata.HmilyMetaDataManager;
import org.dromara.hmily.tac.metadata.model.TableMetaData;
import org.dromara.hmily.tac.sqlparser.model.common.segment.dml.expr.HmilyBinaryOperationExpression;
import org.dromara.hmily.tac.sqlparser.model.common.segment.dml.expr.HmilyExpressionSegment;
import org.dromara.hmily.tac.sqlparser.model.common.segment.dml.expr.simple.HmilyParameterMarkerExpressionSegment;
import org.dromara.hmily.tac.sqlparser.model.common.segment.generic.table.HmilySimpleTableSegment;
import org.dromara.hmily.tac.sqlparser.model.dialect.mysql.dml.HmilyMySQLUpdateStatement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Hmily update SQL compute engine.
 *
 * @author zhaojun
 */
@RequiredArgsConstructor
public final class HmilyUpdateSQLComputeEngine extends AbstractHmilySQLComputeEngine {
    
    private static final String DERIVED_COLUMN = "_DERIVED";
    
    private final HmilyMySQLUpdateStatement sqlStatement;
    
    @Override
    Collection<HmilySQLTuple> createTuples(final String sql, final List<Object> parameters, final Connection connection, final String resourceId) throws SQLException {
        Collection<HmilySQLTuple> result = new LinkedList<>();
        HmilySimpleTableSegment tableSegment = (HmilySimpleTableSegment) sqlStatement.getTableSegment();
        String tableName = sql.substring(tableSegment.getStartIndex(), tableSegment.getStopIndex() + 1);
        String selectSQL = String.format("SELECT %s FROM %s %s", Joiner.on(", ").join(getSelectItems(parameters, tableSegment, tableName)), tableName, getWhereCondition(sql));
        Collection<Map<String, Object>> records = HmilySQLComputeUtils.executeQuery(connection, selectSQL, getWhereParameters(parameters));
        result.addAll(doConvert(records, HmilyMetaDataManager.get(resourceId).getTableMetaDataMap().get(tableSegment.getTableName().getIdentifier().getValue())));
        return result;
    }
    
    private List<String> getSelectItems(final List<Object> parameters, final HmilySimpleTableSegment tableSegment, final String tableName) {
        List<String> result = new LinkedList<>();
        result.add(HmilySQLComputeUtils.getAllColumns(tableSegment, tableName));
        sqlStatement.getSetAssignment().getAssignments().forEach(assignment -> result.add(
            String.format("%s AS %s", ExpressionHandler.getValue(parameters, assignment.getValue()), assignment.getColumn().getIdentifier().getValue() + DERIVED_COLUMN)));
        return result;
    }
    
    private String getWhereCondition(final String sql) {
        return sqlStatement.getWhere().map(segment -> sql.substring(segment.getStartIndex(), segment.getStopIndex() + 1)).orElse("");
    }
    
    private List<Object> getWhereParameters(final List<Object> parameters) {
        List<Object> result = new LinkedList<>();
        sqlStatement.getWhere().ifPresent(whereSegment -> {
            HmilyExpressionSegment hmilyExpressionSegment = whereSegment.getExpr();
            getParameters(hmilyExpressionSegment, parameters, result);
        });
        return result;
    }
    
    private void getParameters(final HmilyExpressionSegment hmilyExpressionSegment, final List<Object> parameters, final List<Object> result) {
        if (hmilyExpressionSegment instanceof HmilyParameterMarkerExpressionSegment) {
            int parameterMarkerIndex = ((HmilyParameterMarkerExpressionSegment) hmilyExpressionSegment).getParameterMarkerIndex();
            result.add(parameters.get(parameterMarkerIndex));
            return;
        }
        if (hmilyExpressionSegment instanceof HmilyBinaryOperationExpression) {
            getParameters(((HmilyBinaryOperationExpression) hmilyExpressionSegment).getLeft(), parameters, result);
            getParameters(((HmilyBinaryOperationExpression) hmilyExpressionSegment).getRight(), parameters, result);
        }
    }
    
    private Collection<HmilySQLTuple> doConvert(final Collection<Map<String, Object>> records, final TableMetaData tableMetaData) {
        Collection<HmilySQLTuple> result = new LinkedList<>();
        for (Map<String, Object> record : records) {
            List<Object> primaryKeyValues = new LinkedList<>();
            Map<String, Object> before = new LinkedHashMap<>();
            Map<String, Object> modified = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : record.entrySet()) {
                if (entry.getKey().contains(DERIVED_COLUMN)) {
                    String key = entry.getKey().replace(DERIVED_COLUMN, "");
                    if (tableMetaData.getColumns().get(key).getDataType() == Types.DATE
                        || tableMetaData.getColumns().get(key).getDataType() == Types.TIME
                        || tableMetaData.getColumns().get(key).getDataType() == Types.TIMESTAMP) {
                        continue;
                    }
                    modified.put(key, entry.getValue());
                } else if (tableMetaData.getPrimaryKeyColumns().contains(entry.getKey())) {
                    modified.put(entry.getKey(), entry.getValue());
                    primaryKeyValues.add(entry.getValue());
                } else {
                    before.put(entry.getKey(), entry.getValue());
                }
            }
            result.add(buildTuple(tableMetaData.getTableName(), HmilySQLManipulation.UPDATE, primaryKeyValues, before, modified));
        }
        return result;
    }
}
