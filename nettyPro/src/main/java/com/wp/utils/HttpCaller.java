/**
 * Copyright 2013 TPRI. All Rights Reserved.
 */
package com.wp.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Map.Entry;

public class HttpCaller {

    public static final String REQUEST_METHOD_GET = "GET";

    public static final String REQUEST_METHOD_POST = "POST";

    public static final String REQUEST_METHOD_PUT = "PUT";

    public static final String REQUEST_METHOD_DELETE = "DELETE";

    private HttpCallerConfig config;

    public HttpCaller(String url, String method) {
        this.config = new HttpCallerConfig();
        this.config.setUrl(url);
        this.config.setMethod(method);
    }

    public HttpCaller(String url, Map<String, String> params) {
        this.config = new HttpCallerConfig();
        this.config.setUrl(url);
        this.config.setMethod(REQUEST_METHOD_POST);
        this.config.setParams(params);
    }

    public HttpCaller(String url, String method, Map<String, String> params) {
        this.config = new HttpCallerConfig();
        this.config.setUrl(url);
        this.config.setMethod(method);
        this.config.setParams(params);
    }

    public HttpCaller(String url, String method, boolean isStream, Map<String, String> params) {
        this.config = new HttpCallerConfig();
        this.config.setUrl(url);
        this.config.setMethod(method);
        this.config.setStream(isStream);
        this.config.setParams(params);
    }
    
    public HttpCaller(HttpCallerConfig config) {
        this.config = config;
    }

    public <T> T request(Object T) throws IOException {
        URL targetUrl = new URL(this.config.getUrl());
        HttpURLConnection conn = (HttpURLConnection) targetUrl.openConnection();
        conn.setRequestMethod(this.config.getMethod());
        conn.setConnectTimeout(this.config.getConnTimeout());
        conn.setReadTimeout(this.config.getReadTimeout());
        //conn.setRequestProperty("Content-type", "application/json");
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.connect();
        write(conn);
        if(this.config.isStream()){
        	 return (T) readStream(conn);
        }
        return (T) read(conn);
    }
    
    private byte[] readStream(HttpURLConnection conn) throws IOException {
        InputStream is = null;
        ByteArrayOutputStream bos = null;
        try {
            is = conn.getInputStream();
        	byte[] buf = new byte[this.config.getMaxBufferSize()];
        	int num = -1;
        	bos = new ByteArrayOutputStream();
        	while((num = is.read(buf, 0 , buf.length)) != -1 ){
        		bos.write(buf, 0, num);
        	}
        }
        finally {
            if (bos != null) {
            	bos.close();
            }
            if (is != null) {
                is.close();
            }
        }
        return bos.toByteArray();
    }
    private String read(HttpURLConnection conn) throws IOException {
        StringBuffer str = new StringBuffer();
        InputStream is = null;
        InputStreamReader reader = null;
        try {
            is = conn.getInputStream();
            reader = new InputStreamReader(is, this.config.getCharset());
            char[] buffer = new char[this.config.getMaxBufferSize()];
            int c = 0;
            while ((c = reader.read(buffer)) >= 0) {
                str.append(buffer, 0, c);
            }
        }
        finally {
            if (reader != null) {
                reader.close();
            }
            if (is != null) {
                is.close();
            }
        }
        if (str.length() < 1) {
            return null;
        }
        return str.toString();
    }

    private void write(HttpURLConnection conn) throws IOException {
        Map<String, String> params = this.config.getParams();
        if (params == null || params.isEmpty()) {
            return;
        }
        OutputStream out = null;
        OutputStreamWriter writer = null;
        try {
            out = conn.getOutputStream();
            writer = new OutputStreamWriter(out, this.config.getCharset());
            for (Entry<String, String> param : params.entrySet()) {
                writer.write("&");
                writer.write(param.getKey());
                writer.write("=");
                if (param.getValue() != null) {
                    writer.write(param.getValue());
                }
            }
        }
        finally {
            if (writer != null) {
                writer.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }
}
