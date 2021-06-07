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

package org.dromara.hmily.tac.sqlparser.model.common.segment.dml.predicate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.dromara.hmily.tac.sqlparser.model.common.segment.HmilySegment;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Or predicate segment.
 */
@RequiredArgsConstructor
@Getter
public final class HmilyOrPredicateSegment implements HmilySegment {
    
    private final int startIndex = 0;
    
    private final int stopIndex = 0;
    
    private final Collection<HmilyAndPredicate> hmilyAndPredicates = new LinkedList<>();
}
