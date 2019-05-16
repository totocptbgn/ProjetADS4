
# Projet d'ADS4  
  
> Projet d’analyse des données structurées pour le semestre 4 de la licence d’informatique à Paris Diderot. Le but est de créer un analyseur lexical et un interpréteur pour exécuter du code permettant de donner des instructions à un robot sur une grille de chiffres. 
  
Le sujet est présent [ici](docs/sujet.pdf).  
Par [Thomas Copt-Bignon](https://github.com/totocptbgn) et [Dao Thauvin](https://github.com/daothauvin).  

## Utilisation

  Le programme a été codé sous Java 11. Pour le lancer, compilez grâce à `javac` le fichier `Main.java`, puis éxecuter la classe `Main` en ajoutant les arguments comme cela :
  ```bash
  $ java Main src/programme.txt src/grille.txt
  ```
  Un menu explicatif s'affichera alors et vous pourrez faire l'action de votre choix. Vous pouvez aussi écrire des programme et des grilles puis les utiliser en les placant dans les répertoires respectifs `progPerso/` et `gridPerso/`.
  
## Grammaire  
> Grammaire utilisée pour le Parser.  
  
```  
programme     → instruction programme | EOF  
  
InProgramme   → instruction InProgramme | Fin  
  
Block         → instruction Block | CLOSE  
  
instruction   → Avancer( expression ); |  
                Tourner( expression ); |  
                Ecrire( expression ); |  
                Si expression Alors InProgramme else |  
                TantQue expression Alors InProgramme |  
                var varbis ; |  
                OPEN Block |  
                new var = expression ; |  
                Try Alors InProgramme Catch InProgramme |  
                def var ( attributs ): Block |  
                return expression;  
  
varbis        → = expression | ( arguments )  
  
attributs     → ε | attribut  
  
attribut      → var suiteattribut  
  
suiteattribut → , argument |  ε  
  
expression    → Lire |  
                nombre |  
                bool |  
                ( expression binOp expression ) |  
                - expression |  
                ! expression |  
                variable  
  
variable      → var isFonction  
  
isFonction    → ( arguments ) | ε  
  
arguments     → ε | argument  
  
argument      → expression suiteargument  
  
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
- Un diagramme de la syntaxe est disponible [ici](https://totocptbgn.github.io/ProjetADS4/).
  
## Forme Bachus-Naur étendue  
> Autre notation pour la grammaire.  
  
```  
<programme>     ::= <Instr>  <programme> | EOF  
  
<InProgramme>   ::= <Instr>  <InProgramme> | "Fin"  
  
<Block>         ::= <Instr>  <Block> | CLOSE  
  
<Instr>         ::= "Avancer(" <Expr> ");" |  
                    "Tourner(" <Expr> ");" |  
                    "Ecrire(" <Expr> ");" |  
                    "Si" <Expr> "Alors" <InProgramme> <else> |  
                    "TantQue" <Expr> "Alors" <InProgramme> |  
                    <var> <varbis> ";" |  
                    OPEN <Block> |  
                    "new" <var> "=" <expression> ";" |  
                    "Try Alors" <InProgramme> "Catch" <InProgramme> |  
                    "def" <var> "(" <attributs> "):" <Block> |  
                    "return" <expression> ";"  
  
<varbis>        ::= "=" <expression> ";" | "(" <arguments> ")"  
  
<attributs>     ::= ε | <attribut>  
  
<attribut>      ::= <var> <suiteattribut>  
  
<suiteattribut> ::= "," <argument> |  ε  
  
<Expr>          ::= "Lire" |  
	            <nombre> |  
	            "(" <Expr> <binOp> <Expr> ")" |  
	            "-" <Expr> |  
	            <bool> |  
	            <variable>  
  
<variable>      ::= <var> <isFonction>  
  
<isFonction>    ::= "( " <arguments> ")" | ε  
  
<arguments>     ::= ε | <argument>  
  
<argument>      ::= <expression> <suiteargument>  
  
<suiteargument> ::= "," <argument> |  ε  
  
<binOp>         ::= "+" | "-" | "*" | "/" | "Et" | "Ou" | "<" | ">" | "="  
  
<nombre>        ::= <chiffrePos> <nombrebis> | <chiffre>  
  
<nombrebis>     ::= <chiffre> <nombrebis> | <chiffre>  
  
<chiffre>       ::= "0" | <chiffrePos>  
  
<chiffrePos>    ::= [1-9]  
  
<bool>          ::= "True" | "False"  
  
<var>           ::= [a-zA-Z] <var> | ε  
  
<else>          ::= "Sinon Alors" <InProgramme> | ε  
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
- les variables typées statiquements (booleen et entier), `var = Expr`, le `TypeException` (Exception pour typage),  
   ```java  
   b = (3 < 4);  
   n = 5;  
   If b {  
      Avancer(n);  
   }  
   ```  
- des variables d'environnements,  
   ```java  
   Ecrire(Width);  
   Ecrire(Height);  
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
- les blocs (avec indentation),  
   ```java  
   i = 3;  
       i = False;  
       If i {  
         Avancer(4);  
       }  
   i = (i+1);  
   Avancer(i);  
   ```  
   #### Remarques :  
  - Si le type est le même que la variable en dehors du bloc alors c'est cette variable qui est modifiée.  
   - Les variables extérieurs qui ont été redéclarer ne sont plus du tout accessibles.  
   - Il est possible de ne pas faire d'indentation dans les instructions `If`, `While`, `Try` pour ne pas créer de bloc.  
  
- l'assignement avec `new` (necessaire pour les redéclarations de même type dans bloc),  
   ```java  
   i = 3;  
       new i = (i + 1);  
       Avancer(i); // i = 4  
   i = (i + 1);  
   Avancer(i); // i = 4  
   ```  
- les fonctions,
   ```java  
   def i(a,b):  
       i = 3;  
       If a {  
         c = 3;  
       }  
   c = 2;  
   i = 2;  
   i(True,1);  
   Ecrire(c);  
   Tourner(i);  
   ```  
   #### Remarques :  
  - La définition de fonctions dans les blocs est possible (mais comme les variables, elles ne seront plus accessibles à l’extérieur du bloc).  
   - Les noms des fonctions ne doivent pas être uniques, si la signature de deux fonctions sont les mêmes alors on redéfinie la première (ou on cache la première si redéfinie dans un bloc). 
   - L'indentation est obligatoire pour les corps des fonctions.  
   - Les variables en dehors de la fonction sont prises en compte lors de l’exécution, c’est-à-dire lors de l’appel de la fonction (fonctions dynamiques). 
   - `return` permet à une fonction de renvoyer une valeur, qui pourra alors être utilisée dans une expression.  
   - Un seul type de retour par fonction est accepté, sauf si la fonction est redéfinie (ou non appelé).  
   - Les appels récursifs sont possibles, si des variables sont créées sans le new, elle sera accessible lors de l'appel récurisif (et garde sa valeur lors du dernier appel).
   - Si la fonction est censé renvoyer un booleen ou un entier mais ne rencontre pas de return lors de l'execution alors renvoie une erreur
   - Lors de l'execution une fonction dois être définie avant son appel (c'est à-dire le def avant l'appel dans le code si l'appel n'est pas dans une fonction et sinon avant l'appel de la fonction ou se trouve l'appel conserné)
- des obstacles `#`, des "blocs de glace" `*` et des "tourniquets" `§`,  
   ```
    0 0 0 0 0 0 0 0 0 0  
    0 0 0 0 # 0 0 * 0 0  
    0 2 0 0 0 4 0 0 0 0  
    0 0 0 * * * # 0 # 0  
    5 4 0 # 0 5 0 0 0 0  
   ```
   #### Remarques :
   - Si le Robot marche sur un obstacle cela produit une `ExecutionException` et le programme s'arrête.
   - Si le Robot marche sur un bloc de glace, il va "glisser" c'est-à-dire, avancer d'une case dans sa direction.
   - Dans le cas où il y a plusieurs blocs de glace alors il va glisser sur tout les blocs de glace jusqu'à arriver à une case sans bloc et va ensuite continuer son action.
   - Si le Robot marche sur un tourniquet, alors il va se tourner dans une direction aléatoire et continuer sa course.
   - Quand le Robot lit sur un tourniquet, il lit alors `0`.
- et un générateur de grille.
	#### Remarques :
	- La taille de la grille est choisie par l'utilisateur (largeur et hauteur entre 3 et 100 inclus).
	- La grille est crée dans un fichier dont l'utilisateur donne le nom, et qui est stockée dans le répertoire `gridPerso/`.
	- La position de départ sera toujours (0, 0) et la case ne comportera jamais d'obstacles.
	- Les cases sont générés aléatoirement et sont remplis d'un nombre entre `-9` et `9` inclus ou alors `#`, `*` et `§`.
