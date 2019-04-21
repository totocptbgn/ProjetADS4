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
	 * @throws IOException Si la commande à executer n'existe pas, alors une exception est levée.
	 */

	public void eval() throws IOException {
		ValueEnvironnement hm = new ValueEnvironnement();
		this.setType(hm);
		System.out.println("fin de setType");
		hm = new ValueEnvironnement();
		for (Instr instr : liste) {
			instr.eval(hm);
		}
	}
	
	public void eval(ValueEnvironnement hm) throws IOException {
		for (Instr instr : liste) {
			instr.eval(hm);
		}
	}
	
	
	public void setType(ValueEnvironnement hm) throws IOException {
		for(int i=0;i<liste.size();i++) {
			liste.get(i).setType(hm);
		}
	}
	/**
	 * Fonction servant à afficher les fonction et afficher les valeurs des expressions.
	 */

	public void debug() throws IOException {
		ValueEnvironnement hm=new ValueEnvironnement();
		this.setType(hm);
		hm = new ValueEnvironnement();
		for (Instr instr : liste) {
			instr.debug(hm);
		}
	}
	
	public void debug(ValueEnvironnement hm) throws IOException {
		hm = new ValueEnvironnement();
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
