import java.io.IOException;

public class SmartInterpreter implements Interpreter {

	private static Grid grille;

	public static void avancer(int dist) {
		grille.avancer(dist);
	}

	public static void tourner(int rot) {
		grille.tourner(rot);
	}

	public static void ecrire(int val) {
		grille.ecrire(val);
	}

	public static int lire() {
		return grille.lire();
	}
	
	public static int getWidth() {
		return grille.getSizeX();
	}
	
	public static int getHeight() {
		return grille.getSizeY();
	}

	public void run(Program prog, Grid initGrid) {
		grille = initGrid;
		try {
			prog.eval();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setGrille(Grid initGrid) {
		grille = initGrid;
	}
}
