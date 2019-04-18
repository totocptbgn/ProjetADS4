import java.util.Map;
import java.io.IOException;

public interface Instr {
	void eval(Map<String,Integer> hm) throws IOException;
	void debug(Map<String,Integer> hm);
}

class Commande implements Instr {

	private String commande;
	private Expr expression;

	public Commande(String commande, Expr ie) {
		this.expression = ie;
		this.commande = commande;
	}

	public void eval(Map<String,Integer> hm) throws IOException {
		switch(commande) {
			case "Avancer":
				SmartInterpreter.avancer(expression.eval(hm));
				break;
			case "Tourner":
				SmartInterpreter.tourner(expression.eval(hm));
				break;
			case "Ecrire":
				SmartInterpreter.ecrire(expression.eval(hm));
				break;
			default:
				throw new IOException("Commande "+commande+" introuvable.");
		}
	}

	public void debug(Map<String,Integer> hm) {
		System.out.print(commande+"(");
		expression.debug(hm);
		System.out.print(")[" + expression.eval(hm) + "]\n");
	}

	@Override
	public String toString() {
		return commande + "(" + expression + ");";
	}
}
