%%
%public
%class Lexer
%unicode
%type Token
%line
%column
%char

%yylexthrow java.io.IOException

%{
	public int getColumn() { return yycolumn; }
	public int getLine() { return yyline; }
	public int getChar() { return yychar; }
%}

int = [0-9]+
blank = [\n\r \t]
string = [a-zA-Z]+

%%

";"         {return new Token(TokenKind.SEMICOLON);}
"("         {return new Token(TokenKind.LPAR);}
")"         {return new Token(TokenKind.RPAR);}
"-"         {return new Token(TokenKind.MINUS);}
"+"         {return new Token(TokenKind.PLUS);}
"Lire"      {return new Token(TokenKind.LIRE);}
{int}       {return new IntToken(TokenKind.INT, Integer.parseInt(yytext()));}
{string}    {return new StringToken(TokenKind.CMD, yytext()); }
{blank}     {}
[^]	        {throw new java.io.IOException("Symbole non reconnu (" + yytext() + "");}
<<EOF>>     {return new Token(TokenKind.EOF);}
