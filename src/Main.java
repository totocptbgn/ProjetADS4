import java.io.*;

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
		System.out.println(grid);
		interp.run(prog, grid);
		//prog.debug();
		System.out.println(grid);

    }

    /**
     * Fonction qui teste les différents programme de test.
     * @throws FileNotFoundException Levée lorsqu'un des fichiers à tester n'est pas trouvé.
     */

    private static void runTests() {
        System.out.println("------------- Test des fichiers dans le répertoire tests : -------------\n");
        testFile("nofile");
        testFile("good0");
        testFile("good1");
        testFile("good2");
        testFile("bad0");
        testFile("bad1");
        testFile("bad2");
		System.out.println("---------------------------- Fin des tests. ----------------------------\n");
    }

    /**
     * Fonction qui test si un fichier est correct, c'est-à-dire, respecte la grammaire du SmartParser).
     * @param filename Nom du fichier dans src/tests/ à tester.
     */

    private static void testFile(String filename) {
    	System.out.println("///////  Test de " + filename + " ///////");

        // Création du Reader sur le fichier indiqué avec filename
        Reader reader;
        try {
            reader = new FileReader(new File("src/tests/" + filename));
        } catch (FileNotFoundException e) {
            System.out.println("  /!\\ Fichier non trouvé : fin du test.\n");
            return;
        }

      	// Mise en place de l'interpreter
		Program p = null;
        String exeName = filename;
        String[] args = {"src/tests/" + filename, "src/grille.txt"};
        IOEnv ioEnv = IOEnv.parseArgs(exeName, args);
        Grid grid = Grid.parseGrid(exeName, ioEnv.inGrid);
        Parser parser = new SmartParser(ioEnv.inProgram);
        SmartInterpreter interp = new SmartInterpreter();
        interp.setGrille(grid);

        // Parsing et compilation
        try {
            p = parser.parseProgram("Tests", reader);
            System.out.println("  - Compilation : Ok.");
        } catch (IOException e) {
            System.out.println("  - Compilation : Fichier incorrect. Cause: [" + e.getMessage() + "]");
			System.out.println("  - Execution : Pas d'execution possible.");
        }

        // Execution
        if (p != null) {
        	System.out.println("  - Execution :\n");
        	try {
        		System.out.println("Grille avant éxécution :");
        		System.out.println(grid);
        		p.eval();
        		System.out.println("Grille après execution :");
        		System.out.println(grid);
        		System.out.println(" ==> Fichier correct.");
        		p.debug();
        	}
        	catch (IOException e) {
        		System.out.println(" ==> Fichier incorrect. Cause: [" + e.getMessage() + "]");
        	}
        }
        System.out.println();
    }
}
