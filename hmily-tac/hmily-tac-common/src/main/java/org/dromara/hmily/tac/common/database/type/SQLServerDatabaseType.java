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

package org.dromara.hmily.tac.common.database.type;

import java.util.Collection;
import java.util.Collections;
import org.dromara.hmily.spi.HmilySPI;
import org.dromara.hmily.tac.common.constants.DatabaseConstant;
import org.dromara.hmily.tac.common.database.metadata.SQLServerDataSourceMetaData;

/**
 * Database type of SQLServer.
 */
@HmilySPI(DatabaseConstant.SQL_SERVER)
public final class SQLServerDatabaseType implements DatabaseType {
    
    @Override
    public String getName() {
        return DatabaseConstant.SQL_SERVER;
    }
    
    @Override
    public Collection<String> getJdbcUrlPrefixes() {
        return Collections.singletonList("jdbc:microsoft:sqlserver:");
    }
    
    @Override
    public SQLServerDataSourceMetaData getDataSourceMetaData(final String url, final String username) {
        return new SQLServerDataSourceMetaData(url);
    }
}
