/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.ByteArrayOutputStream
 *  java.io.EOFException
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.InterruptedException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Thread
 *  java.nio.charset.Charset
 *  java.util.LinkedHashMap
 *  java.util.Map
 */
package net.optifine.http;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.src.Config;
import net.optifine.http.HttpPipelineConnection;
import net.optifine.http.HttpPipelineRequest;
import net.optifine.http.HttpResponse;

public class HttpPipelineReceiver
extends Thread {
    private HttpPipelineConnection httpPipelineConnection = null;
    private static final Charset ASCII = Charset.forName((String)"ASCII");
    private static final String HEADER_CONTENT_LENGTH = "Content-Length";
    private static final char CR = '\r';
    private static final char LF = '\n';

    public HttpPipelineReceiver(HttpPipelineConnection httpPipelineConnection) {
        super("HttpPipelineReceiver");
        this.httpPipelineConnection = httpPipelineConnection;
    }

    public void run() {
        while (!Thread.interrupted()) {
            HttpPipelineRequest httppipelinerequest = null;
            try {
                httppipelinerequest = this.httpPipelineConnection.getNextRequestReceive();
                InputStream inputstream = this.httpPipelineConnection.getInputStream();
                HttpResponse httpresponse = this.readResponse(inputstream);
                this.httpPipelineConnection.onResponseReceived(httppipelinerequest, httpresponse);
            }
            catch (InterruptedException var4) {
                return;
            }
            catch (Exception exception) {
                this.httpPipelineConnection.onExceptionReceive(httppipelinerequest, exception);
            }
        }
    }

    private HttpResponse readResponse(InputStream in) throws IOException {
        String s = this.readLine(in);
        String[] astring = Config.tokenize(s, " ");
        if (astring.length < 3) {
            throw new IOException("Invalid status line: " + s);
        }
        String s1 = astring[0];
        int i = Config.parseInt(astring[1], 0);
        String s2 = astring[2];
        LinkedHashMap map = new LinkedHashMap();
        do {
            String s3;
            if ((s3 = this.readLine(in)).length() <= 0) {
                byte[] abyte = null;
                String s6 = (String)map.get((Object)HEADER_CONTENT_LENGTH);
                if (s6 != null) {
                    int k = Config.parseInt(s6, -1);
                    if (k > 0) {
                        abyte = new byte[k];
                        this.readFull(abyte, in);
                    }
                } else {
                    String s7 = (String)map.get((Object)"Transfer-Encoding");
                    if (Config.equals(s7, "chunked")) {
                        abyte = this.readContentChunked(in);
                    }
                }
                return new HttpResponse(i, s, (Map)map, abyte);
            }
            int j = s3.indexOf(":");
            if (j <= 0) continue;
            String s4 = s3.substring(0, j).trim();
            String s5 = s3.substring(j + 1).trim();
            map.put((Object)s4, (Object)s5);
        } while (true);
    }

    private byte[] readContentChunked(InputStream in) throws IOException {
        ByteArrayOutputStream bytearrayoutputstream;
        int i;
        bytearrayoutputstream = new ByteArrayOutputStream();
        do {
            String s = this.readLine(in);
            String[] astring = Config.tokenize(s, "; ");
            i = Integer.parseInt((String)astring[0], (int)16);
            byte[] abyte = new byte[i];
            this.readFull(abyte, in);
            bytearrayoutputstream.write(abyte);
            this.readLine(in);
        } while (i != 0);
        return bytearrayoutputstream.toByteArray();
    }

    private void readFull(byte[] buf, InputStream in) throws IOException {
        int j;
        for (int i = 0; i < buf.length; i += j) {
            j = in.read(buf, i, buf.length - i);
            if (j >= 0) continue;
            throw new EOFException();
        }
    }

    private String readLine(InputStream in) throws IOException {
        int j;
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        int i = -1;
        boolean flag = false;
        while ((j = in.read()) >= 0) {
            bytearrayoutputstream.write(j);
            if (i == 13 && j == 10) {
                flag = true;
                break;
            }
            i = j;
        }
        byte[] abyte = bytearrayoutputstream.toByteArray();
        String s = new String(abyte, ASCII);
        if (flag) {
            s = s.substring(0, s.length() - 2);
        }
        return s;
    }
}

