import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)  {
        choixUtilisateur(args, true);
    }

    private static void runMain(String[] args) {
        // Mise en place du programme
        String exeName = "Main";
        IOEnv ioEnv = IOEnv.parseArgs(exeName, args);

        // Création du Parser
        Parser parser = new SmartParser(ioEnv.inProgram);

        // Construction du Program en lisant le ficher grâce au Parser
		Program prog;
		try {
			System.out.println("\n============================================= Parsing de programme.txt =============================================\n");
			prog = parser.parseProgram(exeName, ioEnv.inProgram);
			System.out.println("===> Correct !");
			System.out.println();
		} catch (IOException e) {
			System.out.println("===> Incorrect...");
			System.out.println("     Cause : " + e.getMessage());
			System.out.println();
			return;
		}

		// Construction de la grille en lisant le fichier
		Grid grid = Grid.parseGrid(exeName, ioEnv.inGrid);

		// Affichage de la grille d'origine
		System.out.println("------------------------------------------------- Grille d'origine --------------------------------------------------\n");

		grid.print();

        // Intrepetation et execution du programme sur la grille
        Interpreter interp = new SmartInterpreter();
        interp.run(prog, grid);
        	
		System.out.println("----------------------------------------------------- Console -------------------------------------------------------\n");
		System.out.print(((SmartInterpreter) interp).getConsole());

		// Affichage de la grille d'arrivée
		System.out.println("------------------------------------------------- Grille d'arrivée --------------------------------------------------\n");
		grid.print();

		// Affichage du programme
		System.out.println("--------------------------------------------- Affichage du programme ------------------------------------------------\n");
		System.out.println(prog);

		// Affichage du débug
		System.out.println("----------------------------------------------------- Débug ---------------------------------------------------------\n");
		try {
			prog.debug();
		}
		catch (TypeException e){
			System.out.print("Exception in thread, TypeException: ");
			System.out.println(e.getLocalizedMessage());
		}
		catch (ExecutionException e){
			System.out.print("Exception in thread, ExecutionException: ");
			System.out.println(e.getLocalizedMessage());
		}
		System.out.println(" [Fin du débug.]");
		System.out.print("\n");
    }

    private static void runTests() {
        System.out.println("\n---------------------------------- Test des fichiers dans le répertoire tests : ------------------------------------\n");
        testFile("good0");
        testFile("good1");
        testFile("good2");
        testFile("good3");
        testFile("good4");
        testFile("good5");
        testFile("good6");
        testFile("good7");
        testFile("good8");
        testFile("good9");
        testFile("bad0");
        testFile("bad1");
        testFile("bad2");
        testFile("bad3");
        testFile("bad4");
        testFile("bad5");
        testFile("bad6");
        testFile("bad7");
        testFile("bad8");
        testFile("bad9");
        testFile("bad10");
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
		Program prog;
        String[] args = {"src/tests/" + filename, "src/grille.txt"};

        IOEnv ioEnv = IOEnv.parseArgs(filename, args);
        Grid grid = Grid.parseGrid(filename, ioEnv.inGrid);

        Parser parser = new SmartParser(ioEnv.inProgram);
        SmartInterpreter interp = new SmartInterpreter();

        // Parsing et compilation
        try {
            prog = parser.parseProgram("Tests", reader);
            System.out.println("  - Compilation : Ok.");
        } catch (IOException e) {
            System.out.println("  - Compilation : Fichier incorrect. Cause: [" + e.getMessage() + "]");
			System.out.println("  - Execution : Pas d'execution possible.\n");
			return;
        }

        // Exécution
        if (prog != null) {
        	System.out.println("  - Execution :\n");
        	try {
        		System.out.println("Grille avant éxécution :");
        		grid.print();
        		interp.run(prog, grid);
				System.out.println("Console: ");
				System.out.print(interp.getConsole());
        		System.out.println("Grille après execution :");
        		grid.print();
        	}
        	catch (Exception e) {
        		System.out.println(" ==> Fichier incorrect. Cause: [" + e.getMessage() + "]");
        	}
		}

		System.out.print("Débug: ");
		try {
			System.out.println();
			prog.debug();
			System.out.println();
		} catch (Exception e) {
			System.out.println("Pas de débug possible.");
		}
		System.out.println();
    }

    private static void choixUtilisateur(String [] args, boolean ft) {
		Scanner sc = new Scanner(System.in);

		if (ft){
			System.out.println("+===================================================================================================================+");
			System.out.println("|                                                                                                                   |");
			System.out.println("|        8888888          888                                               888                                     |");
			System.out.println("|          888            888                                               888                                     |");
			System.out.println("|          888            888                                               888                                     |");
			System.out.println("|          888   88888b.  888888  .d88b.  888d888 88888b.  888d888  .d88b.  888888  .d88b.  888  888 888d888        |");
			System.out.println("|          888   888 \"88b 888    d8P  Y8b 888P\"   888 \"88b 888P\"   d8P  Y8b 888    d8P  Y8b 888  888 888P\"          |");
			System.out.println("|          888   888  888 888    88888888 888     888  888 888     88888888 888    88888888 888  888 888            |");
			System.out.println("|          888   888  888 Y88b.  Y8b.     888     888 d88P 888     Y8b.     Y88b.  Y8b.     Y88b 888 888            |");
			System.out.println("|        8888888 888  888  \"Y888  \"Y8888  888     88888P\"  888      \"Y8888   \"Y888  \"Y8888   \"Y88888 888            |");
			System.out.println("|                                                 888                                                               |");
			System.out.println("|                                                 888                    POUR MINI-ROBOT.                           |");
			System.out.println("|                                                 888                    Par Thomas Copt-Bignon & Dao Thauvin.      |");
			System.out.println("|                                                                                                                   |");
			System.out.println("|                                                                                                                   |");
			System.out.println("|        Ce programme vous permet d'exécuter des programmes sur des grilles afin de manipuler un mini-robot         |");
			System.out.println("|        qui pourra parcourir et écrire sur la grille. De base, vous pouvez lancer le programme écrit dans          |");
			System.out.println("|        'programme.txt' sur la grille de 'grille.txt'. Mais vous pouvez aussi utiliser les fichiers dans           |");
			System.out.println("|        les dossiers gridPerso/ et progPerso/. Il est possible de générer des grilles aléatoires en                |");
			System.out.println("|        informant le nom du fichier généré et la taille de la grille. Enfin, vous pouvez lancer les tests          |");
			System.out.println("|        des programmes dans le répertoire tests/ sur la grille de base. Amusez-vous bien !                         |");
			System.out.println("|                                                                                                                   |");
			System.out.println("+===================================================================================================================+\n");
		} else {
			System.out.println("=====================================================================================================================\n");
		}

		System.out.println(" [0]	Lancer le programme.");
		System.out.println(" [1]	Lancer un programme personalisé.");
		System.out.println(" [2]	Lancer la batterie de test.");
		System.out.println(" [3]	Générer une grille aléatoire.");
		System.out.println(" [4]	Quitter.\n");

		boolean b = true;
		while (b){
			System.out.print("Veuillez séléctionnez votre choix : ");
			String c =  sc.nextLine();

			switch (c) {
				case "0":
					runMain(args);
					choixUtilisateur(args, false);
					return;
				case "1":
					b = false;
					break;
				case "2":
					runTests();
					choixUtilisateur(args, false);
					return;
				case "3":
					String genFilename = "";
					boolean bb = true;
					while (bb) {
						System.out.print("Rentrez le nom du fichier généré : ");
						genFilename = sc.nextLine();
						if (genFilename.equals("")){
							System.out.println("\nNom incorrect.");
						} else {
							bb = false;
						}
					}
					bb = true;
					int height = 0;
					while (bb) {
						System.out.print("Rentrez la hauteur de la grille : ");
						height = sc.nextInt();
						if (height < 3 || height > 100){
							System.out.println("\nVeuillez rentrer un nombre entre 3 et 100 inclus.");
						} else {
							bb = false;
						}
					}
					bb = true;
					int width = 0;
					while (bb) {
						System.out.print("Rentrez la largeur de la grille : ");
						width = sc.nextInt();
						if (width < 3 || width > 100){
							System.out.println("\nVeuillez rentrer un nombre entre 3 et 100 inclus.");
						} else {
							bb = false;
						}
					}
					RandomGridGenerator.generateGrid(genFilename,width,height);
					choixUtilisateur(args, false);
					return;
				case "4":
					return;
				default:
					System.out.println("Choix incorrect.");
					break;
			}
		}

		System.out.println("\n [0]	Lancer 'programme.txt'.");
		System.out.println(" [1]	Lancer un programme personalisé.\n");

		String filename = "";
		b = true;
		while (b){
			System.out.print("Veuillez séléctionnez votre choix : ");
			String c =  sc.nextLine();

			if (c.equals("0")){
				filename = "src/programme.txt";
				b = false;
			} else if (c.equals("1")){
				System.out.print("Veuillez rentrer le nom du fichier (situé dans le répertoire progPerso/) : ");
				filename = "src/progPerso/" + sc.nextLine();
				System.out.println();
				File f = new File(filename);
				if (!f.exists()){
					System.out.println("Fichier introuvable.");
				} else {
					b = false;
				}
			} else {
				System.out.println("Choix incorrect.");
			}
		}
		args[0] = filename;

		System.out.println("\n [0]	Utiliser 'grille.txt'.");
		System.out.println(" [1]	Utiliser une grille personalisée.\n");

		filename = "";
		b = true;
		while (b){
			System.out.print("Veuillez séléctionnez votre choix : ");
			String c =  sc.nextLine();

			if (c.equals("0")){
				filename = "src/grille.txt";
				b = false;
			} else if (c.equals("1")){
				System.out.print("Veuillez rentrer le nom du fichier (situé dans le répertoire gridPerso/) : ");
				filename = "src/gridPerso/" + sc.nextLine();
				System.out.println();
				File f = new File(filename);
				if (!f.exists()){
					System.out.println("Fichier introuvable.");
				} else {
					b = false;
				}
			} else {
				System.out.println("Choix incorrect.");
			}
		}
		args[1] = filename;
		runMain(args);
	}
}
