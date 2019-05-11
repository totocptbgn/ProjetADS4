import java.io.IOException;
import java.util.ArrayList;

public interface Instr {
	void eval(ValueEnvironnement hm) throws IOException, ExecutionException;

	void debug(ValueEnvironnement hm) throws IOException, ExecutionException;

	void setType(ValueEnvironnement hm) throws IOException, ExecutionException;
}

class Commande implements Instr {

	private String commande;
	private Expr expression;

	public Commande(String commande, Expr ie) {
		this.expression = ie;
		this.commande = commande;
	}

	public void setType(ValueEnvironnement hm) throws IOException, ExecutionException {
		expression.setType(hm);
		if (expression.getType() != Type.INT)
			throw new ExecutionException("Une commande attent un entier");
	}

	public void eval(ValueEnvironnement hm) throws IOException, ExecutionException {
		switch (commande) {
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
			throw new ExecutionException("Commande " + commande + " introuvable.");
		}
	}

	public void debug(ValueEnvironnement hm) throws IOException, ExecutionException {
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

	private boolean hasElse = false;
	private Program elseBody;

	public void setType(ValueEnvironnement hm) throws IOException, ExecutionException {
		condition.setType(hm);
		if (condition.getType() != Type.BOOL)
			throw new ExecutionException("Condition doit être un booleen");
		body.setType(hm);
		if (hasElse) {
			elseBody.setType(hm);
		}
	}

	public If(Expr ie, Program body, Program elseBody) {
		this.condition = ie;
		this.body = body;
		if (elseBody != null) {
			this.hasElse = true;
			this.elseBody = elseBody;
		}
	}

	public void eval(ValueEnvironnement hm) throws IOException, ExecutionException {
		if (condition.evalBool(hm)) {
			body.eval(hm);
		} else {
			if (hasElse) {
				elseBody.eval(hm);
			}
		}
	}

	public void debug(ValueEnvironnement hm) throws IOException, ExecutionException {
		System.out.print("If (");
		condition.debug(hm);
		System.out.println(")[");
		body.debug(hm);
		System.out.println(Block.getIndent() + "]");
		if (hasElse) {
			System.out.println(Block.getIndent() + "Else [");
			elseBody.debug(hm);
			System.out.println(Block.getIndent() + "]");
		}
	}

	@Override
	public String toString() {
		String s = "If(" + condition + ")[\n" + body + Block.getIndent() + "]";
		if (hasElse) {
			s += Block.getIndent() + "Else [\n" + elseBody + Block.getIndent() + "]";
		}
		return s;
	}
}

class New extends Assign {

	public New(String nom, Expr val) {
		super(nom, val);
	}

	@Override
	public void eval(ValueEnvironnement hm) throws IOException, ExecutionException {
		Type type = value.getType();
		// System.out.println(name+" de type "+type);
		if (type == Type.BOOL) {
			hm.newBoolean(name, value.evalBool(hm));
		} else if (type == Type.INT) {
			hm.newInteger(name, value.evalInt(hm));
		}
	}

	@Override
	public void debug(ValueEnvironnement hm) throws IOException, ExecutionException {
		System.out.print("New ");
		System.out.print(name + "=");
		value.debug(hm);
		if (value.getType() == Type.BOOL) {
			hm.newBoolean(name, value.evalBool(hm));
			System.out.println("[" + value.evalBool(hm) + "]");
		} else if (value.getType() == Type.INT) {
			hm.newInteger(name, value.evalInt(hm));
			System.out.println("[" + value.evalInt(hm) + "]");
		}
	}

	public void setType(ValueEnvironnement hm) throws IOException, ExecutionException {
		value.setType(hm);
		Type type = value.getType();
		if (hm.defined(name) == null) {
			if (type == Type.BOOL) {
				hm.newBoolean(name, false);
			} else if (type == Type.INT) {
				hm.newInteger(name, 0);
			}
		} else {
			throw new ExecutionException("La variable " + name + " existe déjà dans le bloc.");
		}
	}

	public String toString() {
		return "New " + name + "=" + value;
	}

}

class Assign implements Instr {
	protected String name;
	protected Expr value;

	public Assign(String nom, Expr val) {
		this.name = nom;
		this.value = val;
	}

	public void setType(ValueEnvironnement hm) throws IOException, ExecutionException {
		value.setType(hm);
		Type type = value.getType();
		if (hm.defined(name) == null || type == hm.defined(name)) {
			if (type == Type.BOOL) {
				hm.addBoolean(name, false);
			} else if (type == Type.INT) {
				hm.addInteger(name, 0);
			}
		} else {
			throw new ExecutionException(
					"Type non compatible " + name + " de type " + hm.exists(name) + " n'est pas de type " + type);
		}
	}

	@Override
	public void eval(ValueEnvironnement hm) throws IOException, ExecutionException {
		Type type = value.getType();
		if (type == Type.BOOL) {
			hm.addBoolean(name, value.evalBool(hm));
		} else if (type == Type.INT) {
			hm.addInteger(name, value.evalInt(hm));
		}
	}

	@Override
	public void debug(ValueEnvironnement hm) throws IOException, ExecutionException {
		Type type = value.getType();
		if (hm.exists(name) == null) {
			System.out.print("Assign ");
		}
		System.out.print(name + "=");
		value.debug(hm);
		if (type == Type.BOOL) {
			hm.addBoolean(name, value.evalBool(hm));
			System.out.println("[" + value.evalBool(hm) + "]");
		} else if (type == Type.INT) {
			hm.addInteger(name, value.evalInt(hm));
			System.out.println("[" + value.evalInt(hm) + "]");
		}
	}

	public String toString() {
		return name + "=" + value;
	}
}

class While implements Instr {
	private Expr condition;
	private Program body;

	public While(Expr ie, Program body) {
		this.condition = ie;
		this.body = body;
	}

	public void setType(ValueEnvironnement hm) throws IOException, ExecutionException {
		condition.setType(hm);
		if (condition.getType() != Type.BOOL)
			throw new ExecutionException("Condition doit être un booleen");
		body.setType(hm);
	}

	public void eval(ValueEnvironnement hm) throws IOException, ExecutionException {
		while (condition.evalBool(hm)) {
			body.eval(hm);
		}
	}

	public void debug(ValueEnvironnement hm) throws IOException, ExecutionException {
		System.out.print("While(");
		condition.debug(hm);
		System.out.println(")[");
		body.debug(hm);
		System.out.println(Block.getIndent() + "]");
	}

	@Override
	public String toString() {
		return "While(" + condition + ")[\n" + body + Block.getIndent() + "]";
	}
}

class Block implements Instr {
	protected ArrayList<Instr> list;
	private static int indent = 0;

	public Block() {
		this.list = new ArrayList<>();
	}

	void remove() {
		this.list.remove(0);
	}

	public void eval(ValueEnvironnement hm) throws IOException, ExecutionException {
		hm.open();
		for (Instr instr : list) {
			try {
				instr.eval(hm);
			} catch (ReturnException re) {
				hm.close();
				throw re;
			}
		}
		hm.close();
	}

	public void setType(ValueEnvironnement hm) throws IOException, ExecutionException {
		hm.open();
		for (Instr instr : list) {
			try {
				instr.setType(hm);
			} catch (ReturnException re) {
				hm.close();
				throw re;
			}
		}
		hm.close();
	}

	public static String getIndent() {
		String ens = "";
		for (int i = 0; i < indent; i++) {
			ens = ens + " ";
		}
		return ens;
	}

	public void debug(ValueEnvironnement hm) throws IOException, ExecutionException {
		indent = indent + 1;
		hm.open();
		for (Instr instr : list) {
			System.out.print(getIndent());
			instr.debug(hm);
		}
		hm.close();
		indent = indent - 1;
	}

	public void add(Instr instr) {
		list.add(0, instr);
	}

	public String toString() {
		indent = indent + 1;
		String ens = "";
		for (Instr instr : list) {
			ens = ens + getIndent() + instr.toString() + "\n";
		}
		indent = indent - 1;
		return ens;
	}
}

class Fonction implements Instr {

	private String nom;
	private ArrayList<String> arguments;
	private Block body;
	private Type t;
	private boolean typeDefine = false;

	public String getNom() {
		return nom;
	}

	public int getNbArguments() {
		return arguments.size();
	}

	public Fonction(String nom, ArrayList<String> arguments, Block body) {
		this.nom = nom;
		this.arguments = arguments;
		this.body = body;
	}

	@Override
	public void eval(ValueEnvironnement hm) {
		hm.newFonction(this);
	}

	public int evalInt(ValueEnvironnement hm, ArrayList<Expr> array) throws IOException, ExecutionException {
		if (t != Type.INT)
			throw new ExecutionException("La fonction " + nom + " ne renvoie pas un entier");
		for (int i = 0; i < array.size(); i++) {
			body.add(new New(this.arguments.get(i), array.get(i)));
		}

		try {
			body.eval(hm);
		} catch (ReturnException re) {
			return re.getIntRes();
		} finally {
			for (int i = 0; i < array.size(); i++) {
				body.remove();
			}
		}
		return -1;

	}

	public boolean evalBool(ValueEnvironnement hm, ArrayList<Expr> array) throws IOException, ExecutionException {
		if (t != Type.BOOL)
			throw new ExecutionException("La fonction " + nom + " ne renvoie pas un booleen");
		for (int i = 0; i < array.size(); i++) {
			body.add(new New(this.arguments.get(i), array.get(i)));

		}
		try {
			body.eval(hm);
		} catch (ReturnException re) {
			return re.getBoolRes();
		} finally {
			for (int i = 0; i < array.size(); i++) {
				body.remove();
			}
		}
		return false;
	}

	public void eval(ValueEnvironnement hm, ArrayList<Expr> array) throws IOException, ExecutionException {
		for (int i = 0; i < array.size(); i++) {
			body.add(new New(this.arguments.get(i), array.get(i)));
		}
		try {
			body.eval(hm);
		} catch (ReturnException re) {}
		finally {
			for (int i = 0; i < array.size(); i++) {
				body.remove();
			}
		}
	}

	@Override
	public void debug(ValueEnvironnement hm) {
		hm.newFonction(this);
		System.out.print(this);

	}

	public void debug(ValueEnvironnement hm, ArrayList<Expr> arguments) throws IOException, ExecutionException {
		String s = "def " + nom + " (";
		for (int i = 0; i < arguments.size(); i++) {
			s = s + arguments;
			if (i != arguments.size() - 1) {
				s = s + ", ";
			}
		}
		s = s + "):\n";
		System.out.print(s);
		for (int i = 0; i < arguments.size(); i++) {
			body.add(new New(this.arguments.get(i), arguments.get(i)));
		}
		try {
			body.debug(hm);
		} finally {
			for (int i = 0; i < arguments.size(); i++) {
				body.remove();
			}
		}
	}

	@Override
	public void setType(ValueEnvironnement hm) {
		hm.newFonction(this);
	}

	public void setType(ValueEnvironnement hm, ArrayList<Expr> arguments) throws IOException, ExecutionException {
		for (int i = 0; i < arguments.size(); i++) {
			for (int j = i + 1; j < arguments.size(); j++) {
				if (arguments.get(i).equals(arguments.get(j)))
					throw new IOException("Argument en double dans la fonction " + nom);
			}
		}
		for (int i = 0; i < arguments.size(); i++) {
			body.add(new New(this.arguments.get(i), arguments.get(i)));
		}
		Type type = null;
		try {
			body.setType(hm);
		} catch (ReturnException re) {
			type = re.getType();
		} finally {
			for (int i = 0; i < arguments.size(); i++) {
				body.remove();
			}
		}
		if (!typeDefine) {
			typeDefine = true;
		} else if (type != t) {
			throw new IOException("Plusieurs types de retour pour " + nom);
		}
		t = type;

	}

	public Type getType() {
		return t;
	}

	public String toString() {
		String s = "def " + nom + " (";
		for (int i = 0; i < arguments.size(); i++) {
			s = s + arguments.get(i);
			if (i != arguments.size() - 1) {
				s = s + ", ";
			}
		}
		s = s + "):\n" + body;
		return s;
	}
}

class Return implements Instr {
	private Expr expression;

	public Return(Expr e) {
		expression = e;
	}

	@Override
	public void eval(ValueEnvironnement hm) throws IOException, ExecutionException {
		Type t = expression.getType();
		if (t == Type.INT)
			throw new ReturnException(expression.evalInt(hm));
		else {
			throw new ReturnException(expression.evalBool(hm));
		}
	}

	@Override
	public void debug(ValueEnvironnement hm) throws IOException, ExecutionException {
		System.out.print("return ");
		expression.debug(hm);
		System.out.println(";");
		Type t = expression.getType();
		if (t == Type.INT)
			throw new ReturnException(expression.evalInt(hm));
		else {
			throw new ReturnException(expression.evalBool(hm));
		}
	}

	@Override
	public void setType(ValueEnvironnement hm) throws IOException, ExecutionException {
		expression.setType(hm);
		if (expression.getType() == Type.INT)
			throw new ReturnException(0);
		else {
			throw new ReturnException(false);
		}

	}

	public String toString() {
		return "return " + expression + ";";
	}
}

class Call implements Instr {
	private ArrayList<Expr> arguments;
	private String nom;

	public Call(String nom, ArrayList<Expr> arguments) {
		this.nom = nom;
		this.arguments = arguments;
	}

	@Override
	public void eval(ValueEnvironnement hm) throws IOException, ExecutionException {
		Fonction f = hm.searchFonction(nom, arguments.size());
		if (f == null)
			throw new ExecutionException("La fonction " + nom + "avec " + arguments.size() + " arguments n'existe pas");
		f.eval(hm, arguments);

	}

	public void debug(ValueEnvironnement hm) throws IOException, ExecutionException {
		Fonction f = hm.searchFonction(nom, arguments.size());
		if (f == null)
			throw new ExecutionException("La fonction " + nom + "avec " + arguments.size() + " n'existe pas");
		System.out.print("Fonction " + nom);
		System.out.print("{");
		for (int i = 0; i < arguments.size(); i++) {
			arguments.get(i).debug(hm);
			if (i != arguments.size() - 1)
				System.out.print(",");
		}
		System.out.println("}");
	}

	@Override
	public void setType(ValueEnvironnement hm) throws IOException, ExecutionException {
		Fonction f = hm.searchFonction(nom, arguments.size());
		if (f == null) {
			throw new ExecutionException("La fonction " + nom + " n'existe pas");
		}
		f.setType(hm, arguments);
	}

	public String toString() {
		StringBuilder s = new StringBuilder("Fonction " + nom + "(");
		for (int i = 0; i < arguments.size(); i++) {
			s.append(arguments.get(i));
			if (i != arguments.size() - 1) {
				s.append(",");
			}
		}
		return s + ");";
	}
}

class TryCatch implements Instr {

	private Program bodyTry;
	private Program bodyCatch;

	public void setType(ValueEnvironnement hm) throws IOException, ExecutionException {
		bodyTry.setType(hm);
		bodyCatch.setType(hm);
	}

	public TryCatch(Program bodyTry, Program bodyCatch) {
		this.bodyTry = bodyTry;
		this.bodyCatch = bodyCatch;
	}

	public void eval(ValueEnvironnement hm) throws IOException, ExecutionException {
		try {
			bodyTry.eval(hm);
		} catch (ExecutionException e) {
			bodyCatch.eval(hm);
		}
	}

	public void debug(ValueEnvironnement hm) throws IOException, ExecutionException {
		System.out.println("Try {");
		bodyTry.debug(hm);
		System.out.print(Block.getIndent());
		System.out.println("} Catch {");
		bodyCatch.debug(hm);
		System.out.print(Block.getIndent());
		System.out.println("}");
	}

	@Override
	public String toString() {
		String s = "Try {\n";
		s += bodyTry.toString();
		s += Block.getIndent();
		s += "} Catch {\n";
		s += bodyCatch.toString();
		s += Block.getIndent();
		s += "}";
		return s;
	}
}
