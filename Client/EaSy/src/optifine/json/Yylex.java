/*
 * Decompiled with CFR 0.0.
 * 
 * Could not load the following classes:
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.lang.ArrayIndexOutOfBoundsException
 *  java.lang.Boolean
 *  java.lang.Character
 *  java.lang.Double
 *  java.lang.Error
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuffer
 *  java.lang.System
 */
package optifine.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import optifine.json.ParseException;
import optifine.json.Yytoken;

class Yylex {
    public static final int YYEOF = -1;
    private static final int ZZ_BUFFERSIZE = 16384;
    public static final int YYINITIAL = 0;
    public static final int STRING_BEGIN = 2;
    private static final int[] ZZ_LEXSTATE = new int[]{0, 0, 1, 1};
    private static final String ZZ_CMAP_PACKED = "\t\u0000\u0001\u0007\u0001\u0007\u0002\u0000\u0001\u0007\u0012\u0000\u0001\u0007\u0001\u0000\u0001\t\b\u0000\u0001\u0006\u0001\u0019\u0001\u0002\u0001\u0004\u0001\n\n\u0003\u0001\u001a\u0006\u0000\u0004\u0001\u0001\u0005\u0001\u0001\u0014\u0000\u0001\u0017\u0001\b\u0001\u0018\u0003\u0000\u0001\u0012\u0001\u000b\u0002\u0001\u0001\u0011\u0001\f\u0005\u0000\u0001\u0013\u0001\u0000\u0001\r\u0003\u0000\u0001\u000e\u0001\u0014\u0001\u000f\u0001\u0010\u0005\u0000\u0001\u0015\u0001\u0000\u0001\u0016\uff82\u0000";
    private static final char[] ZZ_CMAP = Yylex.zzUnpackCMap("\t\u0000\u0001\u0007\u0001\u0007\u0002\u0000\u0001\u0007\u0012\u0000\u0001\u0007\u0001\u0000\u0001\t\b\u0000\u0001\u0006\u0001\u0019\u0001\u0002\u0001\u0004\u0001\n\n\u0003\u0001\u001a\u0006\u0000\u0004\u0001\u0001\u0005\u0001\u0001\u0014\u0000\u0001\u0017\u0001\b\u0001\u0018\u0003\u0000\u0001\u0012\u0001\u000b\u0002\u0001\u0001\u0011\u0001\f\u0005\u0000\u0001\u0013\u0001\u0000\u0001\r\u0003\u0000\u0001\u000e\u0001\u0014\u0001\u000f\u0001\u0010\u0005\u0000\u0001\u0015\u0001\u0000\u0001\u0016\uff82\u0000");
    private static final int[] ZZ_ACTION = Yylex.zzUnpackAction();
    private static final String ZZ_ACTION_PACKED_0 = "\u0002\u0000\u0002\u0001\u0001\u0002\u0001\u0003\u0001\u0004\u0003\u0001\u0001\u0005\u0001\u0006\u0001\u0007\u0001\b\u0001\t\u0001\n\u0001\u000b\u0001\f\u0001\r\u0005\u0000\u0001\f\u0001\u000e\u0001\u000f\u0001\u0010\u0001\u0011\u0001\u0012\u0001\u0013\u0001\u0014\u0001\u0000\u0001\u0015\u0001\u0000\u0001\u0015\u0004\u0000\u0001\u0016\u0001\u0017\u0002\u0000\u0001\u0018";
    private static final int[] ZZ_ROWMAP = Yylex.zzUnpackRowMap();
    private static final String ZZ_ROWMAP_PACKED_0 = "\u0000\u0000\u0000\u001b\u00006\u0000Q\u0000l\u0000\u0087\u00006\u0000\u00a2\u0000\u00bd\u0000\u00d8\u00006\u00006\u00006\u00006\u00006\u00006\u0000\u00f3\u0000\u010e\u00006\u0000\u0129\u0000\u0144\u0000\u015f\u0000\u017a\u0000\u0195\u00006\u00006\u00006\u00006\u00006\u00006\u00006\u00006\u0000\u01b0\u0000\u01cb\u0000\u01e6\u0000\u01e6\u0000\u0201\u0000\u021c\u0000\u0237\u0000\u0252\u00006\u00006\u0000\u026d\u0000\u0288\u00006";
    private static final int[] ZZ_TRANS = new int[]{2, 2, 3, 4, 2, 2, 2, 5, 2, 6, 2, 2, 7, 8, 2, 9, 2, 2, 2, 2, 2, 10, 11, 12, 13, 14, 15, 16, 16, 16, 16, 16, 16, 16, 16, 17, 18, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 4, 19, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 5, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 21, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 22, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 23, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 16, 16, 16, 16, 16, 16, 16, 16, -1, -1, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1, -1, -1, -1, -1, -1, -1, -1, 24, 25, 26, 27, 28, 29, 30, 31, 32, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 33, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 34, 35, -1, -1, 34, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 37, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 38, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 39, -1, 39, -1, 39, -1, -1, -1, -1, -1, 39, 39, -1, -1, -1, -1, 39, 39, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 33, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 20, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 35, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 38, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 40, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 41, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 42, -1, 42, -1, 42, -1, -1, -1, -1, -1, 42, 42, -1, -1, -1, -1, 42, 42, -1, -1, -1, -1, -1, -1, -1, -1, -1, 43, -1, 43, -1, 43, -1, -1, -1, -1, -1, 43, 43, -1, -1, -1, -1, 43, 43, -1, -1, -1, -1, -1, -1, -1, -1, -1, 44, -1, 44, -1, 44, -1, -1, -1, -1, -1, 44, 44, -1, -1, -1, -1, 44, 44, -1, -1, -1, -1, -1, -1, -1, -1};
    private static final int ZZ_UNKNOWN_ERROR = 0;
    private static final int ZZ_NO_MATCH = 1;
    private static final int ZZ_PUSHBACK_2BIG = 2;
    private static final String[] ZZ_ERROR_MSG = new String[]{"Unkown internal scanner error", "Error: could not match input", "Error: pushback value was too large"};
    private static final int[] ZZ_ATTRIBUTE = Yylex.zzUnpackAttribute();
    private static final String ZZ_ATTRIBUTE_PACKED_0 = "\u0002\u0000\u0001\t\u0003\u0001\u0001\t\u0003\u0001\u0006\t\u0002\u0001\u0001\t\u0005\u0000\b\t\u0001\u0000\u0001\u0001\u0001\u0000\u0001\u0001\u0004\u0000\u0002\t\u0002\u0000\u0001\t";
    private Reader zzReader;
    private int zzState;
    private int zzLexicalState = 0;
    private char[] zzBuffer = new char[16384];
    private int zzMarkedPos;
    private int zzCurrentPos;
    private int zzStartRead;
    private int zzEndRead;
    private int yyline;
    private int yychar;
    private int yycolumn;
    private boolean zzAtBOL = true;
    private boolean zzAtEOF;
    private StringBuffer sb = new StringBuffer();

    private static int[] zzUnpackAction() {
        int[] aint = new int[45];
        int i = 0;
        Yylex.zzUnpackAction(ZZ_ACTION_PACKED_0, i, aint);
        return aint;
    }

    private static int zzUnpackAction(String packed, int offset, int[] result) {
        int i = 0;
        int j = offset;
        int k = packed.length();
        while (i < k) {
            int l = packed.charAt(i++);
            char i1 = packed.charAt(i++);
            do {
                result[j++] = i1;
            } while (l > 0);
            --l;
        }
        return j;
    }

    private static int[] zzUnpackRowMap() {
        int[] aint = new int[45];
        int i = 0;
        Yylex.zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, i, aint);
        return aint;
    }

    private static int zzUnpackRowMap(String packed, int offset, int[] result) {
        int i = 0;
        int j = offset;
        int k = packed.length();
        while (i < k) {
            int l = packed.charAt(i++) << 16;
            result[j++] = l | packed.charAt(i++);
        }
        return j;
    }

    private static int[] zzUnpackAttribute() {
        int[] aint = new int[45];
        int i = 0;
        Yylex.zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, i, aint);
        return aint;
    }

    private static int zzUnpackAttribute(String packed, int offset, int[] result) {
        int i = 0;
        int j = offset;
        int k = packed.length();
        while (i < k) {
            int l = packed.charAt(i++);
            char i1 = packed.charAt(i++);
            do {
                result[j++] = i1;
            } while (l > 0);
            --l;
        }
        return j;
    }

    int getPosition() {
        return this.yychar;
    }

    Yylex(Reader in) {
        this.zzReader = in;
    }

    Yylex(InputStream in) {
        this((Reader)new InputStreamReader(in));
    }

    private static char[] zzUnpackCMap(String packed) {
        char[] achar = new char[65536];
        int i = 0;
        int j = 0;
        while (i < 90) {
            int k = packed.charAt(i++);
            char c0 = packed.charAt(i++);
            do {
                achar[j++] = c0;
            } while (k > 0);
            --k;
        }
        return achar;
    }

    private boolean zzRefill() throws IOException {
        int j;
        if (this.zzStartRead > 0) {
            System.arraycopy((Object)this.zzBuffer, (int)this.zzStartRead, (Object)this.zzBuffer, (int)0, (int)(this.zzEndRead - this.zzStartRead));
            this.zzEndRead -= this.zzStartRead;
            this.zzCurrentPos -= this.zzStartRead;
            this.zzMarkedPos -= this.zzStartRead;
            this.zzStartRead = 0;
        }
        if (this.zzCurrentPos >= this.zzBuffer.length) {
            char[] achar = new char[this.zzCurrentPos * 2];
            System.arraycopy((Object)this.zzBuffer, (int)0, (Object)achar, (int)0, (int)this.zzBuffer.length);
            this.zzBuffer = achar;
        }
        if ((j = this.zzReader.read(this.zzBuffer, this.zzEndRead, this.zzBuffer.length - this.zzEndRead)) > 0) {
            this.zzEndRead += j;
            return false;
        }
        if (j == 0) {
            int i = this.zzReader.read();
            if (i == -1) {
                return true;
            }
            this.zzBuffer[this.zzEndRead++] = (char)i;
            return false;
        }
        return true;
    }

    public final void yyclose() throws IOException {
        this.zzAtEOF = true;
        this.zzEndRead = this.zzStartRead;
        if (this.zzReader != null) {
            this.zzReader.close();
        }
    }

    public final void yyreset(Reader reader) {
        this.zzReader = reader;
        this.zzAtBOL = true;
        this.zzAtEOF = false;
        this.zzStartRead = 0;
        this.zzEndRead = 0;
        this.zzMarkedPos = 0;
        this.zzCurrentPos = 0;
        this.yycolumn = 0;
        this.yychar = 0;
        this.yyline = 0;
        this.zzLexicalState = 0;
    }

    public final int yystate() {
        return this.zzLexicalState;
    }

    public final void yybegin(int newState) {
        this.zzLexicalState = newState;
    }

    public final String yytext() {
        return new String(this.zzBuffer, this.zzStartRead, this.zzMarkedPos - this.zzStartRead);
    }

    public final char yycharat(int pos) {
        return this.zzBuffer[this.zzStartRead + pos];
    }

    public final int yylength() {
        return this.zzMarkedPos - this.zzStartRead;
    }

    private void zzScanError(int errorCode) {
        String s;
        try {
            s = ZZ_ERROR_MSG[errorCode];
        }
        catch (ArrayIndexOutOfBoundsException var4) {
            s = ZZ_ERROR_MSG[0];
        }
        throw new Error(s);
    }

    public void yypushback(int number) {
        if (number > this.yylength()) {
            this.zzScanError(2);
        }
        this.zzMarkedPos -= number;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public Yytoken yylex() throws IOException, ParseException {
        l = this.zzEndRead;
        achar = this.zzBuffer;
        achar1 = Yylex.ZZ_CMAP;
        aint = Yylex.ZZ_TRANS;
        aint1 = Yylex.ZZ_ROWMAP;
        aint2 = Yylex.ZZ_ATTRIBUTE;
        block28 : do {
            k = this.zzMarkedPos;
            this.yychar += k - this.zzStartRead;
            j = -1;
            this.zzCurrentPos = this.zzStartRead = k;
            j1 = this.zzStartRead;
            this.zzState = Yylex.ZZ_LEXSTATE[this.zzLexicalState];
            do lbl-1000: // 3 sources:
            {
                if (j1 < l) {
                    i = achar[j1++];
                } else {
                    if (this.zzAtEOF) {
                        i = -1;
                        break;
                    }
                    this.zzCurrentPos = j1;
                    this.zzMarkedPos = k;
                    flag = this.zzRefill();
                    j1 = this.zzCurrentPos;
                    k = this.zzMarkedPos;
                    achar = this.zzBuffer;
                    l = this.zzEndRead;
                    if (flag) {
                        i = -1;
                        break;
                    }
                    i = achar[j1++];
                }
                k1 = aint[aint1[this.zzState] + achar1[i]];
                if (k1 == -1) break;
                this.zzState = k1;
                i1 = aint2[this.zzState];
                if ((i1 & 1) != 1) ** GOTO lbl-1000
                j = this.zzState;
                k = j1;
            } while ((i1 & 8) != 8);
            this.zzMarkedPos = k;
            switch (j < 0 ? j : Yylex.ZZ_ACTION[j]) {
                case 1: {
                    throw new ParseException(this.yychar, 0, (Object)new Character(this.yycharat(0)));
                }
                case 2: {
                    olong = Long.valueOf((String)this.yytext());
                    return new Yytoken(0, (Object)olong);
                }
                case 3: 
                case 25: 
                case 26: 
                case 27: 
                case 28: 
                case 29: 
                case 30: 
                case 31: 
                case 32: 
                case 33: 
                case 34: 
                case 35: 
                case 36: 
                case 37: 
                case 38: 
                case 39: 
                case 40: 
                case 41: 
                case 42: 
                case 43: 
                case 44: 
                case 45: 
                case 46: 
                case 47: 
                case 48: {
                    continue block28;
                }
                case 4: {
                    this.sb.delete(0, this.sb.length());
                    this.yybegin(2);
                    continue block28;
                }
                case 5: {
                    return new Yytoken(1, null);
                }
                case 6: {
                    return new Yytoken(2, null);
                }
                case 7: {
                    return new Yytoken(3, null);
                }
                case 8: {
                    return new Yytoken(4, null);
                }
                case 9: {
                    return new Yytoken(5, null);
                }
                case 10: {
                    return new Yytoken(6, null);
                }
                case 11: {
                    this.sb.append(this.yytext());
                    continue block28;
                }
                case 12: {
                    this.sb.append('\\');
                    continue block28;
                }
                case 13: {
                    this.yybegin(0);
                    return new Yytoken(0, this.sb.toString());
                }
                case 14: {
                    this.sb.append('\"');
                    continue block28;
                }
                case 15: {
                    this.sb.append('/');
                    continue block28;
                }
                case 16: {
                    this.sb.append('\b');
                    continue block28;
                }
                case 17: {
                    this.sb.append('\f');
                    continue block28;
                }
                case 18: {
                    this.sb.append('\n');
                    continue block28;
                }
                case 19: {
                    this.sb.append('\r');
                    continue block28;
                }
                case 20: {
                    this.sb.append('\t');
                    continue block28;
                }
                case 21: {
                    d0 = Double.valueOf((String)this.yytext());
                    return new Yytoken(0, (Object)d0);
                }
                case 22: {
                    return new Yytoken(0, null);
                }
                case 23: {
                    obool = Boolean.valueOf((String)this.yytext());
                    return new Yytoken(0, (Object)obool);
                }
                case 24: {
                    try {
                        l1 = Integer.parseInt((String)this.yytext().substring(2), (int)16);
                        this.sb.append((char)l1);
                        continue block28;
                    }
                    catch (Exception exception) {
                        throw new ParseException(this.yychar, 2, (Object)exception);
                    }
                }
            }
            if (i == -1 && this.zzStartRead == this.zzCurrentPos) {
                this.zzAtEOF = true;
                return null;
            }
            this.zzScanError(1);
        } while (true);
    }
}

