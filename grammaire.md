## Grammaire :
> Grammaire utilisée pour le Parser.

```
programme     → instruction ; programme | ε

InProgramme   → instruction ; programme | Fin

Instr         → Avancer(Expr) |
                Tourner(Expr) |
                Ecrire(Expr) |
                Si Expr Alors InProgramme |
                TantQue Expr Faire InProgramme |
                var = Expr

Expr          → Lire |
                nombre |
                ( Expr binOp Expr ) |
                - Expr |
                bool

binOp         → + | - | Et | Ou | < | > | =

nombre        → ?[1-9][0-9]* | 0

bool          → True | False

var           → [a-zA-Z]+
```