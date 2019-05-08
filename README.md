

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
                var = expression; |
                OPEN Block |
                new var = expression; |
                Try Alors InProgramme Catch InProgramme

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
                   <var> "=" <Expr> ";" |
                   OPEN <Block> |
                   "new" <var> "=" <expression>; |
                   "Try" "Alors" <InProgramme> "Catch" <InProgramme>

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
- les variables typées (booleen et entier), `var = Expr`,
```java
b = (3 < 4);
n = 5;
If b {
   Avancer(n);
}
```
- les blocs (avec indentation),
```java
// À FAIRE
```
- des obstacles et des "blocs de glace" (`#` et `*`dans la grille),
```
 0 0 0 0 0 0 0 0 0 0
 0 0 0 0 # 0 0 * 0 0
 0 2 0 0 0 4 0 0 0 0
 0 0 0 * * * # 0 # 0
 5 4 0 # 0 5 0 0 0 0
```
- et le try / catch (et les `ExecutionException`).
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

## Idées à ajouter

- les fonctions !
