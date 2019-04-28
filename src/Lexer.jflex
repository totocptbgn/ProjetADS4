import java.util.Stack;
%%
%public
%class Lexer
%unicode
%type Token
%line
%column
%char

%yylexthrow java.io.IOException
%state INCOMMENT

%{
	public int getColumn() {return yycolumn + 1;}
	public int getLine() {return yyline + 1;}
	public int getChar() {return yychar + 1;}
	private Stack<Integer> stackSpace = new Stack<>();
	private boolean isNewLine = true;
%}

%init{
	stackSpace.add(0);
%init}

int = [0-9]+
blank = [\r\t\n ]
string = [a-zA-Z]+

%%

<YYINITIAL> {
(\r|\n|\r\n|\u2028|\u2029|\u000B|\u000C|\u008)([ \t]*)$ 	{}
((\r|\n|\r\n|\u2028|\u2029|\u000B|\u000C|\u008)([ \t]*))  	{
																if (isNewLine) {
																	int nbspace = 0;
																	for (int i = 1; i < yytext().length(); i++) {
																		if (yytext().charAt(i) == ' ')
																			nbspace++;
																		else if (yytext().charAt(i) == '\t')
																			nbspace = nbspace + 4;
																	}
																	if (stackSpace.empty() || stackSpace.peek() < nbspace) {
																		stackSpace.push(nbspace);
																		return new Token(TokenKind.OPEN);
																	} else if (nbspace != stackSpace.peek()) {
																		int nbSpace = 0;
																		while (!stackSpace.empty() && stackSpace.peek() > nbspace) {
																			nbSpace++;
																			stackSpace.pop();
																		}
																		if (stackSpace.empty() || stackSpace.peek() < nbspace)
																			throw new java.io.IOException("Mauvaise Indentation Ligne " + (getLine() + 1));
																		else
																			return new Token(TokenKind.CLOSE, nbSpace);
																	}
																}
														  	}
";"                             						  	{isNewLine = true; return new Token(TokenKind.SEMICOLON);}
"("                             							{isNewLine = false; return new Token(TokenKind.LPAR);}
")"                             							{isNewLine = false; return new Token(TokenKind.RPAR);}
"-"                             							{isNewLine = false; return new Token(TokenKind.MINUS);}
"+"                             							{isNewLine = false; return new Token(TokenKind.PLUS);}
"*"                             							{isNewLine = false; return new Token(TokenKind.TIMES);}
"/"                             							{isNewLine = false; return new Token(TokenKind.DIVIDE);}
"!"                             							{isNewLine = false; return new Token(TokenKind.NOT);}
("Et"|"&&"|"And")               							{isNewLine = false; return new Token(TokenKind.AND);}
("Ou"|"&&"|"Or")                							{isNewLine = false; return new Token(TokenKind.OR);}
">"                             							{isNewLine = false; return new Token(TokenKind.SUP);}
"<"                             							{isNewLine = false; return new Token(TokenKind.INF);}
"="                             							{isNewLine = false; return new Token(TokenKind.EQ);}
"!="                            							{isNewLine = false; return new Token(TokenKind.NOTEQ);}
"True"                          							{isNewLine = false; return new Token(TokenKind.TRUE);}
"False"                         							{isNewLine = false; return new Token(TokenKind.FALSE);}
("new"|"New")							  					{isNewLine = false; return new Token(TokenKind.NEW);}
("Def"|"def")												{isNewLine = false; return new Token(TokenKind.DEF);}
"Try"														{isNewLine = false; return new Token(TokenKind.TRY);}
"Catch"														{isNewLine = false; return new Token(TokenKind.CATCH);}
("Si"|"If")                     							{isNewLine = false; return new Token(TokenKind.IF);}
("Sinon"|"Else")                							{isNewLine = false; return new Token(TokenKind.ELSE);}
("Alors"|"Then"|"{")            							{isNewLine = true; return new Token(TokenKind.THEN);}
("TantQue"|"While")             							{isNewLine = false; return new Token(TokenKind.WHILE);}
("Fin"|"End"|"}")               							{isNewLine = true; return new Token(TokenKind.END);}
"Lire"                          							{isNewLine = false; return new Token(TokenKind.LIRE);}
("Tourner"|"Avancer"|"Ecrire")  							{isNewLine = false; return new StringToken(TokenKind.COM, yytext());}
{int}                           							{isNewLine = false; return new IntToken(TokenKind.INT, Integer.parseInt(yytext()));}
{string}                        							{isNewLine = false; return new StringToken(TokenKind.VAR, yytext());}
{blank}                         							{}
"//"[^\n]*                      							{}
"/*"                            							{yybegin(INCOMMENT);}
[^]	                            							{throw new java.io.IOException("Symbole non reconnu (" + yytext() + ") Line " + getLine() + " Column " + getColumn() + " Char " + getChar());}
<<EOF>>                         							{
																int nbspace = 0;
																while (!stackSpace.empty()) {
																		nbspace++;
																		stackSpace.pop();
																}
																return new Token(TokenKind.EOF, nbspace + 1);
															}
}

<INCOMMENT> {
"*/"                            							{isNewLine = false; yybegin(YYINITIAL);}
[^]                             							{}
<<EOF>>                         							{
																int nbspace = 0;
																while (!stackSpace.empty()) {
																	nbspace++;
																	stackSpace.pop();
																}
																return new Token(TokenKind.EOF, nbspace + 1);
															}
}
