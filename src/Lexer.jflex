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
	public int getColumn() { return yycolumn; }
	public int getLine() { return yyline; }
	public int getChar() { return yychar; }
%}

int = [0-9]+
blank = [\n\r \t]
string = [a-zA-Z]+

%%

<YYINITIAL> {
";"                             {return new Token(TokenKind.SEMICOLON);}
"("                             {return new Token(TokenKind.LPAR);}
")"                             {return new Token(TokenKind.RPAR);}
"-"                             {return new Token(TokenKind.MINUS);}
"+"                             {return new Token(TokenKind.PLUS);}
"*"                             {return new Token(TokenKind.TIMES);}
"/"                             {return new Token(TokenKind.DIVIDE);}
"!"                             {return new Token(TokenKind.NOT);}
("Et"|"&&"|"And")               {return new Token(TokenKind.AND);}
("Ou"|"&&"|"Or")                {return new Token(TokenKind.OR);}
">"                             {return new Token(TokenKind.SUP);}
"<"                             {return new Token(TokenKind.INF);}
"="                             {return new Token(TokenKind.EQ);}
"!="                             {return new Token(TokenKind.NOTEQ);}
"True"                          {return new Token(TokenKind.TRUE);}
"False"                         {return new Token(TokenKind.FALSE);}
("Si"|"If")                     {return new Token(TokenKind.IF);}
("Alors"|"Then")                {return new Token(TokenKind.THEN);}
("TantQue"|"While")             {return new Token(TokenKind.WHILE);}
("Faire"|"Do")                  {return new Token(TokenKind.DO);}
("Fin"|"End")                   {return new Token(TokenKind.END);}
"Lire"                          {return new Token(TokenKind.LIRE);}
("Tourner"|"Avancer"|"Ecrire")  {return new StringToken(TokenKind.COM,yytext());}
{int}                           {return new IntToken(TokenKind.INT, Integer.parseInt(yytext()));}
{string}                        {return new StringToken(TokenKind.VAR, yytext());}
{blank}                         {}
"//"[^\n]*                      {}
"/*"                            {yybegin(INCOMMENT);}
[^]	                            {throw new java.io.IOException("Symbole non reconnu (" + yytext() + "");}
<<EOF>>                         {return new Token(TokenKind.EOF);}
} <INCOMMENT> {
"*/"                            {yybegin(YYINITIAL);}
[^]                             {}
<<EOF>>                         {return new Token(TokenKind.EOF);}
}

