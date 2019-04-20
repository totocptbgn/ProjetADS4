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
		if (check(TokenKind.TOURNER)){
			eat(TokenKind.TOURNER);
			eat(TokenKind.LPAR);
			Expr ie = parseExpression();
			eat(TokenKind.RPAR);
			return new Commande("Tourner",ie);
		} else if (check(TokenKind.AVANCER)){
			eat(TokenKind.AVANCER);
			eat(TokenKind.LPAR);
			Expr ie = parseExpression();
			eat(TokenKind.RPAR);
			return new Commande("Avancer",ie);
		} else if (check(TokenKind.ECRIRE)){
			eat(TokenKind.ECRIRE);
			eat(TokenKind.LPAR);
			Expr ie = parseExpression();
			eat(TokenKind.RPAR);
			return new Commande("Ecrire",ie);
		} else if (check(TokenKind.IF)){
			eat(TokenKind.IF);
			Expr expr = parseExpression();
			eat(TokenKind.THEN);
			Program prog = parseInProgram();
			return new If(expr, prog);
		} else if (check(TokenKind.WHILE)){
			eat(TokenKind.WHILE);
			Expr expr = parseExpression();
			eat(TokenKind.DO);
			Program prog = parseInProgram();
			return new While(expr, prog);
		} else {
			throw new IOException("Instruction attendue. Trouvé:(" + token.kind + ")" + lexerPos());
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
			} else if (check(TokenKind.AND)){
				eat(TokenKind.AND);
				op = BinOp.AND;
			} else if (check(TokenKind.OR)){
				eat(TokenKind.OR);
				op = BinOp.OR;
			} else if (check(TokenKind.INF)){
				eat(TokenKind.INF);
				op = BinOp.INF;
			} else if (check(TokenKind.SUP)){
				eat(TokenKind.SUP);
				op = BinOp.SUP;
			} else if (check(TokenKind.EQ)){
				eat(TokenKind.EQ);
				op = BinOp.EQ;
			} else {
				throw new IOException("Attendu: BinOP Trouvé: (" + token.kind + ")" + lexerPos());
			}
			Expr expr1 = parseExpression();
			eat(TokenKind.RPAR);
			expr = new Ope(op, expr0, expr1);
		} else if (check(TokenKind.TRUE)){
			eat(TokenKind.TRUE);
			return new True();
		} else if (check(TokenKind.FALSE)){
			eat(TokenKind.FALSE);
			return new False();
		} else {
			throw new IOException("Attendu: (Expression) Trouvé: (" + token.kind + ")" + lexerPos());
		}
		return expr;
	}
}
