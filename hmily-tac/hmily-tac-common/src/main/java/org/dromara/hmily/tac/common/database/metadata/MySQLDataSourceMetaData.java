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

package org.dromara.hmily.tac.common.database.metadata;

import com.google.common.base.Strings;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Getter;
import org.dromara.hmily.tac.common.database.UnrecognizedDatabaseURLException;

/**
 * Data source meta data for MySQL.
 */
@Getter
public final class MySQLDataSourceMetaData implements DataSourceMetaData {
    
    private static final int DEFAULT_PORT = 3306;
    
    private final String hostName;
    
    private final int port;
    
    private final String catalog;
    
    private final String schema;
    
    private final Pattern pattern = Pattern.compile("jdbc:(mysql|mysqlx)(:loadbalance|:replication)?:(\\w*:)?//([\\w\\-\\.]+):?([0-9]*),?.*?/([\\w\\-]+);?\\S*", Pattern.CASE_INSENSITIVE);
    
    public MySQLDataSourceMetaData(final String url) {
        Matcher matcher = pattern.matcher(url);
        if (!matcher.find()) {
            throw new UnrecognizedDatabaseURLException(url, pattern.pattern());
        }
        hostName = matcher.group(4);
        port = Strings.isNullOrEmpty(matcher.group(5)) ? DEFAULT_PORT : Integer.parseInt(matcher.group(5));
        catalog = matcher.group(6);
        schema = null;
    }
}
