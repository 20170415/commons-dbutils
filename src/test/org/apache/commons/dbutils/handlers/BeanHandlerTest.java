/*
 * Copyright 2003-2005 The Apache Software Foundation
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
package org.apache.commons.dbutils.handlers;

import java.sql.SQLException;

import org.apache.commons.dbutils.BaseTestCase;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.TestBean;

/**
 * BeanHandlerTest
 */
public class BeanHandlerTest extends BaseTestCase {

	/**
	 * Constructor for BeanHandlerTest.
	 */
	public BeanHandlerTest(String name) {
		super(name);
	}

	public void testHandle() throws SQLException {
		ResultSetHandler h = new BeanHandler(TestBean.class);
        TestBean results = (TestBean) h.handle(this.rs);
        
		assertNotNull(results);
		assertEquals("1", results.getOne());
		assertEquals("2", results.getTwo());
		assertEquals("3", results.getThree());
        assertEquals("not set", results.getDoNotSet());
	}
    
    public void testEmptyResultSetHandle() throws SQLException {
        ResultSetHandler h = new BeanHandler(TestBean.class);
        TestBean results = (TestBean) h.handle(this.emptyResultSet);
        
        assertNull(results);
    }

}
