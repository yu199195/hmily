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

package org.dromara.hmily.tac.sqlparser.model.common.constant;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.TreeSet;

/**
 * Logical operator.
 */
public enum HmilyLogicalOperator {
    
    AND("AND", "&&"), 
    OR("OR", "||");
    
    private final Collection<String> texts = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    
    HmilyLogicalOperator(final String... texts) {
        this.texts.addAll(Arrays.asList(texts));
    }
    
    /**
     * Get logical operator value from text.
     *
     * @param text text
     * @return logical operator value
     */
    public static Optional<HmilyLogicalOperator> valueFrom(final String text) {
        return Arrays.stream(values()).filter(each -> each.texts.contains(text)).findFirst();
    }
}
