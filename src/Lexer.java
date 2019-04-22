/* The following code was generated by JFlex 1.7.0 */

/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.7.0
 * from the specification file <tt>Lexer.jflex</tt>
 */
public class Lexer {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;
  public static final int INCOMMENT = 2;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\2\1\52\2\0\1\2\22\0\1\2\1\13\4\0\1\16"+
    "\1\0\1\5\1\6\1\11\1\10\1\0\1\7\1\0\1\12\12\1"+
    "\1\0\1\4\1\26\1\27\1\25\2\0\1\17\2\3\1\46\1\14"+
    "\1\32\2\3\1\40\2\3\1\47\2\3\1\22\1\3\1\44\1\3"+
    "\1\36\1\30\2\3\1\45\3\3\6\0\1\33\1\3\1\51\1\21"+
    "\1\31\1\41\1\3\1\43\1\37\2\3\1\34\1\3\1\20\1\42"+
    "\2\3\1\24\1\35\1\15\1\23\1\50\4\3\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uffff\0\uff95\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\1\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\1\13\1\14\1\4\1\1\2\4"+
    "\1\15\1\16\1\17\7\4\1\3\1\20\1\3\1\21"+
    "\1\22\3\4\1\22\3\4\1\23\6\4\2\24\1\4"+
    "\1\25\1\4\1\26\1\27\15\4\1\30\3\4\1\31"+
    "\2\4\1\32\4\4\1\33\3\4\1\34\1\35\1\36"+
    "\1\4";

  private static int [] zzUnpackAction() {
    int [] result = new int[87];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\53\0\126\0\201\0\126\0\254\0\126\0\126"+
    "\0\126\0\126\0\126\0\126\0\327\0\u0102\0\u012d\0\u0158"+
    "\0\u0183\0\u01ae\0\126\0\126\0\126\0\u01d9\0\u0204\0\u022f"+
    "\0\u025a\0\u0285\0\u02b0\0\u02db\0\u0306\0\126\0\u0331\0\126"+
    "\0\254\0\u035c\0\u0387\0\u03b2\0\126\0\u03dd\0\u0408\0\u0433"+
    "\0\254\0\u045e\0\u0489\0\u04b4\0\u04df\0\u050a\0\u0535\0\u0560"+
    "\0\254\0\u058b\0\254\0\u05b6\0\126\0\254\0\u05e1\0\u060c"+
    "\0\u0637\0\u0662\0\u068d\0\u06b8\0\u06e3\0\u070e\0\u0739\0\u0764"+
    "\0\u078f\0\u07ba\0\u07e5\0\254\0\u0810\0\u083b\0\u0866\0\254"+
    "\0\u0891\0\u08bc\0\254\0\u08e7\0\u0912\0\u093d\0\u0968\0\254"+
    "\0\u0993\0\u09be\0\u09e9\0\254\0\254\0\254\0\u0a14";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[87];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12"+
    "\1\13\1\14\1\15\1\16\1\17\1\6\1\20\1\21"+
    "\2\6\1\22\2\6\1\23\1\24\1\25\1\26\1\6"+
    "\1\27\3\6\1\30\1\6\1\31\4\6\1\32\1\33"+
    "\1\34\2\6\12\5\1\35\41\5\54\0\1\4\54\0"+
    "\1\6\10\0\2\6\1\0\6\6\3\0\22\6\12\0"+
    "\1\36\1\37\67\0\1\40\26\0\1\6\10\0\1\6"+
    "\1\41\1\0\1\6\1\42\4\6\3\0\4\6\1\43"+
    "\14\6\1\44\17\0\1\45\37\0\1\6\10\0\2\6"+
    "\1\0\1\6\1\46\4\6\3\0\4\6\1\47\13\6"+
    "\1\50\1\6\4\0\1\6\10\0\2\6\1\0\4\6"+
    "\2\51\3\0\22\6\4\0\1\6\10\0\2\6\1\0"+
    "\5\6\1\52\3\0\3\6\1\53\6\6\1\54\1\55"+
    "\6\6\4\0\1\6\10\0\2\6\1\0\6\6\3\0"+
    "\3\6\1\56\3\6\1\57\12\6\4\0\1\6\10\0"+
    "\2\6\1\0\6\6\3\0\7\6\1\60\12\6\4\0"+
    "\1\6\10\0\2\6\1\0\6\6\3\0\11\6\1\61"+
    "\10\6\4\0\1\6\10\0\2\6\1\0\6\6\3\0"+
    "\13\6\1\62\6\6\4\0\1\6\10\0\2\6\1\0"+
    "\6\6\3\0\12\6\1\63\7\6\4\0\1\6\10\0"+
    "\2\6\1\0\6\6\3\0\7\6\1\64\12\6\13\0"+
    "\1\65\40\0\52\37\4\0\1\6\10\0\2\6\1\0"+
    "\2\6\1\66\3\6\3\0\22\6\4\0\1\6\10\0"+
    "\2\6\1\0\6\6\3\0\5\6\1\67\14\6\4\0"+
    "\1\6\10\0\2\6\1\0\5\6\1\70\3\0\22\6"+
    "\4\0\1\6\10\0\2\6\1\0\2\6\1\41\3\6"+
    "\3\0\22\6\4\0\1\6\10\0\2\6\1\0\6\6"+
    "\3\0\12\6\1\71\7\6\4\0\1\6\10\0\2\6"+
    "\1\0\6\6\3\0\3\6\1\72\16\6\4\0\1\6"+
    "\10\0\2\6\1\0\4\6\1\73\1\6\3\0\22\6"+
    "\4\0\1\6\10\0\2\6\1\0\1\6\1\74\4\6"+
    "\3\0\22\6\4\0\1\6\10\0\2\6\1\0\4\6"+
    "\1\75\1\6\3\0\22\6\4\0\1\6\10\0\2\6"+
    "\1\0\6\6\3\0\1\6\1\76\20\6\4\0\1\6"+
    "\10\0\2\6\1\0\6\6\3\0\4\6\1\77\2\6"+
    "\1\100\12\6\4\0\1\6\10\0\2\6\1\0\1\6"+
    "\1\66\4\6\3\0\22\6\4\0\1\6\10\0\2\6"+
    "\1\0\1\6\1\101\4\6\3\0\22\6\4\0\1\6"+
    "\10\0\2\6\1\0\6\6\3\0\7\6\1\102\12\6"+
    "\4\0\1\6\10\0\2\6\1\0\5\6\1\103\3\0"+
    "\22\6\4\0\1\6\10\0\2\6\1\0\6\6\3\0"+
    "\1\6\1\104\20\6\4\0\1\6\10\0\2\6\1\0"+
    "\6\6\3\0\7\6\1\105\12\6\4\0\1\6\10\0"+
    "\2\6\1\0\5\6\1\106\3\0\22\6\4\0\1\6"+
    "\10\0\2\6\1\0\1\6\1\107\4\6\3\0\22\6"+
    "\4\0\1\6\10\0\2\6\1\0\6\6\3\0\1\6"+
    "\1\110\20\6\4\0\1\6\10\0\1\6\1\111\1\0"+
    "\6\6\3\0\22\6\4\0\1\6\10\0\2\6\1\0"+
    "\5\6\1\112\3\0\22\6\4\0\1\6\10\0\2\6"+
    "\1\0\1\6\1\113\4\6\3\0\22\6\4\0\1\6"+
    "\10\0\2\6\1\0\6\6\3\0\5\6\1\114\14\6"+
    "\4\0\1\6\10\0\2\6\1\0\5\6\1\115\3\0"+
    "\22\6\4\0\1\6\10\0\2\6\1\0\6\6\3\0"+
    "\12\6\1\116\7\6\4\0\1\6\10\0\2\6\1\0"+
    "\6\6\3\0\4\6\1\117\15\6\4\0\1\6\10\0"+
    "\2\6\1\0\6\6\3\0\1\6\1\120\20\6\4\0"+
    "\1\6\10\0\2\6\1\0\5\6\1\121\3\0\22\6"+
    "\4\0\1\6\10\0\2\6\1\0\6\6\3\0\5\6"+
    "\1\113\14\6\4\0\1\6\10\0\2\6\1\0\6\6"+
    "\3\0\21\6\1\122\4\0\1\6\10\0\2\6\1\0"+
    "\6\6\3\0\14\6\1\123\5\6\4\0\1\6\10\0"+
    "\2\6\1\0\1\6\1\122\4\6\3\0\22\6\4\0"+
    "\1\6\10\0\2\6\1\0\6\6\3\0\1\6\1\124"+
    "\20\6\4\0\1\6\10\0\2\6\1\0\6\6\3\0"+
    "\1\6\1\63\20\6\4\0\1\6\10\0\2\6\1\0"+
    "\1\6\1\104\4\6\3\0\22\6\4\0\1\6\10\0"+
    "\2\6\1\0\6\6\3\0\1\6\1\125\20\6\4\0"+
    "\1\6\10\0\2\6\1\0\6\6\3\0\1\6\1\126"+
    "\20\6\4\0\1\6\10\0\2\6\1\0\6\6\3\0"+
    "\1\6\1\127\20\6\4\0\1\6\10\0\2\6\1\0"+
    "\4\6\1\117\1\6\3\0\22\6\4\0\1\6\10\0"+
    "\2\6\1\0\5\6\1\126\3\0\22\6\1\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[2623];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unknown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\1\1\1\11\1\1\6\11\6\1\3\11"+
    "\10\1\1\11\1\1\1\11\4\1\1\11\17\1\1\11"+
    "\42\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[87];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true iff the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true iff the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;
  
  /** 
   * The number of occupied positions in zzBuffer beyond zzEndRead.
   * When a lead/high surrogate has been read from the input stream
   * into the final zzBuffer position, this will have a value of 1;
   * otherwise, it will have a value of 0.
   */
  private int zzFinalHighSurrogate = 0;

  /* user code: */
	public int getColumn() { return yycolumn; }
	public int getLine() { return yyline; }
	public int getChar() { return yychar; }


  /**
   * Creates a new scanner
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Lexer(java.io.Reader in) {
    this.zzReader = in;
  }


  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x110000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 168) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length - zzFinalHighSurrogate) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzBuffer.length*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
      zzEndRead += zzFinalHighSurrogate;
      zzFinalHighSurrogate = 0;
    }

    /* fill the buffer with new input */
    int requested = zzBuffer.length - zzEndRead;
    int numRead = zzReader.read(zzBuffer, zzEndRead, requested);

    /* not supposed to occur according to specification of java.io.Reader */
    if (numRead == 0) {
      throw new java.io.IOException("Reader returned 0 characters. See JFlex examples for workaround.");
    }
    if (numRead > 0) {
      zzEndRead += numRead;
      /* If numRead == requested, we might have requested to few chars to
         encode a full Unicode character. We assume that a Reader would
         otherwise never return half characters. */
      if (numRead == requested) {
        if (Character.isHighSurrogate(zzBuffer[zzEndRead - 1])) {
          --zzEndRead;
          zzFinalHighSurrogate = 1;
        }
      }
      /* potentially more input available */
      return false;
    }

    /* numRead < 0 ==> end of stream */
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * Internal scan buffer is resized down to its initial length, if it has grown.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    zzFinalHighSurrogate = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
    if (zzBuffer.length > ZZ_BUFFERSIZE)
      zzBuffer = new char[ZZ_BUFFERSIZE];
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public Token yylex() throws java.io.IOException, java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      yychar+= zzMarkedPosL-zzStartRead;

      boolean zzR = false;
      int zzCh;
      int zzCharCount;
      for (zzCurrentPosL = zzStartRead  ;
           zzCurrentPosL < zzMarkedPosL ;
           zzCurrentPosL += zzCharCount ) {
        zzCh = Character.codePointAt(zzBufferL, zzCurrentPosL, zzMarkedPosL);
        zzCharCount = Character.charCount(zzCh);
        switch (zzCh) {
        case '\u000B':  // fall through
        case '\u000C':  // fall through
        case '\u0085':  // fall through
        case '\u2028':  // fall through
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn += zzCharCount;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];

      // set up zzAction for empty match case:
      int zzAttributes = zzAttrL[zzState];
      if ( (zzAttributes & 1) == 1 ) {
        zzAction = zzState;
      }


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL) {
            zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
            zzCurrentPosL += Character.charCount(zzInput);
          }
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = Character.codePointAt(zzBufferL, zzCurrentPosL, zzEndReadL);
              zzCurrentPosL += Character.charCount(zzInput);
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
        zzAtEOF = true;
            switch (zzLexicalState) {
            case YYINITIAL: {
              return new Token(TokenKind.EOF);
            }  // fall though
            case 88: break;
            case INCOMMENT: {
              return new Token(TokenKind.EOF);
            }  // fall though
            case 89: break;
            default:
        return null;
        }
      }
      else {
        switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
          case 1: 
            { throw new java.io.IOException("Symbole non reconnu (" + yytext() + "");
            } 
            // fall through
          case 31: break;
          case 2: 
            { return new IntToken(TokenKind.INT, Integer.parseInt(yytext()));
            } 
            // fall through
          case 32: break;
          case 3: 
            { 
            } 
            // fall through
          case 33: break;
          case 4: 
            { return new StringToken(TokenKind.VAR, yytext());
            } 
            // fall through
          case 34: break;
          case 5: 
            { return new Token(TokenKind.SEMICOLON);
            } 
            // fall through
          case 35: break;
          case 6: 
            { return new Token(TokenKind.LPAR);
            } 
            // fall through
          case 36: break;
          case 7: 
            { return new Token(TokenKind.RPAR);
            } 
            // fall through
          case 37: break;
          case 8: 
            { return new Token(TokenKind.MINUS);
            } 
            // fall through
          case 38: break;
          case 9: 
            { return new Token(TokenKind.PLUS);
            } 
            // fall through
          case 39: break;
          case 10: 
            { return new Token(TokenKind.TIMES);
            } 
            // fall through
          case 40: break;
          case 11: 
            { return new Token(TokenKind.DIVIDE);
            } 
            // fall through
          case 41: break;
          case 12: 
            { return new Token(TokenKind.NOT);
            } 
            // fall through
          case 42: break;
          case 13: 
            { return new Token(TokenKind.SUP);
            } 
            // fall through
          case 43: break;
          case 14: 
            { return new Token(TokenKind.INF);
            } 
            // fall through
          case 44: break;
          case 15: 
            { return new Token(TokenKind.EQ);
            } 
            // fall through
          case 45: break;
          case 16: 
            { yybegin(INCOMMENT);
            } 
            // fall through
          case 46: break;
          case 17: 
            { return new Token(TokenKind.NOTEQ);
            } 
            // fall through
          case 47: break;
          case 18: 
            { return new Token(TokenKind.AND);
            } 
            // fall through
          case 48: break;
          case 19: 
            { return new Token(TokenKind.OR);
            } 
            // fall through
          case 49: break;
          case 20: 
            { return new Token(TokenKind.IF);
            } 
            // fall through
          case 50: break;
          case 21: 
            { return new Token(TokenKind.DO);
            } 
            // fall through
          case 51: break;
          case 22: 
            { yybegin(YYINITIAL);
            } 
            // fall through
          case 52: break;
          case 23: 
            { return new Token(TokenKind.END);
            } 
            // fall through
          case 53: break;
          case 24: 
            { return new Token(TokenKind.ELSE);
            } 
            // fall through
          case 54: break;
          case 25: 
            { return new Token(TokenKind.TRUE);
            } 
            // fall through
          case 55: break;
          case 26: 
            { return new Token(TokenKind.THEN);
            } 
            // fall through
          case 56: break;
          case 27: 
            { return new Token(TokenKind.LIRE);
            } 
            // fall through
          case 57: break;
          case 28: 
            { return new Token(TokenKind.FALSE);
            } 
            // fall through
          case 58: break;
          case 29: 
            { return new Token(TokenKind.WHILE);
            } 
            // fall through
          case 59: break;
          case 30: 
            { return new StringToken(TokenKind.COM,yytext());
            } 
            // fall through
          case 60: break;
          default:
            zzScanError(ZZ_NO_MATCH);
        }
      }
    }
  }


}
