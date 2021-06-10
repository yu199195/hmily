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

package org.dromara.hmily.core.context;

import org.dromara.hmily.spi.HmilySPI;

/**
 * The type Thread local hmily context.
 *
 * @author xiaoyu
 */
@HmilySPI("threadLocal")
public final class ThreadLocalHmilyContext implements HmilyContext {
    
    private static final ThreadLocal<HmilyTransactionContext> CURRENT_LOCAL = new ThreadLocal<>();
    
    /**
     * set value.
     *
     * @param context context
     */
    public void set(final HmilyTransactionContext context) {
        CURRENT_LOCAL.set(context);
    }
    
    /**
     * get value.
     *
     * @return TccTransactionContext
     */
    public HmilyTransactionContext get() {
        return CURRENT_LOCAL.get();
    }
    
    /**
     * clean threadLocal for gc.
     */
    public void remove() {
        CURRENT_LOCAL.remove();
    }
}
