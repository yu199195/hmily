/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.dromara.hmily.xa.core;

import javax.transaction.xa.Xid;
import java.util.Vector;

/**
 * Coordinator .
 *
 * @author sixh chenbin
 */
public class Coordinator implements Mock {

    /**
     * all SubCoordinator.
     */
    private Vector<SubCoordinator> coordinators = new Vector<>();

    private XIdImpl xid;

    public Coordinator(XIdImpl xid) {
        this.xid = xid;
    }

    @Override
    public int prepare() {
        return 0;
    }

    @Override
    public void rollback() {

    }

    @Override
    public void commit() {

    }

    public synchronized boolean addCoordinators(SubCoordinator subCoordinator) {
        if (coordinators.contains(subCoordinator)) {
            return true;
        }
        return this.coordinators.add(subCoordinator);
    }

    public XIdImpl getSubXid() {
        return this.xid.newBranchId();
    }
}
