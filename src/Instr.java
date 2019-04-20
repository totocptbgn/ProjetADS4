import java.io.IOException;

public interface Instr {
	void eval(ValueEnvironnement hm) throws IOException;
	void debug(ValueEnvironnement hm) throws IOException;
}

class Commande implements Instr {

	private String commande;
	private Expr expression;

	public Commande(String commande, Expr ie) {
		this.expression = ie;
		this.commande = commande;
		expression.setType();
	}

	public void eval(ValueEnvironnement hm) throws IOException {
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

	public void debug(ValueEnvironnement hm) throws IOException {
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
		this.body = body;
	}

	public void eval(ValueEnvironnement hm) throws IOException {
		if (condition.evalBool(hm)) {
			body.eval();
		}
	}

	public void debug(ValueEnvironnement hm) throws IOException {
		System.out.print("If(");
		condition.debug(hm);
		System.out.println(")["); 
		body.debug(); 
		System.out.println("]");
	}

	@Override
	public String toString() {
		return "If(" + condition + ")[\n" + body + "]";
	}
}

class Assign implements Instr {
	private String name;
	private Expr value;

	public Assign(String nom, Expr val) {
		this.name = nom;
		this.value = val;
	}

	@Override
	public void eval(ValueEnvironnement hm) throws IOException {

	}

	@Override
	public void debug(ValueEnvironnement hm) throws IOException {

	}
}

class While implements Instr {
	private Expr condition;
	private Program body;

	public While(Expr ie,Program body) {
		this.condition = ie;
		this.body = body;
	}

	public void eval(ValueEnvironnement hm) throws IOException {
		while(condition.evalBool(hm)) {
			body.eval();
		}
	}

	public void debug(ValueEnvironnement hm) throws IOException {
		System.out.print("While(");
		condition.debug(hm);
		System.out.println(")["); 
		body.debug(); 
		System.out.println("]");
	}
	
	@Override
	public String toString() {
		return "While(" + condition + ")[\n" + body + "]";
	}
}

