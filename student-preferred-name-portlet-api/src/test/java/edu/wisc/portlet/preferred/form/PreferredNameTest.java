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

package edu.wisc.portlet.preferred.form;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit tests for PreferredName.
 *
 * @since StudentPreferredNamePortlet v 1.0.7
 */
public class PreferredNameTest {

    /**
     * Test that two PreferredName s with identical semantic content are considered equal.
     */
    @Test
    public void testSemanticEquals() {

        final PreferredName timOne = new PreferredName();
        final PreferredName timTwo = new PreferredName();

        timOne.setFirstName("Timothy");
        timTwo.setFirstName("Timothy");

        timOne.setMiddleName("Elliott");
        timTwo.setMiddleName("Elliott");

        timOne.setLastName("Levett");
        timTwo.setLastName("Levett");

        timOne.setPvi("a_pvi_value");
        timTwo.setPvi("a_pvi_value");

        timOne.setHideSource(true);
        timTwo.setHideSource(true);

        assertEquals(timOne, timTwo);
        assertEquals(timOne.hashCode(), timTwo.hashCode());
    }

    /**
     * Test that new, blank instances of PreferredName are equal.
     */
    @Test
    public void testNewInstancesAreEqual() {

        final PreferredName nameOne = new PreferredName();
        final PreferredName nameTwo = new PreferredName();

        assertEquals(nameOne.hashCode(), nameTwo.hashCode());
        assertEquals(nameOne, nameTwo);

    }
}
