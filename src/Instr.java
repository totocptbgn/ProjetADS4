import java.util.Map;
import java.io.IOException;

public interface Instr {
	void eval(Map<String,Integer> hm) throws IOException;
}

class Commande implements Instr {
	private String commande;
	private IntExpr expression;

	public Commande(String commande, IntExpr ie) {
		this.expression=ie;
		this.commande=commande;
	}

	@Override
	public String toString() {
		return commande+"("+ expression + ")";
	}

	public void eval(Map<String,Integer> hm) throws IOException {
		switch(commande) {
			case "Avancer":
				break;
			case "Tourner":
				break;
			case "Ecrire":
				break;
			default:
				throw new IOException("Commande introuvable");
		}
	}

}
