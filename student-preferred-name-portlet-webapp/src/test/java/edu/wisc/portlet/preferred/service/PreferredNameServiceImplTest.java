/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package edu.wisc.portlet.preferred.service;

import edu.wisc.portlet.preferred.dao.PreferredNameDao;
import edu.wisc.portlet.preferred.form.PreferredName;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit test for PreferredNameServiceImpl.
 */
public class PreferredNameServiceImplTest {

    private PreferredNameServiceImpl serviceImpl;

    @Mock
    private PreferredNameDao preferredNameDao;

    @Before
    public void setUp() {
        initMocks(this);
        serviceImpl = new PreferredNameServiceImpl();
        serviceImpl.setPreferredNameDao(preferredNameDao);
    }


    /**
     * Test that the service represents the preferred name of a user
     * for whom the underlying DAO reports no preferred name as having an unpopulated preferred
     * name object.
     */
    @Test
    public void noPreferredNameRepresentedAsPreferredName() {

        when(preferredNameDao.getPreferredName("some_pvi")).thenReturn(null);

        final PreferredName returnedPreferredName = serviceImpl.getPreferredName("some_pvi");

        assertEquals(new PreferredName(), returnedPreferredName);


    }

}
