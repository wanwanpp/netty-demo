/**
 * Copyright 2013 TPRI. All Rights Reserved.
 */
package com.wp.utils;

import java.io.IOException;
import java.util.Map;

public final class HttpCallerUtils {

    private HttpCallerUtils() {
    }

    public static String request(String url, Map<String, String> params) throws IOException {
        HttpCaller c = new HttpCaller(url, HttpCaller.REQUEST_METHOD_POST, params);
        return c.request(String.class);
    }

    public static String get(String url, Map<String, String> params) throws IOException {
        HttpCaller c = new HttpCaller(url, HttpCaller.REQUEST_METHOD_GET, params);
        return c.request(String.class);
    }

    public static byte[] getStream(String url, Map<String, String> params) throws IOException {
        HttpCaller c = new HttpCaller(url, HttpCaller.REQUEST_METHOD_GET, true, params);
        return c.request(Byte.class);
    }

    public static String post(String url, Map<String, String> params) throws IOException {
        HttpCaller c = new HttpCaller(url, HttpCaller.REQUEST_METHOD_POST, params);
        return c.request(String.class);
    }

    public static byte[] postStream(String url, Map<String, String> params) throws IOException {
        HttpCaller c = new HttpCaller(url, HttpCaller.REQUEST_METHOD_POST, true, params);
        return c.request(Byte.class);
    }

    public static String put(String url, Map<String, String> params) throws IOException {
        HttpCaller c = new HttpCaller(url, HttpCaller.REQUEST_METHOD_PUT, params);
        return c.request(String.class);
    }

    public static String delete(String url, Map<String, String> params) throws IOException {
        HttpCaller c = new HttpCaller(url, HttpCaller.REQUEST_METHOD_DELETE, params);
        return c.request(String.class);
    }
}
