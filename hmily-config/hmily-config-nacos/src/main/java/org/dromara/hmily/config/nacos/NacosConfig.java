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

package org.dromara.hmily.config.nacos;

import lombok.Data;
import org.dromara.hmily.config.api.AbstractConfig;
import org.dromara.hmily.config.api.constant.PrefixConstants;
import org.dromara.hmily.spi.HmilySPI;

/**
 * NacosConfig .
 *
 * @author xiaoyu
 */
@Data
@HmilySPI("remoteNacos")
public class NacosConfig extends AbstractConfig {

    private String server;

    private String dataId;

    private String group;

    private long timeoutMs = 5000;

    private String fileExtension;

    @Override
    public String prefix() {
        return PrefixConstants.REMOTE_NACOS;
    }

    /**
     * File name string.
     *
     * @return the string
     */
    public String fileName() {
        return dataId + "." + fileExtension;
    }
}
