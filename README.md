

# Projet d'ADS4

> Projet d'analyse des données structurées pour le semestre 4 de la license d'infomatique à Paris Diderot. Le but est de créer un analyseur lexical et un interpreteur pour executer du code permettant de donner des instructions à un robot sur une grille de chiffres.

Le sujet est présent [ici](sujet.pdf).
Par [Thomas Copt-Bignon](https://github.com/totocptbgn) et [Dao Thauvin](https://github.com/daothauvin).

## Grammaire
> Grammaire utilisée pour le Parser.

```

programme     → instruction programme | EOF

InProgramme   → instruction InProgramme | Fin

Block 	      → instruction Block | CLOSE

instruction   → Avancer( expression ); |
                Tourner( expression ); |
                Ecrire( expression ); |
                Si expression Alors InProgramme else |
                TantQue expression Alors InProgramme |
                var varbis ; |
                OPEN Block |
                new var = expression ; |
                Try Alors InProgramme Catch InProgramme |
				def( attributs ): Block |
				return expression;

varbis        → = expression | ( arguments )

attributs	  → ε | attribut

attribut	  → variable suiteattribut

suiteattribut → , argument |  ε

expression    → Lire |
                nombre |
                bool |
                ( expression binOp expression ) |
                - expression |
                ! expression |
				variable
				
variable	  → var isFonction

isFonction     → ( arguments ) | ε

arguments 	→ ε | argument

argument	  → expression suiteargument

suiteargument → "," argument |  ε


binOp         → + | - | * | / | Et | Ou | < | > | = | !=

nombre        → ?[1-9][0-9]* | 0

bool          → True | False

var           → [a-zA-Z]+

else          → Sinon Alors InProgramme | ε
```
### Détails

- **OPEN** et **CLOSE** représentent les indentations descendantes et ascendantes.
- **EOF** = *End Of File* (caractère de fin de fichier).
- Le programme peut aussi être écrit en anglais. *Par exemple :* `TantQue` *peut être écrit* `While`*, etc...*
- On peut remplacer `Alors` et `Fin` par respectivement `{` et `}` et `Et` et `Ou` par `&&` et `||`.

## Forme Bachus-Naur étendue
> Autre notation pour la grammaire.

```
<programme>    ::= <Instr>  <programme> | EOF

<InProgramme>  ::= <Instr>  <InProgramme> | "Fin"

<Block>	       ::= <Instr>  <Block> | CLOSE

<Instr>        ::= "Avancer(" <Expr> ");" |
                   "Tourner(" <Expr> ");" |
                   "Ecrire(" <Expr> ");" |
                   "Si" <Expr> "Alors" <InProgramme> <else> |
                   "TantQue" <Expr> "Alors" <InProgramme> |
                   <var> <varbis> ";" |
                   OPEN <Block> |
                   "new" <var> "=" <expression>; |
                   "Try" "Alors" <InProgramme> "Catch" <InProgramme> |
				   "def(" <attributs> "):" <Block> |
				   "return" <expression> ";"

<varbis>      ::= "=" <expression> ";" | "(" <arguments> ")"

<attributs>	  ::= ε | <attribut>

<attribut>	  ::= <variable> <suiteattribut>

<suiteattribut> ::= "," <argument> |  ε

<Expr> 	       ::= "Lire" |
                   <nombre> |
                   "(" <Expr> <binOp> <Expr> ")" |
                   "-" <Expr> |
                   <bool> |
				   <variable>
				
<variable>	  ::= <var> <isFonction>

<isFonction>  ::= "( " <arguments> ")" | ε

<arguments>	   ::= ε | <argument>

<argument>	   ::= <expression> <suiteargument>

<suiteargument>::= "," <argument> |  ε

<binOp>        ::= "+" | "-" | "*" | "/" | "Et" | "Ou" | "<" | ">" | "="

<nombre>       ::= <chiffrePos> <nombrebis> | <chiffre>

<nombrebis>    ::= <chiffre> <nombrebis> | <chiffre>

<chiffre>      ::= "0" | <chiffrePos>

<chiffrePos>   ::= [1-9]

<bool> 	       ::= "True" | "False"

<var>          ::= [a-zA-Z] <var> | [a-zA-Z]

<else>         ::= "Sinon" "Alors" <InProgramme> | ε
```

## Fonctionnalités ajoutées pour la Partie 3

- les commentaires (`//` et `/* */`),
```java
// Simple commentaire
/*
Commentaires
multi-lignes
*/
```
- les opérations diviser `/` et multiplier `*`,
```java
Avancer((3 * 4));
Avancer((4 / 2));
```
- l'opération non-égal `!=`
```java
If ((3 > 4) != (6 != 4)) {
   Avancer(5);
}
```
- la négation `!`,
```java
If !(3 > 4) {
   Avancer(5);
}
```
- la possibilité de mettre un `Else` après le `If`,
```java
If (3 > 4) {
   Avancer(5);
} Else {
   Tourner(1);
   Avancer(5);
}
```
- les variables typées statiquements (booleen et entier), `var = Expr`,
```java
b = (3 < 4);
n = 5;
If b {
   Avancer(n);
}
```
Les variables d'environnements :
```java
Ecrire(Width);
Ecrire(Height);
```
- des obstacles et des "blocs de glace" (`#` et `*`dans la grille),
```
 0 0 0 0 0 0 0 0 0 0
 0 0 0 0 # 0 0 * 0 0
 0 2 0 0 0 4 0 0 0 0
 0 0 0 * * * # 0 # 0
 5 4 0 # 0 5 0 0 0 0
```
- le try / catch (et les `ExecutionException`),
```java
Try {
   Avancer(5);
} Catch {
   Tourner(1);
   Avancer(1);
   Tourner(3);
   Avancer(5);
}
Ecrire(7);
```
- les blocs (avec indentation).
```java
i=3;
	i=False;
i=(i+1);
```
Remarques :
1. Si le type est le même que la variable en dehors du bloc alors c'est cette variable qui est modifié
2. Les variables exterieurs qui ont été redéclarer ne sont plus du tout accessibles
3. Il est possible de ne pas faire d'indentation dans les instructions if, while, try pour ne pas créer de bloc
- l'assignement avec new (necessaire pour redéclaration de même type dans bloc)
```java
i=3;
	new i=(i+1); //i=4
i=(i+1); //i=4
```
- les fonctions :
```java
def i(a,b):
	i=3;
	if a {
		c=3;
	}
c=2;
i=2;
i(True,1);
Ecrire(c);
Tourner(i);
```
Remarques :
1. définition de fonctions dans bloc possible (comme variable plus accessible à l'extérieur du bloc)
2. Les noms des fonctions ne doivent pas être unique, si la signature deux fonctions sont les même alors on rédefinie la première (ou la cache si redéfinie dans un bloc)
3. L'indentation est oblifatoire pour les corps des fonctions
4. Les variables en dehors de la fonction prisent en compte sont les variables lors de l'execution, c'est à dire lors de l'appel de la fonction (fonctions dynamiques)
5. `return` permet à une fonction de renvoyer une valeur, qui pourra alors être utilisée dans une expression
6. Un seul type de retour par fonction est accépté ( on ne prend pas en compte les returns non utilisées à l'execution), sauf si la fonction est redéfinie
7. Les appels recursif ne sont pas possible