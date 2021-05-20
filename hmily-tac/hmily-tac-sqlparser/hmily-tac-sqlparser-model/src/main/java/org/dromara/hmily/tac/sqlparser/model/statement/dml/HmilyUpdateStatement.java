/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dromara.hmily.tac.sqlparser.model.statement.dml;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.dromara.hmily.tac.sqlparser.model.segment.dml.assignment.HmilySetAssignmentSegment;
import org.dromara.hmily.tac.sqlparser.model.segment.dml.predicate.HmilyWhereSegment;
import org.dromara.hmily.tac.sqlparser.model.segment.generic.table.HmilyTableSegment;
import org.dromara.hmily.tac.sqlparser.model.statement.AbstractHmilyStatement;

import java.util.Optional;

/**
 * Update statement.
 */
@Getter
@Setter
@ToString
public abstract class HmilyUpdateStatement extends AbstractHmilyStatement implements HmilyDMLStatement {
    
    private HmilyTableSegment tableSegment;
    
    private HmilySetAssignmentSegment setAssignment;
    
    private HmilyWhereSegment where;
    
    /**
     * Get where.
     * 
     * @return where segment
     */
    public Optional<HmilyWhereSegment> getWhere() {
        return Optional.ofNullable(where);
    }
}
