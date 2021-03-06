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

/**
 * TransactionContext .
 *
 * @author sixh chenbin
 */
public class TransactionContext {

    private Coordinator coordinator;

    private final XidImpl xId;

    private Finally oneFinally;

    /**
     * Instantiates a new Transaction context.
     *
     * @param coordinator the coordinator
     * @param xId         the x id
     */
    public TransactionContext(final Coordinator coordinator, final XidImpl xId) {
        this.coordinator = coordinator;
        this.xId = xId;
        if (coordinator != null) {
            oneFinally = coordinator;
        }
    }

    /**
     * Gets coordinator.
     *
     * @return the coordinator
     */
    public Coordinator getCoordinator() {
        return coordinator;
    }

    /**
     * Gets one finally.
     *
     * @return the one finally
     */
    public Finally getOneFinally() {
        return oneFinally;
    }

    /**
     * Sets one finally.
     *
     * @param oneFinally the one finally
     */
    public void setOneFinally(final Finally oneFinally) {
        this.oneFinally = oneFinally;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public XidImpl getXid() {
        return xId;
    }

    /**
     * Sets coordinator.
     *
     * @param coordinator the coordinator
     */
    public void setCoordinator(final Coordinator coordinator) {
        this.coordinator = coordinator;
    }
}
