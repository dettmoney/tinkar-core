/*
 * Copyright © 2015 Integrated Knowledge Management (support@ikm.dev)
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
package dev.ikm.tinkar.common.service;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public interface ExecutorService {

    /** The fork join executor. */
    ForkJoinPool forkJoinThreadPool();

    /** The blocking thread pool executor. */
    ThreadPoolExecutor blockingThreadPool();

    /** The thread pool executor. */
    ThreadPoolExecutor threadPool();

    /** The io thread pool executor. */
    ThreadPoolExecutor ioThreadPool();

    /** The scheduled executor. */
    ScheduledExecutorService scheduled();

}
