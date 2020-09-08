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

package pw.cdmi.core.http.client;

import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pw.cdmi.core.http.Headers;
import pw.cdmi.core.http.client.event.ProgressInputStream;
import pw.cdmi.core.http.client.event.ProgressListener;
import pw.cdmi.core.http.client.event.ProgressPublisher;
import pw.cdmi.core.http.client.model.WebServiceRequest;

public class ResponseProgressHandler implements ResponseHandler {
    
	private static final Logger logger = LoggerFactory.getLogger(ResponseProgressHandler.class);
	
    private final WebServiceRequest originalRequest;
    
    public ResponseProgressHandler(WebServiceRequest originalRequest) {
        this.originalRequest = originalRequest;
    }
    
    @Override
    public void handle(ResponseMessage response) throws ServiceException,
            ClientException {
        
        final ProgressListener listener = this.originalRequest.getProgressListener();
        Map<String, String> headers = response.getHeaders();
        String s = headers.get(Headers.CONTENT_LENGTH);
        if (s != null) {
            try {
                long contentLength = Long.parseLong(s);
                ProgressPublisher.publishResponseContentLength(listener, contentLength);
            } catch (NumberFormatException e) {
            	logger.error("Cannot parse the Content-Length header of the response: ", e);
            }
        }
        
        InputStream content = response.getContent();
        if (content != null && listener != ProgressListener.NOOP) {
            InputStream progressInputStream = 
                    ProgressInputStream.inputStreamForResponse(content, originalRequest);
            response.setContent(progressInputStream);
        }
    }

}
