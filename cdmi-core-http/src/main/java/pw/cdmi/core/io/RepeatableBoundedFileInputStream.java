/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package pw.cdmi.core.io;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pw.cdmi.core.http.client.ClientException;

public class RepeatableBoundedFileInputStream extends InputStream {
	private static final Logger logger = LoggerFactory.getLogger(RepeatableBoundedFileInputStream.class);
	
    private BoundedInputStream bis = null;
    private FileChannel fileChannel = null;
    private long markPos = 0;
    
    public RepeatableBoundedFileInputStream(BoundedInputStream bis) throws IOException {
        FileInputStream fin = (FileInputStream)bis.getWrappedInputStream();
        this.bis = bis;
        this.fileChannel = fin.getChannel();
        this.markPos = fileChannel.position();
    }

    public void reset() throws IOException {
        bis.backoff(fileChannel.position() - markPos);
        fileChannel.position(markPos);
        logger.trace("Reset to position " + markPos);
    }

    public boolean markSupported() {
        return true;
    }

    public void mark(int readlimit) {
        try {
            markPos = fileChannel.position();
        } catch (IOException e) {
            throw new ClientException("Failed to mark file position", e);
        }
        logger.trace("File input stream marked at position " + markPos);
    }

    public int available() throws IOException {
        return bis.available();
    }

    public void close() throws IOException {
        bis.close();
    }

    public int read() throws IOException {
        return bis.read();
    }

    @Override
    public long skip(long n) throws IOException {
        return bis.skip(n);
    }

    public int read(byte[] b, int off, int len) throws IOException {
        return bis.read(b, off, len);
    }

    public InputStream getWrappedInputStream() {
        return this.bis;
    }
 
}

