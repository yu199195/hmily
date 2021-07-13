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

package org.dromara.hmily.tac.sqlparser.model.common.segment.dml.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dromara.hmily.tac.sqlparser.model.common.segment.HmilySegment;
import org.dromara.hmily.tac.sqlparser.model.common.segment.dml.order.item.HmilyOrderByItemSegment;

import java.util.Collection;

/**
 * Group by segment.
 */
@RequiredArgsConstructor
@Getter
public final class HmilyGroupBySegment implements HmilySegment {
    
    private final int startIndex;
    
    private final int stopIndex;
    
    private final Collection<HmilyOrderByItemSegment> groupByItems;
}