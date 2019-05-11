import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

public class SmartParser implements Parser {

	private static Lexer lexer;
	private Token token;

	public SmartParser(Reader reader) {
		lexer = new Lexer(reader);
		this.token = new Token(TokenKind.OPEN);
	}

	public static String lexerPos() {
		return " Column: " + lexer.getColumn() + " Line: " + lexer.getLine() + " Char: " + lexer.getChar();
	}

	public boolean check(TokenKind tokenTest){
		return tokenTest == this.token.kind;
	}

	public void next() throws IOException {
		//System.out.println(this.token);
		this.token = lexer.yylex();
	}

	public void eat(TokenKind tokenTest) throws IOException {
		if (!check(tokenTest)) throw new IOException("Attendu: (" + tokenTest + ") Trouvé: (" + token.kind + ")" + lexerPos());
		if (token.islastcopy()) {
			next();
		} else {
			token.useOneCopy();
		}
	}

	@Override
	public Program parseProgram(String exeName, Reader reader) throws IOException {
		if (check(TokenKind.EOF)){
			return new Program();
} else if(check(TokenKind.COM) || check(TokenKind.IF) || check(TokenKind.WHILE) || check(TokenKind.VAR) || check(TokenKind.OPEN) || check(TokenKind.DEF) || check(TokenKind.RETURN) || check(TokenKind.NEW) || check(TokenKind.TRY)) {
			Instr instr = parseInstruction();
			Program p = parseProgram(exeName, reader);
			p.add(instr);
			return p;
		}
		else {
			throw new IOException("Nouvelle instruction attendue.");
		}
	}

	private Block parseBlock() throws IOException {
		if (check(TokenKind.CLOSE)) {
			eat(TokenKind.CLOSE);
			return new Block();
		} else if (check(TokenKind.EOF)) {
			eat(TokenKind.EOF);
			return new Block();
} else if (check(TokenKind.NEW) || check(TokenKind.COM) || check(TokenKind.IF) || check(TokenKind.WHILE) || check(TokenKind.VAR) || check(TokenKind.OPEN) || check(TokenKind.DEF) || check(TokenKind.RETURN) || check(TokenKind.TRY)) {			Instr instr = parseInstruction();
			Block b = parseBlock();
			b.add(instr);
			return b;
		} else {
			throw new IOException("Attendu: CLOSE ou Instruction Trouvé: (" + token.kind + ")" + lexerPos());
		}
	}

	private Program parseInProgram() throws IOException {
		if (check(TokenKind.END)){
			eat(TokenKind.END);
			return new Program();
		} else if (check(TokenKind.NEW) || check(TokenKind.COM) || check(TokenKind.IF) || check(TokenKind.WHILE) || check(TokenKind.VAR) || check(TokenKind.OPEN) || check(TokenKind.DEF) || check(TokenKind.RETURN)) {
			Instr instr = parseInstruction();
			Program p = parseInProgram();
			p.add(instr);
			return p;
		} else {
			if (check(TokenKind.CLOSE)) {
				throw new IOException("Ouverture du block en dehors de la boucle : Le block ne peut pas être fermé ici " + lexerPos());
			}
			throw new IOException("Attendu: END ou Instruction Trouvé: (" + token.kind + ")" + lexerPos());
		}
	}

	private Instr parseInstruction() throws IOException {
		if (check(TokenKind.COM)){
			String commande = token.getStringValue();
			eat(TokenKind.COM);
			eat(TokenKind.LPAR);
			Expr ie = parseExpression();
			eat(TokenKind.RPAR);
			eat(TokenKind.SEMICOLON);
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
			boolean isAssign=false;
			if(check(TokenKind.EQ))
				isAssign=true;
			ArrayList<Expr> expr=parseVarBis();
			Instr i;
			if(isAssign)
				i= new Assign(name, expr.get(0));
			else
				i= new Call(name,expr);
			eat(TokenKind.SEMICOLON);
			return i;
		} else if (check(TokenKind.NEW)){
			eat(TokenKind.NEW);
			String name = token.getStringValue();
			eat(TokenKind.VAR);
			eat(TokenKind.EQ);
			Expr expr = parseExpression();
			eat(TokenKind.SEMICOLON);
			return new New(name, expr);
		} else if (check(TokenKind.OPEN)){
			eat(TokenKind.OPEN);
			return parseBlock();
		}
		else if(check(TokenKind.DEF)) {
			eat(TokenKind.DEF);
			String name = token.getStringValue();
			eat(TokenKind.VAR);
			eat(TokenKind.LPAR);
			ArrayList<String> attributs=parseAttributs();
			eat(TokenKind.RPAR);
			eat(TokenKind.DOUBLEPOINT);
			eat(TokenKind.OPEN);
			Block b=parseBlock();
			return new Fonction(name,attributs,b);
		}
		else if(check(TokenKind.RETURN)) {
			eat(TokenKind.RETURN);
			Return ret= new Return(parseExpression());
			eat(TokenKind.SEMICOLON);
			return ret;
		} else if (check(TokenKind.TRY)){
			eat(TokenKind.TRY);
			eat(TokenKind.THEN);
			Program tryProg = parseInProgram();
			eat(TokenKind.CATCH);
			eat(TokenKind.THEN);
			Program catchProg = parseInProgram();
			return new TryCatch(tryProg, catchProg);
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
			ArrayList<Expr> exprs=parseIsFonction();
			return new Var(name,exprs);
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
		if (check(TokenKind.ELSE)){
			eat(TokenKind.ELSE);
			eat(TokenKind.THEN);
			return this.parseInProgram();
		}
		else if (check(TokenKind.COM) || check(TokenKind.IF) || check(TokenKind.WHILE) || check(TokenKind.VAR) || check(TokenKind.OPEN) || check(TokenKind.CLOSE) || check(TokenKind.EOF) || check(TokenKind.END) || check(TokenKind.NEW) || check(TokenKind.DEF) || check(TokenKind.RETURN) || check(TokenKind.TRY))
			return null;
		else
			throw new IOException("Attendu: Else ou Instruction Trouvé: (" + token.kind + ")" + lexerPos());
	}

	public ArrayList<Expr> parseIsFonction() throws IOException {
		if(check(TokenKind.LPAR)) {
			eat(TokenKind.LPAR);
			ArrayList<Expr> exprs=parseArguments();
			eat(TokenKind.RPAR);
			return exprs;
		}
		//follow isFonction
		else if(check(TokenKind.RPAR) || check(TokenKind.SEMICOLON) || check(TokenKind.PLUS) || check(TokenKind.MINUS) || check(TokenKind.AND) ||  check(TokenKind.OR) || check(TokenKind.INF) || check(TokenKind.SUP) || check(TokenKind.EQ) || check(TokenKind.NOTEQ) || check(TokenKind.TIMES) || check(TokenKind.DIVIDE) || check(TokenKind.THEN) || check(TokenKind.VIR)) {
			return null;
		}
		else {
			throw new IOException("Attendu: Expression ou ; ou Alors Trouvé: (" + token.kind + ")" + lexerPos());
		}
	}
	public ArrayList<Expr> parseArguments() throws IOException {
		if(check(TokenKind.RPAR)) {
			//fonction sans arguments
			return new ArrayList<Expr>();
		}
		if(check(TokenKind.LIRE) || check(TokenKind.MINUS) || check(TokenKind.NOT) || check(TokenKind.INT) || check(TokenKind.LPAR) || check(TokenKind.TRUE) || check(TokenKind.FALSE) || check(TokenKind.VAR)) {
			//fonction avec arguments
			return parseArgument();
		}
		else {
			throw new IOException("Attendu: Expression ou ) Trouvé: (" + token.kind + ")" + lexerPos());
		}

	}
	public ArrayList<Expr> parseArgument() throws IOException {
		if(check(TokenKind.LIRE) || check(TokenKind.MINUS) || check(TokenKind.NOT) || check(TokenKind.INT) || check(TokenKind.LPAR) || check(TokenKind.TRUE) || check(TokenKind.FALSE) || check(TokenKind.VAR)) {
			Expr expr=parseExpression();
			ArrayList<Expr> exprs=parseSuiteArgument();
			exprs.add(0,expr);
			return exprs;
		}
		else {
			throw new IOException("Attendu: Expression Trouvé: (" + token.kind + ")" + lexerPos());
		}
	}

	public ArrayList<Expr> parseSuiteArgument() throws IOException {
		if(check(TokenKind.RPAR)) {
			return new ArrayList<Expr>();
		}
		else if(check(TokenKind.VIR)) {
			eat(TokenKind.VIR);
			return parseArgument();
		}
		else {
			throw new IOException("Attendu: , ou ) Trouvé: (" + token.kind + ")" + lexerPos());
		}
	}

	public ArrayList<String> parseAttributs() throws IOException {
		if(check(TokenKind.RPAR)) {
			//fonction sans arguments
			return new ArrayList<String>();
		}
		if(check(TokenKind.VAR)) {
			//fonction avec arguments
			return parseAttribut();
		}
		else {
			throw new IOException("Attendu: Variable ou ) Trouvé: (" + token.kind + ")" + lexerPos());
		}

	}
	public ArrayList<String> parseAttribut() throws IOException {
		if(check(TokenKind.VAR)) {
			String name = token.getStringValue();
			eat(TokenKind.VAR);
			ArrayList<String> names=parseSuiteAttribut();
			names.add(0,name);

			return names;
		}
		else {
			throw new IOException("Attendu: Variable Trouvé: (" + token.kind + ")" + lexerPos());
		}
	}

	public ArrayList<String> parseSuiteAttribut() throws IOException {
		if(check(TokenKind.RPAR)) {
			return new ArrayList<String>();
		}
		else if(check(TokenKind.VIR)) {
			eat(TokenKind.VIR);
			return parseAttribut();
		}
		else {
			throw new IOException("Attendu: , ou ) Trouvé: (" + token.kind + ")" + lexerPos());
		}
	}

	public ArrayList<Expr> parseVarBis() throws IOException {
		if(check(TokenKind.EQ)) {
			eat(TokenKind.EQ);
			Expr ex=parseExpression();
			ArrayList<Expr> expr=new ArrayList<Expr>();
			expr.add(ex);
			return expr;
		}
		else if(check(TokenKind.LPAR)) {
			eat(TokenKind.LPAR);
			ArrayList<Expr> args = parseArguments();
			eat(TokenKind.RPAR);
			return args;
		}
		else {
			throw new IOException("Attendu: = ou ( Trouvé: (" + token.kind + ")" + lexerPos());
		}
	}
}
