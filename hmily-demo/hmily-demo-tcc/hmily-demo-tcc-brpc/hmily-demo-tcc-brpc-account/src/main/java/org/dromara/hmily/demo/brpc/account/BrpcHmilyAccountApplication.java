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

package org.dromara.hmily.demo.brpc.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * The BrpcHmilyAccountApplication.
 *
 * @author liu·yu
 */
@SpringBootApplication
@MapperScan("org.dromara.hmily.demo.common.account.mapper")
public class BrpcHmilyAccountApplication {

    /**
     * main.
     *
     * @param args args.
     */
    public static void main(final String[] args) {
        SpringApplication springApplication = new SpringApplication(BrpcHmilyAccountApplication.class);
        springApplication.setWebApplicationType(WebApplicationType.NONE);
        springApplication.run(args);
        synchronized (BrpcHmilyAccountApplication.class){
            try {
                BrpcHmilyAccountApplication.class.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
