import java.util.Map;
import java.io.IOException;

public interface Instr {
	void eval(Map<String,Integer> hm) throws IOException;
	void debug(Map<String,Integer> hm) throws IOException;
}

class Commande implements Instr {

	private String commande;
	private Expr expression;

	public Commande(String commande, Expr ie) {
		this.expression = ie;
		this.commande = commande;
		expression.setType();
	}

	public void eval(Map<String,Integer> hm) throws IOException {
		switch(commande) {
			case "Avancer":
				SmartInterpreter.avancer(expression.evalInt(hm));
				break;
			case "Tourner":
				SmartInterpreter.tourner(expression.evalInt(hm));
				break;
			case "Ecrire":
				SmartInterpreter.ecrire(expression.evalInt(hm));
				break;
			default:
				throw new IOException("Commande "+commande+" introuvable.");
		}
	}

	public void debug(Map<String,Integer> hm) throws IOException {
		System.out.print(commande+"(");
		expression.debug(hm);
		System.out.print(")[" + expression.evalInt(hm) + "]\n");
	}

	@Override
	public String toString() {
		return commande + "(" + expression + ");";
	}
}
