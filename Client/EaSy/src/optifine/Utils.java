/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.awt.Component
 *  java.awt.Dimension
 *  java.awt.Rectangle
 *  java.awt.Toolkit
 *  java.io.BufferedReader
 *  java.io.ByteArrayOutputStream
 *  java.io.File
 *  java.io.FileInputStream
 *  java.io.FileOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.OutputStream
 *  java.io.PrintStream
 *  java.io.PrintWriter
 *  java.io.Reader
 *  java.io.StringWriter
 *  java.io.Writer
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Enum
 *  java.lang.IllegalArgumentException
 *  java.lang.Integer
 *  java.lang.NullPointerException
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.RuntimeException
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.System
 *  java.lang.Throwable
 *  java.lang.reflect.Array
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.StringTokenizer
 *  javax.swing.JOptionPane
 */
package optifine;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.StringTokenizer;
import javax.swing.JOptionPane;

public class Utils {
    public static final String MAC_OS_HOME_PREFIX = "Library/Application Support";
    private static final char[] hexTable = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private Utils() {
    }

    public static File getWorkingDirectory() {
        return Utils.getWorkingDirectory("minecraft");
    }

    public static File getWorkingDirectory(String applicationName) {
        String s = System.getProperty((String)"user.home", (String)".");
        File file1 = null;
        switch (Utils.getPlatform().ordinal()) {
            case 1: 
            case 2: {
                file1 = new File(s, '.' + applicationName + '/');
                break;
            }
            case 3: {
                String s1 = System.getenv((String)"APPDATA");
                if (s1 != null) {
                    file1 = new File(s1, "." + applicationName + '/');
                    break;
                }
                file1 = new File(s, '.' + applicationName + '/');
                break;
            }
            case 4: {
                file1 = new File(s, "Library/Application Support/" + applicationName);
                break;
            }
            default: {
                file1 = new File(s, applicationName + '/');
            }
        }
        if (!file1.exists() && !file1.mkdirs()) {
            throw new RuntimeException("The working directory could not be created: " + (Object)file1);
        }
        return file1;
    }

    public static OS getPlatform() {
        String s = System.getProperty((String)"os.name").toLowerCase();
        if (s.contains((CharSequence)"win")) {
            return OS.WINDOWS;
        }
        if (s.contains((CharSequence)"mac")) {
            return OS.MACOS;
        }
        if (s.contains((CharSequence)"solaris")) {
            return OS.SOLARIS;
        }
        if (s.contains((CharSequence)"sunos")) {
            return OS.SOLARIS;
        }
        if (s.contains((CharSequence)"linux")) {
            return OS.LINUX;
        }
        return s.contains((CharSequence)"unix") ? OS.LINUX : OS.UNKNOWN;
    }

    public static int find(byte[] buf, byte[] pattern) {
        return Utils.find(buf, 0, pattern);
    }

    public static int find(byte[] buf, int index, byte[] pattern) {
        for (int i = index; i < buf.length - pattern.length; ++i) {
            boolean flag = true;
            for (int j = 0; j < pattern.length; ++j) {
                if (pattern[j] == buf[i + j]) continue;
                flag = false;
                break;
            }
            if (!flag) continue;
            return i;
        }
        return -1;
    }

    public static byte[] readAll(InputStream is) throws IOException {
        ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
        byte[] abyte = new byte[1024];
        do {
            int i;
            if ((i = is.read(abyte)) < 0) {
                is.close();
                byte[] abyte1 = bytearrayoutputstream.toByteArray();
                return abyte1;
            }
            bytearrayoutputstream.write(abyte, 0, i);
        } while (true);
    }

    public static void dbg(String str) {
        System.out.println(str);
    }

    public static String[] tokenize(String str, String delim) {
        ArrayList list = new ArrayList();
        StringTokenizer stringtokenizer = new StringTokenizer(str, delim);
        while (stringtokenizer.hasMoreTokens()) {
            String s = stringtokenizer.nextToken();
            list.add((Object)s);
        }
        String[] astring = (String[])list.toArray((Object[])new String[list.size()]);
        return astring;
    }

    public static String getExceptionStackTrace(Throwable e) {
        StringWriter stringwriter = new StringWriter();
        PrintWriter printwriter = new PrintWriter((Writer)stringwriter);
        e.printStackTrace(printwriter);
        printwriter.close();
        try {
            stringwriter.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return stringwriter.getBuffer().toString();
    }

    public static void copyFile(File fileSrc, File fileDest) throws IOException {
        if (!fileSrc.getCanonicalPath().equals((Object)fileDest.getCanonicalPath())) {
            FileInputStream fileinputstream = new FileInputStream(fileSrc);
            FileOutputStream fileoutputstream = new FileOutputStream(fileDest);
            Utils.copyAll((InputStream)fileinputstream, (OutputStream)fileoutputstream);
            fileoutputstream.flush();
            fileinputstream.close();
            fileoutputstream.close();
        }
    }

    public static void copyAll(InputStream is, OutputStream os) throws IOException {
        byte[] abyte = new byte[1024];
        int i;
        while ((i = is.read(abyte)) >= 0) {
            os.write(abyte, 0, i);
        }
        return;
    }

    public static void showMessage(String msg) {
        JOptionPane.showMessageDialog((Component)null, (Object)msg, (String)"OptiFine", (int)1);
    }

    public static void showErrorMessage(String msg) {
        JOptionPane.showMessageDialog((Component)null, (Object)msg, (String)"Error", (int)0);
    }

    public static String readFile(File file) throws IOException {
        return Utils.readFile(file, "ASCII");
    }

    public static String readFile(File file, String encoding) throws IOException {
        FileInputStream fileinputstream = new FileInputStream(file);
        return Utils.readText((InputStream)fileinputstream, encoding);
    }

    public static String readText(InputStream in, String encoding) throws IOException {
        InputStreamReader inputstreamreader = new InputStreamReader(in, encoding);
        BufferedReader bufferedreader = new BufferedReader((Reader)inputstreamreader);
        StringBuffer stringbuffer = new StringBuffer();
        do {
            String s;
            if ((s = bufferedreader.readLine()) == null) {
                bufferedreader.close();
                inputstreamreader.close();
                in.close();
                return stringbuffer.toString();
            }
            stringbuffer.append(s);
            stringbuffer.append("\n");
        } while (true);
    }

    public static String[] readLines(InputStream in, String encoding) throws IOException {
        String s = Utils.readText(in, encoding);
        String[] astring = Utils.tokenize(s, "\n\r");
        return astring;
    }

    public static void centerWindow(Component c, Component par) {
        if (c != null) {
            Rectangle rectangle1;
            Rectangle rectangle = c.getBounds();
            if (par != null && par.isVisible()) {
                rectangle1 = par.getBounds();
            } else {
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                rectangle1 = new Rectangle(0, 0, dimension.width, dimension.height);
            }
            int j = rectangle1.x + (rectangle1.width - rectangle.width) / 2;
            int i = rectangle1.y + (rectangle1.height - rectangle.height) / 2;
            if (j < 0) {
                j = 0;
            }
            if (i < 0) {
                i = 0;
            }
            c.setBounds(j, i, rectangle.width, rectangle.height);
        }
    }

    public static String byteArrayToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuffer stringbuffer = new StringBuffer();
        for (int i = 0; i < bytes.length; ++i) {
            byte b0 = bytes[i];
            stringbuffer.append(hexTable[b0 >> 4 & 15]);
            stringbuffer.append(hexTable[b0 & 15]);
        }
        return stringbuffer.toString();
    }

    public static String arrayToCommaSeparatedString(Object[] arr) {
        if (arr == null) {
            return "";
        }
        StringBuffer stringbuffer = new StringBuffer();
        for (int i = 0; i < arr.length; ++i) {
            Object object = arr[i];
            if (i > 0) {
                stringbuffer.append(", ");
            }
            if (object == null) {
                stringbuffer.append("null");
                continue;
            }
            if (!object.getClass().isArray()) {
                stringbuffer.append(arr[i]);
                continue;
            }
            stringbuffer.append("[");
            if (object instanceof Object[]) {
                Object[] aobject = (Object[])object;
                stringbuffer.append(Utils.arrayToCommaSeparatedString(aobject));
            } else {
                for (int j = 0; j < Array.getLength((Object)object); ++j) {
                    if (j > 0) {
                        stringbuffer.append(", ");
                    }
                    stringbuffer.append(Array.get((Object)object, (int)j));
                }
            }
            stringbuffer.append("]");
        }
        return stringbuffer.toString();
    }

    public static String removePrefix(String str, String prefix) {
        if (str != null && prefix != null) {
            if (str.startsWith(prefix)) {
                str = str.substring(prefix.length());
            }
            return str;
        }
        return str;
    }

    public static String removePrefix(String str, String[] prefixes) {
        if (str != null && prefixes != null) {
            String s;
            int i = str.length();
            for (int j = 0; j < prefixes.length && (str = Utils.removePrefix(str, s = prefixes[j])).length() == i; ++j) {
            }
            return str;
        }
        return str;
    }

    public static String removeSuffix(String str, String suffix) {
        if (str != null && suffix != null) {
            if (str.endsWith(suffix)) {
                str = str.substring(0, str.length() - suffix.length());
            }
            return str;
        }
        return str;
    }

    public static String removeSuffix(String str, String[] suffixes) {
        if (str != null && suffixes != null) {
            String s;
            int i = str.length();
            for (int j = 0; j < suffixes.length && (str = Utils.removeSuffix(str, s = suffixes[j])).length() == i; ++j) {
            }
            return str;
        }
        return str;
    }

    public static String ensurePrefix(String str, String prefix) {
        if (str != null && prefix != null) {
            if (!str.startsWith(prefix)) {
                str = prefix + str;
            }
            return str;
        }
        return str;
    }

    public static boolean equals(Object o1, Object o2) {
        if (o1 == o2) {
            return true;
        }
        return o1 == null ? false : o1.equals(o2);
    }

    public static int parseInt(String str, int defVal) {
        try {
            if (str == null) {
                return defVal;
            }
            str = str.trim();
            return Integer.parseInt((String)str);
        }
        catch (NumberFormatException var3) {
            return defVal;
        }
    }

    public static boolean equalsMask(String str, String mask, char wildChar) {
        if (mask != null && str != null) {
            String s1;
            if (mask.indexOf((int)wildChar) < 0) {
                return mask.equals((Object)str);
            }
            ArrayList list = new ArrayList();
            String s = "" + wildChar;
            if (mask.startsWith(s)) {
                list.add((Object)"");
            }
            StringTokenizer stringtokenizer = new StringTokenizer(mask, s);
            while (stringtokenizer.hasMoreElements()) {
                list.add((Object)stringtokenizer.nextToken());
            }
            if (mask.endsWith(s)) {
                list.add((Object)"");
            }
            if (!str.startsWith(s1 = (String)list.get(0))) {
                return false;
            }
            String s2 = (String)list.get(list.size() - 1);
            if (!str.endsWith(s2)) {
                return false;
            }
            int i = 0;
            for (int j = 0; j < list.size(); ++j) {
                String s3 = (String)list.get(j);
                if (s3.length() <= 0) continue;
                int k = str.indexOf(s3, i);
                if (k < 0) {
                    return false;
                }
                i = k + s3.length();
            }
            return true;
        }
        return mask == str;
    }

    public static Object[] addObjectToArray(Object[] arr, Object obj) {
        if (arr == null) {
            throw new NullPointerException("The given array is NULL");
        }
        int i = arr.length;
        int j = i + 1;
        Object[] aobject = (Object[])Array.newInstance((Class)arr.getClass().getComponentType(), (int)j);
        System.arraycopy((Object)arr, (int)0, (Object)aobject, (int)0, (int)i);
        aobject[i] = obj;
        return aobject;
    }

    public static Object[] addObjectToArray(Object[] arr, Object obj, int index) {
        ArrayList list = new ArrayList((Collection)Arrays.asList((Object[])arr));
        list.add(index, obj);
        Object[] aobject = (Object[])Array.newInstance((Class)arr.getClass().getComponentType(), (int)list.size());
        return list.toArray(aobject);
    }

    public static Object[] addObjectsToArray(Object[] arr, Object[] objs) {
        if (arr == null) {
            throw new NullPointerException("The given array is NULL");
        }
        if (objs.length == 0) {
            return arr;
        }
        int i = arr.length;
        int j = i + objs.length;
        Object[] aobject = (Object[])Array.newInstance((Class)arr.getClass().getComponentType(), (int)j);
        System.arraycopy((Object)arr, (int)0, (Object)aobject, (int)0, (int)i);
        System.arraycopy((Object)objs, (int)0, (Object)aobject, (int)i, (int)objs.length);
        return aobject;
    }

    public static Object[] removeObjectFromArray(Object[] arr, Object obj) {
        ArrayList list = new ArrayList((Collection)Arrays.asList((Object[])arr));
        list.remove(obj);
        Object[] aobject = Utils.collectionToArray((Collection)list, arr.getClass().getComponentType());
        return aobject;
    }

    public static Object[] collectionToArray(Collection coll, Class elementClass) {
        if (coll == null) {
            return null;
        }
        if (elementClass == null) {
            return null;
        }
        if (elementClass.isPrimitive()) {
            throw new IllegalArgumentException("Can not make arrays with primitive elements (int, double), element class: " + (Object)elementClass);
        }
        Object[] aobject = (Object[])Array.newInstance((Class)elementClass, (int)coll.size());
        return coll.toArray(aobject);
    }

    public static enum OS {
        LINUX,
        SOLARIS,
        WINDOWS,
        MACOS,
        UNKNOWN;
        
    }

}

