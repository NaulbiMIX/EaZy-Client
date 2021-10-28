/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.PrintStream
 *  java.io.Reader
 *  java.io.StringReader
 *  java.lang.Class
 *  java.lang.Error
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.System
 *  java.text.ParseException
 *  java.text.SimpleDateFormat
 *  java.util.Date
 *  java.util.LinkedList
 *  java.util.List
 *  java.util.Map
 */
package optifine.json;

import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import optifine.json.ContainerFactory;
import optifine.json.ContentHandler;
import optifine.json.JSONArray;
import optifine.json.JSONObject;
import optifine.json.ParseException;
import optifine.json.Yylex;
import optifine.json.Yytoken;

public class JSONParser {
    public static final int S_INIT = 0;
    public static final int S_IN_FINISHED_VALUE = 1;
    public static final int S_IN_OBJECT = 2;
    public static final int S_IN_ARRAY = 3;
    public static final int S_PASSED_PAIR_KEY = 4;
    public static final int S_IN_PAIR_VALUE = 5;
    public static final int S_END = 6;
    public static final int S_IN_ERROR = -1;
    private LinkedList handlerStatusStack;
    private Yylex lexer = new Yylex((Reader)null);
    private Yytoken token = null;
    private int status = 0;

    private int peekStatus(LinkedList statusStack) {
        if (statusStack.size() == 0) {
            return -1;
        }
        Integer integer = (Integer)statusStack.getFirst();
        return integer;
    }

    public void reset() {
        this.token = null;
        this.status = 0;
        this.handlerStatusStack = null;
    }

    public void reset(Reader in) {
        this.lexer.yyreset(in);
        this.reset();
    }

    public int getPosition() {
        return this.lexer.getPosition();
    }

    public Object parse(String s) throws ParseException {
        return this.parse(s, (ContainerFactory)null);
    }

    public Object parse(String s, ContainerFactory containerFactory) throws ParseException {
        StringReader stringreader = new StringReader(s);
        try {
            return this.parse((Reader)stringreader, containerFactory);
        }
        catch (IOException ioexception) {
            throw new ParseException(-1, 2, (Object)ioexception);
        }
    }

    public Object parse(Reader in) throws IOException, ParseException {
        return this.parse(in, (ContainerFactory)null);
    }

    public Object parse(Reader in, ContainerFactory containerFactory) throws IOException, ParseException {
        this.reset(in);
        LinkedList linkedlist = new LinkedList();
        LinkedList linkedlist1 = new LinkedList();
        do {
            this.nextToken();
            block1 : switch (this.status) {
                case -1: {
                    throw new ParseException(this.getPosition(), 1, this.token);
                }
                case 0: {
                    switch (this.token.type) {
                        case 0: {
                            this.status = 1;
                            linkedlist.addFirst((Object)new Integer(this.status));
                            linkedlist1.addFirst(this.token.value);
                            break block1;
                        }
                        case 1: {
                            this.status = 2;
                            linkedlist.addFirst((Object)new Integer(this.status));
                            linkedlist1.addFirst((Object)this.createObjectContainer(containerFactory));
                            break block1;
                        }
                        default: {
                            this.status = -1;
                            break block1;
                        }
                        case 3: 
                    }
                    this.status = 3;
                    linkedlist.addFirst((Object)new Integer(this.status));
                    linkedlist1.addFirst((Object)this.createArrayContainer(containerFactory));
                    break;
                }
                case 1: {
                    if (this.token.type == -1) {
                        return linkedlist1.removeFirst();
                    }
                    throw new ParseException(this.getPosition(), 1, this.token);
                }
                case 2: {
                    switch (this.token.type) {
                        case 0: {
                            if (this.token.value instanceof String) {
                                String s3 = (String)this.token.value;
                                linkedlist1.addFirst((Object)s3);
                                this.status = 4;
                                linkedlist.addFirst((Object)new Integer(this.status));
                                break block1;
                            }
                            this.status = -1;
                            break block1;
                        }
                        default: {
                            this.status = -1;
                            break block1;
                        }
                        case 2: {
                            if (linkedlist1.size() > 1) {
                                linkedlist.removeFirst();
                                linkedlist1.removeFirst();
                                this.status = this.peekStatus(linkedlist);
                                break block1;
                            }
                            this.status = 1;
                        }
                        case 5: 
                    }
                    break;
                }
                case 3: {
                    switch (this.token.type) {
                        case 0: {
                            List list3 = (List)linkedlist1.getFirst();
                            list3.add(this.token.value);
                            break block1;
                        }
                        case 1: {
                            List list2 = (List)linkedlist1.getFirst();
                            Map map4 = this.createObjectContainer(containerFactory);
                            list2.add((Object)map4);
                            this.status = 2;
                            linkedlist.addFirst((Object)new Integer(this.status));
                            linkedlist1.addFirst((Object)map4);
                            break block1;
                        }
                        default: {
                            this.status = -1;
                            break block1;
                        }
                        case 3: {
                            List list1 = (List)linkedlist1.getFirst();
                            List list4 = this.createArrayContainer(containerFactory);
                            list1.add((Object)list4);
                            this.status = 3;
                            linkedlist.addFirst((Object)new Integer(this.status));
                            linkedlist1.addFirst((Object)list4);
                            break block1;
                        }
                        case 4: {
                            if (linkedlist1.size() > 1) {
                                linkedlist.removeFirst();
                                linkedlist1.removeFirst();
                                this.status = this.peekStatus(linkedlist);
                                break block1;
                            }
                            this.status = 1;
                        }
                        case 5: 
                    }
                    break;
                }
                case 4: {
                    switch (this.token.type) {
                        case 0: {
                            linkedlist.removeFirst();
                            String s2 = (String)linkedlist1.removeFirst();
                            Map map3 = (Map)linkedlist1.getFirst();
                            map3.put((Object)s2, this.token.value);
                            this.status = this.peekStatus(linkedlist);
                            break block1;
                        }
                        case 1: {
                            linkedlist.removeFirst();
                            String s1 = (String)linkedlist1.removeFirst();
                            Map map2 = (Map)linkedlist1.getFirst();
                            Map map1 = this.createObjectContainer(containerFactory);
                            map2.put((Object)s1, (Object)map1);
                            this.status = 2;
                            linkedlist.addFirst((Object)new Integer(this.status));
                            linkedlist1.addFirst((Object)map1);
                            break block1;
                        }
                        default: {
                            this.status = -1;
                            break block1;
                        }
                        case 3: {
                            linkedlist.removeFirst();
                            String s = (String)linkedlist1.removeFirst();
                            Map map = (Map)linkedlist1.getFirst();
                            List list = this.createArrayContainer(containerFactory);
                            map.put((Object)s, (Object)list);
                            this.status = 3;
                            linkedlist.addFirst((Object)new Integer(this.status));
                            linkedlist1.addFirst((Object)list);
                        }
                        case 6: 
                    }
                }
            }
            if (this.status != -1) continue;
            throw new ParseException(this.getPosition(), 1, this.token);
        } while (this.token.type != -1);
        throw new ParseException(this.getPosition(), 1, this.token);
    }

    private void nextToken() throws ParseException, IOException {
        this.token = this.lexer.yylex();
        if (this.token == null) {
            this.token = new Yytoken(-1, null);
        }
    }

    private Map createObjectContainer(ContainerFactory containerFactory) {
        if (containerFactory == null) {
            return new JSONObject();
        }
        Map map = containerFactory.createObjectContainer();
        return map == null ? new JSONObject() : map;
    }

    private List createArrayContainer(ContainerFactory containerFactory) {
        if (containerFactory == null) {
            return new JSONArray();
        }
        List list = containerFactory.creatArrayContainer();
        return list == null ? new JSONArray() : list;
    }

    public void parse(String s, ContentHandler contentHandler) throws ParseException {
        this.parse(s, contentHandler, false);
    }

    public void parse(String s, ContentHandler contentHandler, boolean isResume) throws ParseException {
        StringReader stringreader = new StringReader(s);
        try {
            this.parse((Reader)stringreader, contentHandler, isResume);
        }
        catch (IOException ioexception) {
            throw new ParseException(-1, 2, (Object)ioexception);
        }
    }

    public void parse(Reader in, ContentHandler contentHandler) throws IOException, ParseException {
        this.parse(in, contentHandler, false);
    }

    public void parse(Reader in, ContentHandler contentHandler, boolean isResume) throws IOException, ParseException {
        if (!isResume) {
            this.reset(in);
            this.handlerStatusStack = new LinkedList();
        } else if (this.handlerStatusStack == null) {
            isResume = false;
            this.reset(in);
            this.handlerStatusStack = new LinkedList();
        }
        LinkedList linkedlist = this.handlerStatusStack;
        try {
            do {
                block1 : switch (this.status) {
                    case -1: {
                        throw new ParseException(this.getPosition(), 1, this.token);
                    }
                    case 0: {
                        contentHandler.startJSON();
                        this.nextToken();
                        switch (this.token.type) {
                            case 0: {
                                this.status = 1;
                                linkedlist.addFirst((Object)new Integer(this.status));
                                if (contentHandler.primitive(this.token.value)) break block1;
                                return;
                            }
                            case 1: {
                                this.status = 2;
                                linkedlist.addFirst((Object)new Integer(this.status));
                                if (contentHandler.startObject()) break block1;
                                return;
                            }
                            default: {
                                this.status = -1;
                                break block1;
                            }
                            case 3: 
                        }
                        this.status = 3;
                        linkedlist.addFirst((Object)new Integer(this.status));
                        if (contentHandler.startArray()) break;
                        return;
                    }
                    case 1: {
                        this.nextToken();
                        if (this.token.type == -1) {
                            contentHandler.endJSON();
                            this.status = 6;
                            return;
                        }
                        this.status = -1;
                        throw new ParseException(this.getPosition(), 1, this.token);
                    }
                    case 2: {
                        this.nextToken();
                        switch (this.token.type) {
                            case 0: {
                                if (this.token.value instanceof String) {
                                    String s = (String)this.token.value;
                                    this.status = 4;
                                    linkedlist.addFirst((Object)new Integer(this.status));
                                    if (contentHandler.startObjectEntry(s)) break block1;
                                    return;
                                }
                                this.status = -1;
                                break;
                            }
                            default: {
                                this.status = -1;
                                break;
                            }
                            case 2: {
                                if (linkedlist.size() > 1) {
                                    linkedlist.removeFirst();
                                    this.status = this.peekStatus(linkedlist);
                                } else {
                                    this.status = 1;
                                }
                                if (!contentHandler.endObject()) {
                                    return;
                                }
                            }
                            case 5: {
                                break;
                            }
                        }
                        break;
                    }
                    case 3: {
                        this.nextToken();
                        switch (this.token.type) {
                            case 0: {
                                if (contentHandler.primitive(this.token.value)) break block1;
                                return;
                            }
                            case 1: {
                                this.status = 2;
                                linkedlist.addFirst((Object)new Integer(this.status));
                                if (contentHandler.startObject()) break block1;
                                return;
                            }
                            default: {
                                this.status = -1;
                                break block1;
                            }
                            case 3: {
                                this.status = 3;
                                linkedlist.addFirst((Object)new Integer(this.status));
                                if (contentHandler.startArray()) break block1;
                                return;
                            }
                            case 4: {
                                if (linkedlist.size() > 1) {
                                    linkedlist.removeFirst();
                                    this.status = this.peekStatus(linkedlist);
                                } else {
                                    this.status = 1;
                                }
                                if (contentHandler.endArray()) break;
                                return;
                            }
                            case 5: 
                        }
                        break;
                    }
                    case 4: {
                        this.nextToken();
                        switch (this.token.type) {
                            case 0: {
                                linkedlist.removeFirst();
                                this.status = this.peekStatus(linkedlist);
                                if (!contentHandler.primitive(this.token.value)) {
                                    return;
                                }
                                if (contentHandler.endObjectEntry()) break block1;
                                return;
                            }
                            case 1: {
                                linkedlist.removeFirst();
                                linkedlist.addFirst((Object)new Integer(5));
                                this.status = 2;
                                linkedlist.addFirst((Object)new Integer(this.status));
                                if (contentHandler.startObject()) break block1;
                                return;
                            }
                            default: {
                                this.status = -1;
                                break block1;
                            }
                            case 3: {
                                linkedlist.removeFirst();
                                linkedlist.addFirst((Object)new Integer(5));
                                this.status = 3;
                                linkedlist.addFirst((Object)new Integer(this.status));
                                if (contentHandler.startArray()) break;
                                return;
                            }
                            case 6: 
                        }
                        break;
                    }
                    case 5: {
                        linkedlist.removeFirst();
                        this.status = this.peekStatus(linkedlist);
                        if (contentHandler.endObjectEntry()) break;
                        return;
                    }
                    case 6: {
                        return;
                    }
                }
                if (this.status != -1) continue;
                throw new ParseException(this.getPosition(), 1, this.token);
            } while (this.token.type != -1);
        }
        catch (IOException ioexception) {
            this.status = -1;
            throw ioexception;
        }
        catch (ParseException parseexception) {
            this.status = -1;
            throw parseexception;
        }
        catch (RuntimeException runtimeexception) {
            this.status = -1;
            throw runtimeexception;
        }
        catch (Error error) {
            this.status = -1;
            throw error;
        }
        this.status = -1;
        throw new ParseException(this.getPosition(), 1, this.token);
    }

    public static Date parseDate(String input) {
        if (input == null) {
            return null;
        }
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
        if (input.endsWith("Z")) {
            input = input.substring(0, input.length() - 1) + "GMT-00:00";
        } else {
            int i = 6;
            String s = input.substring(0, input.length() - i);
            String s1 = input.substring(input.length() - i, input.length());
            input = s + "GMT" + s1;
        }
        try {
            return simpledateformat.parse(input);
        }
        catch (java.text.ParseException parseexception) {
            System.out.println("Error parsing date: " + input);
            System.out.println(parseexception.getClass().getName() + ": " + parseexception.getMessage());
            return null;
        }
    }
}

