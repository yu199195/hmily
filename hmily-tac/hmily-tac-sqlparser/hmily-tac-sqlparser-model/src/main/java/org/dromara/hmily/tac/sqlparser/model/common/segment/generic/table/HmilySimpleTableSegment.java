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

package org.dromara.hmily.tac.sqlparser.model.common.segment.generic.table;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.dromara.hmily.tac.sqlparser.model.common.segment.generic.HmilyAliasAvailable;
import org.dromara.hmily.tac.sqlparser.model.common.segment.generic.HmilyAliasSegment;
import org.dromara.hmily.tac.sqlparser.model.common.segment.generic.HmilyOwnerAvailable;
import org.dromara.hmily.tac.sqlparser.model.common.segment.generic.HmilyOwnerSegment;
import org.dromara.hmily.tac.sqlparser.model.common.value.identifier.HmilyIdentifierValue;

import java.util.Optional;

/**
 * Simple table segment.
 */
@RequiredArgsConstructor
@Getter
@ToString
public final class HmilySimpleTableSegment implements HmilyTableSegment, HmilyOwnerAvailable, HmilyAliasAvailable {
    
    private final HmilyTableNameSegment tableName;
    
    @Setter
    private HmilyOwnerSegment owner;
    
    @Setter
    private HmilyAliasSegment alias;
    
    public HmilySimpleTableSegment(final int startIndex, final int stopIndex, final HmilyIdentifierValue hmilyIdentifierValue) {
        tableName = new HmilyTableNameSegment(startIndex, stopIndex, hmilyIdentifierValue);
    }
    
    @Override
    public int getStartIndex() {
        return null == owner ? tableName.getStartIndex() : owner.getStartIndex(); 
    }
    
    @Override
    public int getStopIndex() {
        return null == alias ? tableName.getStopIndex() : alias.getStopIndex();
    }
    
    @Override
    public Optional<HmilyOwnerSegment> getOwner() {
        return Optional.ofNullable(owner);
    }
    
    @Override
    public Optional<String> getAlias() {
        return null == alias ? Optional.empty() : Optional.ofNullable(alias.getIdentifier().getValue());
    }
}
