/*
 * Copyright 2003-2004 The Apache Software Foundation
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
package org.apache.commons.dbutils;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * Test the BasicRowProcessor class.
 */
public class BasicRowProcessorTest extends BaseTestCase {

    private static final RowProcessor processor = new BasicRowProcessor();

    /**
     * Constructor for BasicRowProcessorTest.
     * @param name
     */
    public BasicRowProcessorTest(String name) {
        super(name);
    }

    public void testToArray() throws SQLException {

        int rowCount = 0;
        Object[] a = null;
        while (this.rs.next()) {
            a = processor.toArray(this.rs);
            assertEquals(COLS, a.length);
            rowCount++;
        }

        assertEquals(ROWS, rowCount);
        assertEquals("4", a[0]);
        assertEquals("5", a[1]);
        assertEquals("6", a[2]);
    }

    public void testToBean() throws SQLException {

        int rowCount = 0;
        TestBean b = null;
        while (this.rs.next()) {
            b = (TestBean) processor.toBean(this.rs, TestBean.class);
            assertNotNull(b);
            rowCount++;
        }

        assertEquals(ROWS, rowCount);
        assertEquals("4", b.getOne());
        assertEquals("5", b.getTwo());
        assertEquals("6", b.getThree());
        assertEquals("not set", b.getDoNotSet());
        assertEquals(3, b.getIntTest());
        assertEquals(new Integer(4), b.getIntegerTest());
        assertEquals(null, b.getNullObjectTest());
        assertEquals(0, b.getNullPrimitiveTest());
        assertEquals("not a date", b.getNotDate());
    }

    public void testToBeanList() throws SQLException {

        List list = processor.toBeanList(this.rs, TestBean.class);
        assertNotNull(list);
        assertEquals(ROWS, list.size());

        TestBean b = (TestBean) list.get(1);

        assertEquals("4", b.getOne());
        assertEquals("5", b.getTwo());
        assertEquals("6", b.getThree());
        assertEquals("not set", b.getDoNotSet());
        assertEquals(3, b.getIntTest());
        assertEquals(new Integer(4), b.getIntegerTest());
        assertEquals(null, b.getNullObjectTest());
        assertEquals(0, b.getNullPrimitiveTest());
        assertEquals("not a date", b.getNotDate());
    }

    public void testToMap() throws SQLException {

        int rowCount = 0;
        Map m = null;
        while (this.rs.next()) {
            m = processor.toMap(this.rs);
            assertNotNull(m);
            assertEquals(COLS, m.keySet().size());
            rowCount++;
        }

        assertEquals(ROWS, rowCount);
        assertEquals("4", m.get("One")); // case shouldn't matter
        assertEquals("5", m.get("two"));
        assertEquals("6", m.get("THREE"));
    }

}
