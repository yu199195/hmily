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

package org.dromara.hmily.core.disruptor;

import com.lmax.disruptor.WorkHandler;
import org.dromara.hmily.core.disruptor.event.DataEvent;

/**
 * Hmily disruptor work handler.
 *
 * @author chenbin sixh
 */
public class HmilyDisruptorWorkHandler<T> implements WorkHandler<DataEvent<T>> {

    private HmilyDisruptorConsumer<T> consumer;

    HmilyDisruptorWorkHandler(final HmilyDisruptorConsumer<T> consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onEvent(final DataEvent<T> t) {
        if (t != null) {
            consumer.execute(t.getT());
        }
    }
}
