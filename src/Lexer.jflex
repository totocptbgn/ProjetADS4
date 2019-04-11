%%
%public
%class Lexer
%unicode
%type Token
%yylexthrow java.io.IOException

int = [0-9]+
blank = [\n\r \t]
%%

";"         {return new Token(TokenKind.SEMICOLON);}
"("         {return new Token(TokenKind.LPAR);}
")"         {return new Token(TokenKind.RPAR);}
"-"         {return new Token(TokenKind.MINUS);}
"+"         {return new Token(TokenKind.PLUS);}
{int}       {return new IntToken(TokenKind.INT, Integer.parseInt(yytext()));}
"Avancer"   {return new Token(TokenKind.AVANCER);}
"Tourner"   {return new Token(TokenKind.TOURNER);}
"Ecrire"    {return new Token(TokenKind.ECRIRE);}
"Lire"      {return new Token(TokenKind.LIRE);}
{blank}     {}
[^]	        {throw new java.io.IOException("Symbole non reconnu (" + yytext() + "");}
<<EOF>>     {return new Token(TokenKind.EOF);}
