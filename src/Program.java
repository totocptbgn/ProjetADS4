import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Program {
	private ArrayList<Instr> liste;
	
	public Program() {
		this.liste = new ArrayList<>();
	}

	/**
	 * Fonction qui va interpreter le programme, c'est-à-dire parcourir les instructions et calculer les expressions
	 * puis les executer sur la grille.
	 *
	 * @param hm HashMap servant à faire lien entre les varible (String) et leurs valeurs (Integer).
	 * @throws IOException Si la commande à executer n'existe pas, alors une exception est levée.
	 */

	public void eval(Map<String,Integer> hm) throws IOException {
		for (Instr instr : liste) {
			instr.eval(hm);
		}
	}

	/**
	 * Fonction servant à afficher les fonction et afficher les valeurs des expressions.
	 */

	public void debug() {
		Map<String,Integer> hm=new HashMap<>();
		for (Instr instr : liste) {
			instr.debug(hm);
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
