import java.io.IOException;
import java.io.Reader;

public class SmartParser implements Parser {

	private Lexer lexer;
	private Token token;
	private int numero=0;
	public SmartParser(Reader reader) {
		this.lexer = new Lexer(reader);
		this.token = new Token(TokenKind.OPEN);
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
		System.out.println(this.token.kind);
		if (!check(tokenTest)) throw new IOException("Attendu: (" + tokenTest + ") Trouvé: (" + token.kind + ")" + lexerPos());
		if(token.islastcopy()) {
			next();
		}
		else {
			token.useOneCopy();
		}
	}

	@Override
	public Program parseProgram(String exeName, Reader reader) throws IOException {
		if (check(TokenKind.EOF)){
			
			return new Program();
		} else if(check(TokenKind.OPEN)) {
			
			eat(TokenKind.OPEN);
			Block block = parseBlock();
			Program p = parseProgram(exeName, reader);
			p.add(block);
			return p;
		}
		else {
			throw new IOException("Impossible");
		}
	}
	
	private Block parseBlock() throws IOException {
		if(check(TokenKind.CLOSE)) {
			eat(TokenKind.CLOSE);
			return new Block();
		}
		else if(check(TokenKind.EOF)) {
			eat(TokenKind.EOF);
			return new Block();
		}
		else {
			Instr instr = parseInstruction();
			eat(TokenKind.SEMICOLON);
			Block b = parseBlock();
			b.add(instr);
			return b;
			
		}
	}
	
	private Program parseInProgram() throws IOException {
		if (check(TokenKind.END)){
			eat(TokenKind.END);
			return new Program();
		} else if(check(TokenKind.OPEN)) {
			
			eat(TokenKind.OPEN);
			Block block = parseBlock();
			Program p = parseInProgram();
			p.add(block);
			return p;
		}
		else {
			System.out.println(this.token.kind);
			throw new IOException("Indentation manquante");
		}
	}

	private Instr parseInstruction() throws IOException {
		if (check(TokenKind.COM)){
			String commande = token.getStringValue();
			eat(TokenKind.COM);
			eat(TokenKind.LPAR);
			Expr ie = parseExpression();
			eat(TokenKind.RPAR);
			return new Commande(commande, ie);
		} else if (check(TokenKind.IF)){
			eat(TokenKind.IF);
			Expr expr = parseExpression();
			eat(TokenKind.THEN);
			Program prog = parseInProgram();
			
			Program bodyprog = parseElse();
			return new If(expr, prog, bodyprog);
		} else if (check(TokenKind.WHILE)){
			eat(TokenKind.WHILE);
			Expr expr = parseExpression();
			eat(TokenKind.THEN);
			Program prog = parseInProgram();
			return new While(expr, prog);
		} else if (check(TokenKind.VAR)){
			String name = token.getStringValue();
			eat(TokenKind.VAR);
			eat(TokenKind.EQ);
			Expr expr = parseExpression();
			return new Assign(name, expr);
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
			Expr expression = parseExpression();
			expr = new Minus(expression);
		} else if (check(TokenKind.NOT)){
			eat(TokenKind.NOT);
			Expr expression = parseExpression();
			expr = new Not(expression);
		} else if (check(TokenKind.INT)){
			expr = new PosInt(token.getIntValue());
			eat(TokenKind.INT);
		} else if (check(TokenKind.LPAR)){
			eat(TokenKind.LPAR);
			Expr expr0 = parseExpression();
			BinOp op=parseBinOp();
			Expr expr1 = parseExpression();
			eat(TokenKind.RPAR);
			expr = new Ope(op, expr0, expr1);
		} else if (check(TokenKind.TRUE)){
			eat(TokenKind.TRUE);
			return new True();
		} else if (check(TokenKind.FALSE)){
			eat(TokenKind.FALSE);
			return new False();
		} else if (check(TokenKind.VAR)){
			String name = token.getStringValue();
			eat(TokenKind.VAR);
			return new Var(name);
		} else {
			throw new IOException("Attendu: (Expression) Trouvé: (" + token.kind + ")" + lexerPos());
		}
		return expr;
	}
	
	private BinOp parseBinOp() throws IOException {
		if (check(TokenKind.PLUS)){
			eat(TokenKind.PLUS);
			return BinOp.PLUS;
		} else if (check(TokenKind.MINUS)){
			eat(TokenKind.MINUS);
			return BinOp.MINUS;
		} else if (check(TokenKind.AND)){
			eat(TokenKind.AND);
			return BinOp.AND;
		} else if (check(TokenKind.OR)){
			eat(TokenKind.OR);
			return BinOp.OR;
		} else if (check(TokenKind.INF)){
			eat(TokenKind.INF);
			return BinOp.INF;
		} else if (check(TokenKind.SUP)){
			eat(TokenKind.SUP);
			return BinOp.SUP;
		} else if (check(TokenKind.EQ)){
			eat(TokenKind.EQ);
			return BinOp.EQ;
		} else if (check(TokenKind.NOTEQ)) {
			eat(TokenKind.NOTEQ);
			return BinOp.NOTEQ;
		} else if (check(TokenKind.TIMES)){
			eat(TokenKind.TIMES);
			return BinOp.TIMES;
		} else if (check(TokenKind.DIVIDE)){
			eat(TokenKind.DIVIDE);
			return BinOp.DIVIDE;
		} else {
			throw new IOException("Attendu: BinOP Trouvé: (" + token.kind + ")" + lexerPos());
		}
	}
	
	public Program parseElse() throws IOException {
		if (check(TokenKind.SEMICOLON)){
			return null;
		} else if (check(TokenKind.ELSE)){
			eat(TokenKind.ELSE);
			eat(TokenKind.THEN);
			return this.parseInProgram();
		}
		else {
			throw new IOException("Attendu: Else ou ; Trouvé: (" + token.kind + ")" + lexerPos());
		}
	}
}
