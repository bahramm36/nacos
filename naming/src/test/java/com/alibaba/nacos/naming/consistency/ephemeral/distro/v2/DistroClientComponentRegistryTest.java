/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.nacos.naming.consistency.ephemeral.distro.v2;

import com.alibaba.nacos.core.cluster.ServerMemberManager;
import com.alibaba.nacos.core.cluster.remote.ClusterRpcClientProxy;
import com.alibaba.nacos.core.distributed.distro.DistroProtocol;
import com.alibaba.nacos.core.distributed.distro.component.DistroComponentHolder;
import com.alibaba.nacos.core.distributed.distro.component.DistroDataProcessor;
import com.alibaba.nacos.core.distributed.distro.component.DistroDataStorage;
import com.alibaba.nacos.core.distributed.distro.component.DistroFailedTaskHandler;
import com.alibaba.nacos.core.distributed.distro.component.DistroTransportAgent;
import com.alibaba.nacos.core.distributed.distro.task.DistroTaskEngineHolder;
import com.alibaba.nacos.naming.core.v2.client.manager.ClientManagerDelegate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class DistroClientComponentRegistryTest {
    
    private DistroClientComponentRegistry distroClientComponentRegistry;
    
    @Mock
    private ServerMemberManager serverMemberManager;
    
    @Mock
    private DistroProtocol distroProtocol;
    
    @Mock
    private DistroTaskEngineHolder taskEngineHolder;
    
    @Mock
    private ClientManagerDelegate clientManager;
    
    @Mock
    private ClusterRpcClientProxy clusterRpcClientProxy;
    
    private DistroComponentHolder componentHolder;
    
    @BeforeEach
    void setUp() throws Exception {
        componentHolder = new DistroComponentHolder();
        
        distroClientComponentRegistry = new DistroClientComponentRegistry(serverMemberManager, distroProtocol, componentHolder,
                taskEngineHolder, clientManager, clusterRpcClientProxy);
    }
    
    @Test
    void testDoRegister() {
        distroClientComponentRegistry.doRegister();
        
        DistroDataStorage dataStorage = componentHolder.findDataStorage(DistroClientDataProcessor.TYPE);
        assertNotNull(dataStorage);
        
        DistroDataProcessor dataProcessor = componentHolder.findDataProcessor(DistroClientDataProcessor.TYPE);
        assertNotNull(dataProcessor);
        
        DistroFailedTaskHandler failedTaskHandler = componentHolder.findFailedTaskHandler(DistroClientDataProcessor.TYPE);
        assertNotNull(failedTaskHandler);
        
        DistroTransportAgent transportAgent = componentHolder.findTransportAgent(DistroClientDataProcessor.TYPE);
        assertNotNull(transportAgent);
        
    }
    
}