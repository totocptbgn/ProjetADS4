## Grammaire :
> Grammaire utilisée pour le Parser.

```
programme     → Instr ; programme | ε

InProgramme   → Instr ; programme | Fin

Instr         → Avancer( Expr ) |
                Tourner( Expr ) |
                Ecrire( Expr ) |
                Si Expr Alors InProgramme |
                TantQue Expr Faire InProgramme |
                var = Expr

Expr          → Lire |
                nombre |
                ( Expr binOp Expr ) |
                - Expr |
                bool

binOp         → + | - | * | / | Et | Ou | < | > | =

nombre        → ?[1-9][0-9]* | 0

bool          → True | False

var           → [a-zA-Z]+
```

## Forme Bachus-Naur étendu :
> Autre notation pour la grammaire.

```
<programme>    ::= <Instr> ";" <programme> | ε

<InProgramme>  ::= <Instr> ";" <programme> | "Fin"

<Instr>        ::= "Avancer(" <Expr> ")" |
		           "Tourner(" <Expr> ")" |
				   "Ecrire(" <Expr> ")" |
				   "Si" <Expr> "Alors" <InProgramme> |
				   "TantQue" <Expr> "Faire" <InProgramme> |
				   <var> "=" <Expr>

<Expr> 		   ::= "Lire" |
					<nombre> |
					"(" <Expr> <binOp> <Expr> ")" |
					"-" <Expr> |
					<bool>

<binOp> 	   ::= "+" | "-" | "*" | "/" | "Et" | "Ou" | "<" | ">" | "="

<nombre> 	   ::= <chiffrePos> <nombrebis> | <chiffre>

<nombrebis>	   ::= <chiffre> <nombrebis> | <chiffre>

<chiffre>	   ::= "0" | <chiffrePos>

<chiffrePos>   ::= [1-9]

<bool> 		   ::= "True" | "False"

<var>   	   ::= [a-zA-Z] <var> | [a-zA-Z]

```
