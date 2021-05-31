/**
 * Copyright 2020 Alibaba Group Holding Limited.
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
package com.alibaba.maxgraph.v2.store;

import com.alibaba.maxgraph.proto.v2.GraphDefPb;
import com.alibaba.maxgraph.v2.common.OperationBatch;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.Closeable;
import java.io.IOException;

public interface GraphPartition extends Closeable {

    /**
     * @param snapshotId
     * @param operationBatch
     * @return True if batch has DDL operation
     * @throws IOException
     */
    boolean writeBatch(long snapshotId, OperationBatch operationBatch) throws IOException;

    long recover();

    GraphDefPb getGraphDefBlob() throws IOException;

    void ingestHdfsFile(FileSystem fs, Path filePath) throws IOException;

    int getId();
}
