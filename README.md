# Projet d'ADS4

> Projet d'analyse des données structurées pour le semestre 4 de la license d'infomatique à Paris Diderot. Le but est de créer un analyseur lexical et un interpreteur pour executer du code permettant de donner des instructions à un robot sur une grille de chiffres.

Le sujet est présent [ici](sujet.pdf).
Par [Thomas Copt-Bignon](https://github.com/totocptbgn) et [Dao Thauvin](https://github.com/daothauvin).

## Grammaire :
> Grammaire utilisée pour le Parser.

```
programme     → instruction ; programme | ε

InProgramme   → instruction ; InProgramme | Fin

instruction   → Avancer(expression) |
                Tourner(expression) |
                Ecrire(expression) |
                Si expression Alors InProgramme else |
                TantQue expression Faire InProgramme |
                var = expression

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

else          → Sinon Faire InProgramme | ε
```

## Fonctionnalités ajoutées pour la Partie 3 :

- les commentaires (`//` et `/* */`),
- les opérations diviser `/` et multiplier `*`,
- l'opération non-égal `!=`
- et la négation `!`.


## Idées à ajouter :

- les portées de blocs,
- un `else` après le `if`,
- et une interface graphique.
