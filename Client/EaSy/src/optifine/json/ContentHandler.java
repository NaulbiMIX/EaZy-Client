/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.lang.Object
 *  java.lang.String
 */
package optifine.json;

import java.io.IOException;
import optifine.json.ParseException;

public interface ContentHandler {
    public void startJSON() throws ParseException, IOException;

    public void endJSON() throws ParseException, IOException;

    public boolean startObject() throws ParseException, IOException;

    public boolean endObject() throws ParseException, IOException;

    public boolean startObjectEntry(String var1) throws ParseException, IOException;

    public boolean endObjectEntry() throws ParseException, IOException;

    public boolean startArray() throws ParseException, IOException;

    public boolean endArray() throws ParseException, IOException;

    public boolean primitive(Object var1) throws ParseException, IOException;
}

