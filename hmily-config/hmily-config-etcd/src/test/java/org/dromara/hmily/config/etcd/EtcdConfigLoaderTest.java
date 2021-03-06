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

package org.dromara.hmily.config.etcd;

import org.dromara.hmily.common.utils.FileUtils;
import org.dromara.hmily.common.utils.StringUtils;
import org.dromara.hmily.config.api.ConfigEnv;
import org.dromara.hmily.config.api.ConfigScan;
import org.dromara.hmily.config.api.entity.*;
import org.dromara.hmily.config.loader.ConfigLoader;
import org.dromara.hmily.config.loader.ServerConfigLoader;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.util.function.Supplier;

import static org.mockito.ArgumentMatchers.any;

/**
 * The type etcd config loader test.
 *
 * @author xiaoyu
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(EtcdClient.class)
public class EtcdConfigLoaderTest {
    
    private EtcdClient client = PowerMockito.mock(EtcdClient.class);
    
    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        String str = FileUtils.readYAML("hmily-etcd.yml");
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(str.getBytes());
        try {
            PowerMockito.when(client.pull(any())).thenReturn(byteArrayInputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test etcd load.
     */
    @Test
    public void testEtcdLoad() {
        ConfigScan.scan();
        ServerConfigLoader loader = new ServerConfigLoader();
        EtcdConfigLoader etcdConfigLoader = new EtcdConfigLoader(client);
        loader.load(ConfigLoader.Context::new, (context, config) -> {
            if (config != null) {
                if (StringUtils.isNoneBlank(config.getConfigMode())) {
                    String configMode = config.getConfigMode();
                    if (configMode.equals("etcd")) {
                        etcdConfigLoader.load(context, this::assertTest);
                    }
                }
            }
        });
    }

    private void assertTest(final Supplier<ConfigLoader.Context> supplier, final EtcdConfig etcdConfig) {
        Assert.assertNotNull(etcdConfig);
        Assert.assertEquals(etcdConfig.prefix(), "remote.etcd");
        Assert.assertEquals(etcdConfig.getTimeoutMs(), 6000);
        HmilyServer server = ConfigEnv.getInstance().getConfig(HmilyServer.class);
        Assert.assertNotNull(server);
        Assert.assertEquals(server.getConfigMode(), "etcd");
        HmilyConfig config = ConfigEnv.getInstance().getConfig(HmilyConfig.class);
        Assert.assertNotNull(config);
        Assert.assertEquals(config.getAppName(), "xiaoyu");
        HmilyDatabaseConfig databaseConfig = ConfigEnv.getInstance().getConfig(HmilyDatabaseConfig.class);
        Assert.assertNotNull(databaseConfig);
        HmilyFileConfig fileConfig = ConfigEnv.getInstance().getConfig(HmilyFileConfig.class);
        Assert.assertNotNull(fileConfig);
        HmilyMetricsConfig metricsConfig = ConfigEnv.getInstance().getConfig(HmilyMetricsConfig.class);
        Assert.assertNotNull(metricsConfig);
        HmilyMongoConfig mongoConfig = ConfigEnv.getInstance().getConfig(HmilyMongoConfig.class);
        Assert.assertNotNull(mongoConfig);
        HmilyRedisConfig redisConfig = ConfigEnv.getInstance().getConfig(HmilyRedisConfig.class);
        Assert.assertNotNull(redisConfig);
        HmilyZookeeperConfig hmilyZookeeperConfig = ConfigEnv.getInstance().getConfig(HmilyZookeeperConfig.class);
        Assert.assertNotNull(hmilyZookeeperConfig);
    }
    
}
