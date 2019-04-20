import java.io.IOException;
import java.io.Reader;

public class SmartParser implements Parser {

	private Lexer lexer;
	private Token token;

	public SmartParser(Reader reader) {
		this.lexer = new Lexer(reader);
		this.token = null;
		try {
			next();
		} catch (IOException ignored) {}
	}

	public String lexerPos() {
		return " Column: " + lexer.getColumn() + " Line: " + lexer.getLine() + " Char: " + lexer.getChar();
	}

	public boolean check(TokenKind tokenTest){
		return tokenTest == this.token.kind;
	}

	public void next() throws IOException {
		this.token = lexer.yylex();
	}

	public void eat(TokenKind tokenTest) throws IOException {
		if (!check(tokenTest)) throw new IOException("Attendu: (" + tokenTest + ") Trouvé: (" + token.kind + ")" + lexerPos());
		next();
	}

	@Override
	public Program parseProgram(String exeName, Reader reader) throws IOException {
		if (check(TokenKind.EOF)){
			return new Program();
		} else {
			Instr instr = parseInstruction();
			eat(TokenKind.SEMICOLON);
			Program p = parseProgram(exeName, reader);
			p.add(instr);
			return p;
		}
	}

	public Program parseInProgram() throws IOException {
		if (check(TokenKind.END)){
			eat(TokenKind.END);
			return new Program();
		} else {
			Instr instr = parseInstruction();
			eat(TokenKind.SEMICOLON);
			Program p = parseInProgram();
			p.add(instr);
			return p;
		}
	}

	private Instr parseInstruction() throws IOException {
		if (check(TokenKind.CMD)){
			String name = token.getStringValue();
			eat(TokenKind.CMD);
			eat(TokenKind.LPAR);
			Expr ie = parseExpression();
			eat(TokenKind.RPAR);
			return new Commande(name,ie);
		} else if (check(TokenKind.IF)){
			eat(TokenKind.IF);
			parseExpression();
			eat(TokenKind.THEN);
			parseInProgram();
			return null; // A COMPLETER
		} else {
			throw new IOException("Instruction attendu. Trouvé:(" + token.kind + ")" + lexerPos());
		}
	}

	private Expr parseExpression() throws IOException {
		Expr expr;
		if (check(TokenKind.LIRE)){
			eat(TokenKind.LIRE);
			expr = new Lire();
		} else if (check(TokenKind.MINUS)){
			eat(TokenKind.MINUS);
			Expr minusExpr = parseExpression();
			expr = new Minus(minusExpr);
		} else if (check(TokenKind.INT)){
			expr = new PosInt(token.getIntValue());
			eat(TokenKind.INT);
		} else if (check(TokenKind.LPAR)){
			eat(TokenKind.LPAR);
			Expr expr0 = parseExpression();
			BinOp op;
			if (check(TokenKind.PLUS)){
				eat(TokenKind.PLUS);
				op = BinOp.PLUS;
			} else if (check(TokenKind.MINUS)){
				eat(TokenKind.MINUS);
				op = BinOp.MINUS;
			} else {
				throw new IOException("Attendu: (MINUS ou PLUS) Trouvé: (" + token.kind + ")" + lexerPos());
			}
			Expr expr1 = parseExpression();
			eat(TokenKind.RPAR);
			expr = new Ope(op, expr0, expr1);
		} else {
			throw new IOException("Attendu: (Expression) Trouvé: (" + token.kind + ")" + lexerPos());
		}
		return expr;
	}
}
