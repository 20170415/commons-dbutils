/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//dbutils/src/java/org/apache/commons/dbutils/Attic/ColumnProcessor.java,v 1.3 2003/12/13 20:51:09 dgraham Exp $
 * $Revision: 1.3 $
 * $Date: 2003/12/13 20:51:09 $
 * 
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2003 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowledgement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgement may appear in the software itself,
 *    if and wherever such third-party acknowledgements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */

package org.apache.commons.dbutils;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * <code>ColumnProcessor</code> implementations match 
 * column names to bean property names and convert 
 * <code>ResultSet</code> columns into objects for those bean properties.  
 * The processor is invoked when creating a JavaBean from a 
 * <code>ResultSet</code>.   
 *
 * @author Corby Page
 * @author David Graham
 * 
 * @see BasicColumnProcessor
 * @see BasicRowProcessor
 * 
 * @since DbUtils 1.1
 */
public interface ColumnProcessor {

    /**
     * Special array value used by <code>mapColumnsToProperties</code> that 
     * indicates there is no bean property that matches a column from a 
     * <code>ResultSet</code>.
     */
    public static final int PROPERTY_NOT_FOUND = -1;

    /**
     * The positions in the returned array represent column numbers.  The 
     * values stored at each position represent the index in the 
     * <code>PropertyDescriptor[]</code> for the bean property that matches 
     * the column name.  If no bean property was found for a column, the 
     * position is set to <code>PROPERTY_NOT_FOUND</code>.
     * 
     * @param rsmd The <code>ResultSetMetaData</code> containing column 
     * information.
     * 
     * @param props The bean property descriptors.
     * 
     * @return An int[] with column index to property index mappings.  The 0th 
     * element is meaningless because JDBC column indexing starts at 1.
     * 
     * @throws SQLException
     */
    public int[] mapColumnsToProperties(
        ResultSetMetaData rsmd,
        PropertyDescriptor[] props)
        throws SQLException;

    /**
     * Convert a <code>ResultSet</code> column into an object.  Simple 
     * implementations could just call <code>rs.getObject(index)</code> while
     * more complex implementations could perform type manipulation to match 
     * the column's type to the bean property type.
     * 
     * @param rs The <code>ResultSet</code> currently being processed.  It is
     * positioned on a valid row before being passed into this method.
     * 
     * @param index The current column index being processed.
     * 
     * @param propertyType The bean property type that this column needs to be
     * converted into.
     * 
     * @return The object from the <code>ResultSet</code> at the given column
     * index after optional type processing or <code>null</code> if the column
     * value was SQL NULL.
     * 
     * @throws SQLException
     */
    public Object process(ResultSet rs, int index, Class propertyType)
        throws SQLException;

}
