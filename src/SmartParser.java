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
		} catch (IOException e) {}
	}

	public boolean check(TokenKind tokenTest){
		return tokenTest == this.token.kind;
	}

	public void next() throws IOException {
		this.token = lexer.yylex();
	}

	public void eat(TokenKind tokenTest) throws IOException {
		if (!check(tokenTest)) throw new IOException("Expected: (" + tokenTest + ") Found: (" + token.kind + ")");

		// Affichage des Token consommés
		if (token.kind == TokenKind.INT) {
			System.out.print(tokenTest + "(" + token.getIntValue() + ") ");
		} else if (token.kind == TokenKind.SEMICOLON){
			System.out.print(tokenTest + "\n");
		} else {
			System.out.print(tokenTest + " ");
		}

		next();
	}

	@Override
	public Program parseProgram(String exeName, Reader reader) throws IOException {
		parseInstruction();
		eat(TokenKind.SEMICOLON);
		if (check(TokenKind.EOF)){
			System.out.println("Fichier correct.");
		} else {
			parseProgram(exeName, reader);
		}
		return null;
	}

	public void parseInstruction() throws IOException {
		if (check(TokenKind.AVANCER)){
			eat(TokenKind.AVANCER);
		} else if (check(TokenKind.TOURNER)){
			eat(TokenKind.TOURNER);
		} else if (check(TokenKind.ECRIRE)){
			eat(TokenKind.ECRIRE);
		} else {
			throw new IOException("Instruction attendu. Trouvé:(" + token.kind + ")");
		}
		eat(TokenKind.LPAR);
		parseExpression();
		eat(TokenKind.RPAR);
	}

	public void parseExpression() throws IOException {
		if (check(TokenKind.LIRE)){
			eat(TokenKind.LIRE);
		} else if (check(TokenKind.MINUS)){
			eat(TokenKind.MINUS);
			eat(TokenKind.INT);
		} else if (check(TokenKind.INT)){
			eat(TokenKind.INT);
		} else if (check(TokenKind.LPAR)){
			eat(TokenKind.LPAR);
			parseExpression();
			if (check(TokenKind.PLUS)){
				eat(TokenKind.PLUS);
			} else if (check(TokenKind.MINUS)){
				eat(TokenKind.MINUS);
			} else {
				throw new IOException("Attendu: (MINUS ou PLUS) Trouvé: (" + token.kind + ")");
			}
			parseExpression();
			eat(TokenKind.RPAR);
		} else {
			throw new IOException("Attendu: (Expression) Trouvé: (" + token.kind + ")");
		}
	}
}
