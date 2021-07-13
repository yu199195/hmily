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

package org.dromara.hmily.tac.sqlparser.model.common.constant;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Paren of SQL.
 */
@RequiredArgsConstructor
@Getter
public enum HmilyParen {
    
    PARENTHESES('(', ')'), BRACKET('[', ']'), BRACES('{', '}');
    
    private final char leftParen;
    
    private final char rightParen;
    
    /**
     * Judge passed token is left paren or not.
     * 
     * @param token token
     * @return is left paren or not
     */
    public static boolean isLeftParen(final char token) {
        return Arrays.stream(values()).anyMatch(each -> each.leftParen == token);
    }
    
    /**
     * Judge left paren match right paren or not.
     * 
     * @param leftToken left token
     * @param rightToken right token
     * @return match or not
     */
    public static boolean match(final char leftToken, final char rightToken) {
        for (HmilyParen each : HmilyParen.values()) {
            if (each.leftParen == leftToken && each.rightParen == rightToken) {
                return true;
            }
        }
        return false;
    }
}