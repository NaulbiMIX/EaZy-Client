/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InterruptedIOException
 *  java.lang.Exception
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.net.Proxy
 *  java.net.URL
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.LinkedHashMap
 *  java.util.Map
 */
package net.optifine.http;

import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.net.Proxy;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.Config;
import net.optifine.http.HttpListener;
import net.optifine.http.HttpPipelineConnection;
import net.optifine.http.HttpPipelineRequest;
import net.optifine.http.HttpRequest;
import net.optifine.http.HttpResponse;

public class HttpPipeline {
    private static Map mapConnections = new HashMap();
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_HOST = "Host";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String HEADER_LOCATION = "Location";
    public static final String HEADER_KEEP_ALIVE = "Keep-Alive";
    public static final String HEADER_CONNECTION = "Connection";
    public static final String HEADER_VALUE_KEEP_ALIVE = "keep-alive";
    public static final String HEADER_TRANSFER_ENCODING = "Transfer-Encoding";
    public static final String HEADER_VALUE_CHUNKED = "chunked";

    private HttpPipeline() {
    }

    public static void addRequest(String urlStr, HttpListener listener) throws IOException {
        HttpPipeline.addRequest(urlStr, listener, Proxy.NO_PROXY);
    }

    public static void addRequest(String urlStr, HttpListener listener, Proxy proxy) throws IOException {
        HttpRequest httprequest = HttpPipeline.makeRequest(urlStr, proxy);
        HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(httprequest, listener);
        HttpPipeline.addRequest(httppipelinerequest);
    }

    public static HttpRequest makeRequest(String urlStr, Proxy proxy) throws IOException {
        URL url = new URL(urlStr);
        if (!url.getProtocol().equals((Object)"http")) {
            throw new IOException("Only protocol http is supported: " + (Object)url);
        }
        String s = url.getFile();
        String s1 = url.getHost();
        int i = url.getPort();
        if (i <= 0) {
            i = 80;
        }
        String s2 = "GET";
        String s3 = "HTTP/1.1";
        LinkedHashMap map = new LinkedHashMap();
        map.put((Object)HEADER_USER_AGENT, (Object)("Java/" + System.getProperty((String)"java.version")));
        map.put((Object)HEADER_HOST, (Object)s1);
        map.put((Object)HEADER_ACCEPT, (Object)"text/html, image/gif, image/png");
        map.put((Object)HEADER_CONNECTION, (Object)HEADER_VALUE_KEEP_ALIVE);
        byte[] abyte = new byte[]{};
        HttpRequest httprequest = new HttpRequest(s1, i, proxy, s2, s, s3, (Map<String, String>)map, abyte);
        return httprequest;
    }

    public static void addRequest(HttpPipelineRequest pr) {
        HttpRequest httprequest = pr.getHttpRequest();
        HttpPipelineConnection httppipelineconnection = HttpPipeline.getConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy());
        while (!httppipelineconnection.addRequest(pr)) {
            HttpPipeline.removeConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy(), httppipelineconnection);
            httppipelineconnection = HttpPipeline.getConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy());
        }
    }

    private static synchronized HttpPipelineConnection getConnection(String host, int port, Proxy proxy) {
        String s = HttpPipeline.makeConnectionKey(host, port, proxy);
        HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)mapConnections.get((Object)s);
        if (httppipelineconnection == null) {
            httppipelineconnection = new HttpPipelineConnection(host, port, proxy);
            mapConnections.put((Object)s, (Object)httppipelineconnection);
        }
        return httppipelineconnection;
    }

    private static synchronized void removeConnection(String host, int port, Proxy proxy, HttpPipelineConnection hpc) {
        String s = HttpPipeline.makeConnectionKey(host, port, proxy);
        HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)mapConnections.get((Object)s);
        if (httppipelineconnection == hpc) {
            mapConnections.remove((Object)s);
        }
    }

    private static String makeConnectionKey(String host, int port, Proxy proxy) {
        String s = host + ":" + port + "-" + (Object)proxy;
        return s;
    }

    public static byte[] get(String urlStr) throws IOException {
        return HttpPipeline.get(urlStr, Proxy.NO_PROXY);
    }

    public static byte[] get(String urlStr, Proxy proxy) throws IOException {
        if (urlStr.startsWith("file:")) {
            URL url = new URL(urlStr);
            InputStream inputstream = url.openStream();
            byte[] abyte = Config.readAll(inputstream);
            return abyte;
        }
        HttpRequest httprequest = HttpPipeline.makeRequest(urlStr, proxy);
        HttpResponse httpresponse = HttpPipeline.executeRequest(httprequest);
        if (httpresponse.getStatus() / 100 != 2) {
            throw new IOException("HTTP response: " + httpresponse.getStatus());
        }
        return httpresponse.getBody();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static HttpResponse executeRequest(HttpRequest req) throws IOException {
        HashMap map = new HashMap();
        String s = "Response";
        String s1 = "Exception";
        HttpListener httplistener = new HttpListener((Map)map){
            final /* synthetic */ Map val$map;
            {
                this.val$map = map;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void finished(HttpRequest req, HttpResponse resp) {
                Map map = this.val$map;
                synchronized (map) {
                    this.val$map.put((Object)"Response", (Object)resp);
                    this.val$map.notifyAll();
                }
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void failed(HttpRequest req, Exception e) {
                Map map = this.val$map;
                synchronized (map) {
                    this.val$map.put((Object)"Exception", (Object)e);
                    this.val$map.notifyAll();
                }
            }
        };
        HashMap hashMap = map;
        synchronized (hashMap) {
            HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(req, httplistener);
            HttpPipeline.addRequest(httppipelinerequest);
            try {
                map.wait();
            }
            catch (InterruptedException var10) {
                throw new InterruptedIOException("Interrupted");
            }
            Exception exception = (Exception)((Object)map.get((Object)"Exception"));
            if (exception != null) {
                if (exception instanceof IOException) {
                    throw (IOException)((Object)exception);
                }
                if (exception instanceof RuntimeException) {
                    throw (RuntimeException)((Object)exception);
                }
                throw new RuntimeException(exception.getMessage(), (Throwable)exception);
            }
            HttpResponse httpresponse = (HttpResponse)map.get((Object)"Response");
            if (httpresponse == null) {
                throw new IOException("Response is null");
            }
            return httpresponse;
        }
    }

    public static boolean hasActiveRequests() {
        for (Object o : mapConnections.values()) {
            HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)o;
            if (!httppipelineconnection.hasActiveRequests()) continue;
            return true;
        }
        return false;
    }

}

