import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Program {
	private static Grid grille;
	private ArrayList<Instr> liste;
	public static void addGrille(Grid gr) {
		grille=gr;
	}
	public static void avancer(int dist) {
		grille.avancer(dist);
	}
	public static void tourner(int rot) {
		grille.tourner(rot);
	}
	public static void ecrire(int val) {
		grille.ecrire(val);
	}
	public int lire() {
		return grille.lire();
	}
	public Program() {
		this.liste = new ArrayList<>();
	}
	
	public void eval(Map<String,Integer> hm) throws IOException {
		for (Instr instr : liste) {
			instr.eval(hm);
		}
	}
	
	public void add(Instr instr) {
		liste.add(0, instr);
	}
	
	public String toString() {
		String ens = "";
		for (Instr instr : liste) {
			ens = ens + instr.toString() + "\n";
		}
		return ens;
	}
}
