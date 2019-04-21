import java.io.IOException;

public interface Instr {
	void eval(ValueEnvironnement hm) throws IOException;
	void debug(ValueEnvironnement hm) throws IOException;
	void setType(ValueEnvironnement hm) throws IOException;
}

class Commande implements Instr {

	private String commande;
	private Expr expression;

	public Commande(String commande, Expr ie) {
		this.expression = ie;
		this.commande = commande;
	}

	public void setType(ValueEnvironnement hm) throws IOException {
		expression.setType(hm);
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
		System.out.print(commande + "(");
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

	public void setType(ValueEnvironnement hm) throws IOException {
		condition.setType(hm);
		body.setType(hm);
	}

	public If(Expr ie,Program body) {
		this.condition = ie;
		this.body = body;
	}

	public void eval(ValueEnvironnement hm) throws IOException {
		if (condition.evalBool(hm)) {
			body.eval(hm);
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

	public Assign(String nom,Expr val) {
		this.name = nom;
		this.value = val;
	}

	public void setType(ValueEnvironnement hm) throws IOException {
		value.setType(hm);
		if (value.getType()==Type.BOOL)
			hm.addBoolean(name,true);
		else if (value.getType()==Type.INT)
			hm.addInteger(name,0);
	}

	@Override
	public void eval(ValueEnvironnement hm) throws IOException {
		Type type=value.getType();
		//System.out.println("de type "+type);
		if(hm.exists(name)==null || type==hm.exists(name)) {
			if(type==Type.BOOL) {
				hm.addBoolean(name, value.evalBool(hm));
			}
			else if(type==Type.INT) {
				hm.addInteger(name, value.evalInt(hm));
			}
		}
		else {
			throw new IOException("Type non compatible "+name+" de type "+name+" de type "+hm.exists(name)+" n'est pas de type "+type);
		}

	}

	@Override
	public void debug(ValueEnvironnement hm) throws IOException {
		Type type=value.getType();
		if(hm.exists(name)==null || type==hm.exists(name)) {
			if(hm.exists(name)==null) {
				System.out.print("Assign ");
			}
			System.out.print(name+"=");
			value.debug(hm);
			if(value.getType()==Type.BOOL) {
				hm.addBoolean(name, value.evalBool(hm));
				System.out.println("["+value.evalBool(hm)+"]");
			}
			else if(value.getType()==Type.INT) {
				hm.addInteger(name, value.evalInt(hm));
				System.out.println("["+value.evalInt(hm)+"]");
			}


		}
		else {
			throw new IOException("Type non compatible "+name+" de type "+name+" de type "+hm.exists(name)+" n'est pas de type "+type);
		}

	}

	public String toString() {
		return name+"="+value;
	}

}

class While implements Instr {
	private Expr condition;
	private Program body;

	public While(Expr ie,Program body) {
		this.condition = ie;
		this.body = body;
	}

	public void setType(ValueEnvironnement hm) throws IOException {
		condition.setType(hm);
		body.setType(hm);
	}

	public void eval(ValueEnvironnement hm) throws IOException {
		
		while(condition.evalBool(hm)) {
			body.eval(hm);
		}
	}

	public void debug(ValueEnvironnement hm) throws IOException {
		System.out.print("While(");
		condition.debug(hm);
		System.out.println(")[");
		body.debug(hm);
		System.out.println("]");
	}

	@Override
	public String toString() {
		return "While(" + condition + ")[\n" + body + "]";
	}
}
