/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.dbutils;

import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * Convenience class for executing QueryExecutor, InsertExecutor, or UpdateExecutors asynchronously.
 *
 * @author William Speirs <wspeirs@apache.org>
 * @since 2.0
 */
public class AsyncQueryRunner {

    private final ExecutorService executorService;

    /**
     * Constructor for AsyncQueryRunner which uses a provided ExecutorService and underlying QueryRunner.
     *
     * @param executorService the {@code ExecutorService} instance used to run JDBC invocations concurrently.
     * @param queryRunner the {@code QueryRunner} instance to use for the queries.
     */
    public AsyncQueryRunner(ExecutorService executorService) {
        this.executorService = executorService;
    }

    /**
     * Execute a {@link org.apache.commons.dbutils.QueryExecutor} given a handler.
     * @param <T> The type of object that the handler returns
     * @param handler The handler that converts the results into an object.
     * @return A <code>Future</code> which returns the result of the query call.
     * @throws SQLException if a database access error occurs
     */
    public <T> Future<T> query(final QueryExecutor executor, final ResultSetHandler<T> handler) throws SQLException {
        return executorService.submit(new Callable<T>() {

            @Override
            public T call() throws Exception {
                return executor.query(handler);
            }

        });
    }

    /**
     * Execute a {@link org.apache.commons.dbutils.UpdateExecutor} given a handler.
     * @param <T> The type of object that the handler returns
     * @return A <code>Future</code> which returns the result of the query call.
     * @throws SQLException if a database access error occurs
     */
    public Future<Integer> update(final UpdateExecutor executor) throws SQLException {
        return executorService.submit(new Callable<Integer>() {

            @Override
            public Integer call() throws Exception {
                return executor.update();
            }

        });
    }

    /**
     * Execute a {@link org.apache.commons.dbutils.InsertExecutor} given a handler.
     * @param <T> The type of object that the handler returns
     * @param handler The handler that converts the results into an object.
     * @return A <code>Future</code> which returns the result of the query call.
     * @throws SQLException if a database access error occurs
     */
    public <T> Future<T> insert(final InsertExecutor executor, final ResultSetHandler<T> handler) throws SQLException {
        return executorService.submit(new Callable<T>() {

            @Override
            public T call() throws Exception {
                return executor.insert(handler);
            }

        });
    }

}
