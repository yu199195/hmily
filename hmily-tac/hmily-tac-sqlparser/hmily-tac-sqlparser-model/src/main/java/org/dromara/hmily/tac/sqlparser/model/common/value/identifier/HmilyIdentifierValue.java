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

package org.dromara.hmily.tac.sqlparser.model.common.value.identifier;

import lombok.Getter;
import org.dromara.hmily.tac.sqlparser.model.common.constant.HmilyQuoteCharacter;
import org.dromara.hmily.tac.sqlparser.model.common.util.HmilySQLUtil;
import org.dromara.hmily.tac.sqlparser.model.common.value.HmilyValueASTNode;

/**
 * Identifier value.
 */
@Getter
public final class HmilyIdentifierValue implements HmilyValueASTNode<String> {
    
    private final String value;
    
    private final HmilyQuoteCharacter hmilyQuoteCharacter;
    
    public HmilyIdentifierValue(final String text) {
        value = HmilySQLUtil.getExactlyValue(text);
        hmilyQuoteCharacter = HmilyQuoteCharacter.getQuoteCharacter(text);
    }

    /**
     * Get value with quote characters, i.e. `table1` or `field1`
     *
     * @return value with quote characters
     */
    public String getValueWithQuoteCharacters() {
        return null == value ? "" : hmilyQuoteCharacter.wrap(value);
    }
}
