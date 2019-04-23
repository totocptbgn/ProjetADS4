
# Projet d'ADS4

> Projet d'analyse des données structurées pour le semestre 4 de la license d'infomatique à Paris Diderot. Le but est de créer un analyseur lexical et un interpreteur pour executer du code permettant de donner des instructions à un robot sur une grille de chiffres.

Le sujet est présent [ici](sujet.pdf).
Par [Thomas Copt-Bignon](https://github.com/totocptbgn) et [Dao Thauvin](https://github.com/daothauvin).

## Grammaire :
> Grammaire utilisée pour le Parser. Remarque: OPEN et CLOSE représentent les indentations descendante et ascendante et EOF : end of file

```

programme     → Instr programme | EOF

InProgramme   → Instr InProgramme | Fin

Block 		  → Instr Block | CLOSE

instruction   → Avancer( expression ); |
                Tourner( expression ); |
                Ecrire( expression ); |
                Si expression Alors InProgramme else |
                TantQue expression Alors InProgramme |
                var = expression; |
				OPEN Block

expression    → Lire |
                nombre |
                bool |
                ( Expr binOp Expr ) |
                - Expr |
                ! Expr

binOp         → + | - | * | / | Et | Ou | < | > | = | !=

nombre        → ?[1-9][0-9]* | 0

bool          → True | False

var           → [a-zA-Z]+

else          → Sinon Alors InProgramme | ε
```

## Forme Bachus-Naur étendue :
> Autre notation pour la grammaire.

```
<programme>    ::= <Instr>  <programme> | EOF

<InProgramme>  ::= <Instr>  <InProgramme> | "Fin"

<Block>		   ::= <Instr>  <Block> | CLOSE


<Instr>        ::= "Avancer(" <Expr> ");" |
		   "Tourner(" <Expr> ");" |
		   "Ecrire(" <Expr> ");" |
	           "Si" <Expr> "Alors" <InProgramme> <else> |
	           "TantQue" <Expr> "Alors" <InProgramme> |
	           <var> "=" <Expr> ";" |
				OPEN <Block>

<Expr> 	       ::= "Lire" |
		   <nombre> |
		   "(" <Expr> <binOp> <Expr> ")" |
		   "-" <Expr> |
		   <bool>

<binOp>        ::= "+" | "-" | "*" | "/" | "Et" | "Ou" | "<" | ">" | "="

<nombre>       ::= <chiffrePos> <nombrebis> | <chiffre>

<nombrebis>    ::= <chiffre> <nombrebis> | <chiffre>

<chiffre>      ::= "0" | <chiffrePos>

<chiffrePos>   ::= [1-9]

<bool> 	       ::= "True" | "False"

<var>          ::= [a-zA-Z] <var> | [a-zA-Z]

<else>         ::= "Sinon" "Alors" <InProgramme> | ε
```

## Fonctionnalités ajoutées pour la Partie 3 :

- les commentaires (`//` et `/* */`),
- les opérations diviser `/` et multiplier `*`,
- l'opération non-égal `!=`
- la négation `!`,
- la possibilité de mettre un `Else` après le `If`,
- et les variables typées (booleen et entier), `var = Expr`.


## Idées à ajouter :

- les portées de blocs,
- le try catch et les blocs,
- les procedures
- et une interface graphique.
