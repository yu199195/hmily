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

import com.alibaba.ttl.TransmittableThreadLocal;
import org.dromara.hmily.spi.HmilySPI;

/**
 * The type Transmittable thread local hmily context.
 *
 * @author xiaoyu
 */
@HmilySPI("transmittable")
public class TransmittableThreadLocalHmilyContext implements HmilyContext {
    
    private static final TransmittableThreadLocal<HmilyTransactionContext> LOCAL = new TransmittableThreadLocal<>();
    
    @Override
    public void set(final HmilyTransactionContext context) {
        LOCAL.set(context);
    }
    
    @Override
    public HmilyTransactionContext get() {
        return LOCAL.get();
    }
    
    @Override
    public void remove() {
        LOCAL.remove();
    }
}
