import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws Exception {
        runMain(args);
        runTests(args);
    }

    /**
     * Fonction qui parse et interprete le programme
     *
     * @param args Tableau de string indiquant l'adresse de la grille et du programme à interpreter
     * @throws IOException Quand le parser ne trouve pas un symbole autorisé par la grammaire
     */

    private static void runMain(String[] args) throws IOException {
        // Mise en place du programme
        String exeName = "Main";
        IOEnv ioEnv = IOEnv.parseArgs(exeName, args);

        // Création du Parser
        Parser parser = new SmartParser(new FileReader(new File(args[0])));

        // Construction du Program en lisant le ficher grâce au Parser
        Program prog = parser.parseProgram(exeName, ioEnv.inProgram);
        Grid grid = Grid.parseGrid(exeName, ioEnv.inGrid);

        // Intrepetation et execution du programme sur la grille
        Interpreter interp = new SmartInterpreter();
        interp.run(prog, grid);

        /*
        prog.debug();
        ioEnv.outGrid.print(grid);
        */
    }

    /**
     * Fonction qui teste les différents programme de test.
     *
     * @param args Tableau de string indiquant l'adresse de la grille et du programme à interpreter
     * @throws IOException Quand le parser ne trouve pas un symbole autorisé par la grammaire.
     */

    private static void runTests(String[] args) throws IOException {
        String exeName = "Tests";
        IOEnv ioEnv = IOEnv.parseArgs(exeName, args);

    }
}
