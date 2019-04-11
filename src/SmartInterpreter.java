import java.io.IOException;
import java.util.HashMap;

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
	public void run(Program prog, Grid initGrid) {
		grille=initGrid;
		try {
			prog.eval(new HashMap<String,Integer>());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
