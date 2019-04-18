import java.io.*;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws Exception {
        runMain(args);
        runTests();
    }

    /**
     * Fonction qui parse et interprete le programme.
     * @param args Tableau de string indiquant l'adresse de la grille et du programme à interpreter
     * @throws IOException Quand le parser ne trouve pas un symbole autorisé par la grammaire
     */

    private static void runMain(String[] args) throws IOException {
        // Mise en place du programme
        String exeName = "Main";
        IOEnv ioEnv = IOEnv.parseArgs(exeName, args);

        // Création du Parser
        Parser parser = new SmartParser(ioEnv.inProgram);

        // Construction du Program en lisant le ficher grâce au Parser
        Program prog = parser.parseProgram(exeName, ioEnv.inProgram);
        System.out.println(prog);
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
     * @throws FileNotFoundException Levée lorsqu'un des fichiers à tester n'est pas trouvé.
     */

    private static void runTests() {
        System.out.println("Test des fichiers dans le répertoire tests :\n");
        testFile("good0");
        testFile("good1");
        testFile("good2");
        testFile("bad0");
        testFile("bad1");
        testFile("bad2");
    }

    /**
     * Fonction qui test si un fichier est correct, c'est-à-dire, respecte la grammaire du SmartParser).
     * @param filename Nom du fichier dans src/tests/ à tester.
     */

    private static void testFile(String filename) {
        System.out.println("-  Test de " + filename + " : ");

        // Création du Reader sur le fichier indiqué avec filename
        Reader reader;
        try {
            reader = new FileReader(new File("src/tests/" + filename));
        } catch (FileNotFoundException e) {
            System.out.println("  Fichier non trouvé.");
            return;
        }

        // Parsing du fichier et affichage des résultats
        Parser parser = new SmartParser(reader);
        Program p=null;
        try {
            p=parser.parseProgram("Tests", reader);
            System.out.println("  Compilation : Fichier correct.");
        } catch (IOException e) {
            System.out.println("  Compilation : Fichier incorrect. Cause: [" + e.getMessage() + "]");
        }
        if(p!=null) {
        	try {
        		p.eval(new HashMap<String,Integer>());
        		System.out.println("  Execution : Fichier correct.");
        	}
        	catch (IOException e) {
        		System.out.println("  Execution : Fichier incorrect. Cause: [" + e.getMessage() + "]");
        	}
        }
    }
}
