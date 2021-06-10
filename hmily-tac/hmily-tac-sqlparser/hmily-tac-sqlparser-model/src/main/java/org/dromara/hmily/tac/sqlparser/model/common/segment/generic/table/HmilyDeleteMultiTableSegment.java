/*
 * Copyright 2017-2021 Dromara.org

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
import lombok.Setter;
import org.dromara.hmily.tac.sqlparser.model.common.segment.generic.HmilyAliasSegment;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Setter
@Getter
public final class HmilyDeleteMultiTableSegment implements HmilyTableSegment {
    
    private int startIndex;
    
    private int stopIndex;
    
    private List<HmilySimpleTableSegment> actualDeleteTables = new LinkedList<>();
    
    private HmilyTableSegment relationTable;
    
    @Override
    public Optional<String> getAlias() {
        return Optional.empty();
    }
    
    @Override
    public void setAlias(final HmilyAliasSegment alias) {
    
    }
}
