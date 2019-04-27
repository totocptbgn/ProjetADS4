import java.io.*;

public class Main {
    public static void main(String[] args) throws Exception {
        runMain(args);
        runTests();
    }

    private static void runMain(String[] args) throws IOException, ExecutionException {
        // Mise en place du programme
        String exeName = "Main";
        IOEnv ioEnv = IOEnv.parseArgs(exeName, args);

        // Création du Parser
        Parser parser = new SmartParser(ioEnv.inProgram);

        // Construction du Program en lisant le ficher grâce au Parser
        Program prog = parser.parseProgram(exeName, ioEnv.inProgram);

		// Construction de la grille en lisant le fichier
		Grid grid = Grid.parseGrid(exeName, ioEnv.inGrid);

		// Affichage de la grille d'origine
		System.out.println("--------------------------- Grille d'origine ---------------------------\n");
		grid.print();

        // Intrepetation et execution du programme sur la grille
        Interpreter interp = new SmartInterpreter();
        interp.run(prog, grid);
		System.out.println("------------------------------- Console -------------------------------\n");
		System.out.print(((SmartInterpreter) interp).getConsole());

		// Affichage de la grille d'arrivée
		System.out.println("--------------------------- Grille d'arrivée ---------------------------\n");
		grid.print();

		// Affichage du débug
		System.out.println("-------------------------------- Débug ----------------------------------\n");
		prog.debug();
		System.out.print("\n");
    }

    private static void runTests() {
        System.out.println("------------- Test des fichiers dans le répertoire tests : -------------\n");
        testFile("nofile");
        testFile("good0");
        testFile("good1");
        testFile("good2");
        testFile("good3");
        testFile("good4");
        testFile("good5");
        testFile("bad0");
        testFile("bad1");
        testFile("bad2");
        testFile("bad3");
        testFile("bad4");
        testFile("bad5");
        testFile("bad6");
		System.out.println("---------------------------- Fin des tests. ----------------------------\n");
    }

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
        		grid.print();
        		p.eval();
        		System.out.println("Grille après execution :");
        		grid.print();
        		System.out.println(" ==> Fichier correct.");
        	}
        	catch (IOException e) {
        		System.out.println(" ==> Fichier incorrect. Cause: [" + e.getMessage() + "]");
        	} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
        System.out.println();
    }
}
