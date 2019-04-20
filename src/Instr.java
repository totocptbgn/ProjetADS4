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

class If implements Instr {

	private Expr condition;
	private Program body;

	public If(Expr ie,Program body) {
		this.condition = ie;
		this.body=body;
	}

	public void eval(Map<String,Integer> hm) throws IOException {
		if(condition.evalBool(hm)) {
			body.eval();
		}
	}

	public void debug(Map<String,Integer> hm) throws IOException {
		System.out.print("If(");
		condition.debug(hm);
		System.out.println(")["); 
		body.debug(); 
		System.out.println("]");
	}

	@Override
	public String toString() {
		return "If("+condition+")[\n"+body+"]";
	}
	
	
}
class Assign implements Instr {
	private Expr expr;
	
}
class While implements Instr {
	private Expr condition;
	private Program body;

	public While(Expr ie,Program body) {
		this.condition = ie;
		this.body=body;
	}

	public void eval(Map<String,Integer> hm) throws IOException {
		while(condition.evalBool(hm)) {
			body.eval();
		}
	}

	public void debug(Map<String,Integer> hm) throws IOException {
		System.out.print("While(");
		condition.debug(hm);
		System.out.println(")["); 
		body.debug(); 
		System.out.println("]");
	}
	
	@Override
	public String toString() {
		return "While("+condition+")[\n"+body+"]";
	}
	
	
}

