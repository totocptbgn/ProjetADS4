public class Token {

	public final TokenKind kind;

	public Token(TokenKind kind) {
		this.kind = kind;
	}

	public int getIntValue(){
		throw new UnsupportedOperationException();
	}

	public String getStringValue() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String toString() {
		return kind.toString();
	}
}

class StringToken extends Token{

	private final String value;

	public StringToken(TokenKind kind, String value) {
		super(kind);
		this.value = value;
	}

	@Override
	public String getStringValue() {
		return value;
	}

	@Override
	public String toString() {
		return kind + "(" + value + ")";
	}
}

class IntToken extends Token{

	private final int value;

	public IntToken(TokenKind kind, int value) {
		super(kind);
		this.value = value;
	}

	@Override
	public int getIntValue() {
		return value;
	}

	@Override
	public String toString() {
		return kind + "(" + value + ")";
	}
}

enum TokenKind {
	 LIRE, INT, SEMICOLON, LPAR, RPAR, MINUS, PLUS, TIMES, DIVIDE, AND, OR,
	 SUP, INF, EQ, NOTEQ, TRUE, FALSE, NOT, IF, ELSE, WHILE, DO, THEN, END, COM , VAR, EOF
}