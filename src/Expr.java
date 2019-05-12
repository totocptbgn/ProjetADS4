import java.util.ArrayList;

public abstract class Expr {

	Type type;

	abstract void debug(ValueEnvironnement hm) throws ExecutionException;

	public int evalInt(ValueEnvironnement hm) throws ExecutionException {
		throw new ExecutionException(this + " Pas un entier");
	}

	public boolean evalBool(ValueEnvironnement hm) throws ExecutionException {
		throw new ExecutionException(this + " Pas un booleen");
	}

	abstract void setType(ValueEnvironnement hm) throws TypeException;

	public Type getType() {
		return type;
	}
}

class True extends Expr {
	public void setType(ValueEnvironnement hm) {
		type = Type.BOOL;
	}

	@Override
	public boolean evalBool(ValueEnvironnement hm) {
		return true;
	}

	@Override
	void debug(ValueEnvironnement hm) {
		System.out.print("True");
	}

	public String toString() {
		return "True";
	}
}

class False extends Expr {
	public void setType(ValueEnvironnement hm) {
		type = Type.BOOL;
	}

	@Override
	public boolean evalBool(ValueEnvironnement hm) {
		return false;
	}

	@Override
	public void debug(ValueEnvironnement hm) {
		System.out.print("False");
	}

	@Override
	public String toString() {
		return "False";
	}
}

class PosInt extends Expr {

	private final int value;

	public PosInt(int value) {
		this.value = value;
	}

	public void setType(ValueEnvironnement hm) {
		type = Type.INT;
	}

	@Override
	public int evalInt(ValueEnvironnement hm) {
		return value;
	}

	@Override
	public void debug(ValueEnvironnement hm) {
		System.out.print(value);
	}

	@Override
	public String toString() {
		return "PosInt(" + value + ")";
	}
}

class Ope extends Expr {

	private final BinOp op;
	private final Expr arg0, arg1;

	public void setType(ValueEnvironnement hm) throws TypeException {
		type = op.getType();
		arg0.setType(hm);
		arg1.setType(hm);
		op.testType(arg0, arg1);
	}

	public Ope(BinOp op, Expr arg0, Expr arg1) {
		this.op = op;
		this.arg0 = arg0;
		this.arg1 = arg1;
	}

	@Override
	public int evalInt(ValueEnvironnement hm) throws ExecutionException {
		return op.applyInt(arg0.evalInt(hm), arg1.evalInt(hm));
	}

	public boolean evalBool(ValueEnvironnement hm) throws ExecutionException {
		if (arg0.getType() == Type.BOOL && arg1.getType() == Type.BOOL)
			return op.applyBool(arg0.evalBool(hm), arg1.evalBool(hm));
		else if (arg0.getType() == Type.INT && arg1.getType() == Type.INT)
			return op.applyBool(arg0.evalInt(hm), arg1.evalInt(hm));
		else return false;
	}

	public void debug(ValueEnvironnement hm) throws ExecutionException {
		System.out.print("(");
		arg0.debug(hm);
		op.debug();
		arg1.debug(hm);
		System.out.print(")");
		if (op.getType() == Type.BOOL) {
			System.out.print(" [Value:" + this.evalBool(hm));
		} else if (op.getType() == Type.INT) {
			System.out.print(" [Value:" + this.evalInt(hm));
		}
		System.out.print("]");
	}

	@Override
	public String toString() {
		return "Ope(" + op + "," + arg0 + "," + arg1 + ")";
	}
}

class Minus extends Expr {

	private final Expr arg0;

	public Minus(Expr arg0) {
		this.arg0 = arg0;
	}

	public void setType(ValueEnvironnement hm) throws TypeException {
		type = Type.INT;
		arg0.setType(hm);
		if (arg0.getType() != Type.INT)
			throw new TypeException("- attend un entier : "+arg0+" type "+arg0.getType());
	}

	@Override
	public int evalInt(ValueEnvironnement hm) throws ExecutionException {
		return -arg0.evalInt(hm);
	}

	@Override
	public void debug(ValueEnvironnement hm) throws ExecutionException {
		System.out.print("-");
		arg0.debug(hm);
	}

	@Override
	public String toString() {
		return "Minus(" + arg0 + ")";
	}
}

class Not extends Expr {

	private final Expr arg0;

	public Not(Expr arg0) {
		this.arg0 = arg0;
	}

	public void setType(ValueEnvironnement hm) throws TypeException {
		type = Type.BOOL;
		arg0.setType(hm);
		if (arg0.getType() != Type.BOOL)
			throw new TypeException("Not Attend un booleen : "+arg0+" type "+arg0.getType());
	}

	@Override
	public boolean evalBool(ValueEnvironnement hm) throws ExecutionException {
		return !arg0.evalBool(hm);
	}

	@Override
	public void debug(ValueEnvironnement hm) throws ExecutionException {
		System.out.print("!");
		arg0.debug(hm);
	}

	@Override
	public String toString() {
		return "Not(" + arg0 + ")";
	}
}

class Var extends Expr {

	private String nom;
	private ArrayList<Expr> arguments;

	public Var(String nom, ArrayList<Expr> arguments) {
		this.nom = nom;
		this.arguments = arguments;
	}

	@Override
	public void setType(ValueEnvironnement hm) throws TypeException {
		if (arguments == null) {
			type = hm.exists(nom);
			if (type == null)
				throw new TypeException("La variable " + nom + " n'existe pas");
		} else {
			Fonction f = hm.searchFonction(nom, arguments.size());
			if (f == null) {
				throw new TypeException("La fonction " + nom + " avec " + arguments.size() + " argument(s) n'existe pas");
			}
			f.setType(hm, arguments);
			type = f.getType();
			if (type == null)
				throw new TypeException("La fonction " + nom + " à " + arguments.size() + " argument(s) n'a pas de valeur de retour");
		}
	}

	public int evalInt(ValueEnvironnement hm) throws ExecutionException {
		if (arguments == null) {
			return hm.getInteger(nom);
		} else {
			Fonction f = hm.searchFonction(nom, arguments.size());
			return f.evalInt(hm, arguments);
		}

	}

	public boolean evalBool(ValueEnvironnement hm) throws ExecutionException {

		if (arguments == null) {
			return hm.getBoolean(nom);
		} else {
			Fonction f = hm.searchFonction(nom, arguments.size());
			return f.evalBool(hm, arguments);
		}
	}

	@Override
	public void debug(ValueEnvironnement hm) throws ExecutionException {
		if (arguments == null) {
			System.out.print("Var " + nom + "[Value:");
			if (hm.exists(nom) == Type.BOOL)
				System.out.print(this.evalBool(hm));
			else if (hm.exists(nom) == Type.INT)
				System.out.print(this.evalInt(hm));
			else
				System.out.print("None");
			System.out.print("]");
		} else {
			Fonction f = hm.searchFonction(nom, arguments.size());
			System.out.print("Fonction " + nom + "[Value:");
			if (hm.searchFonction(nom, arguments.size()).getType() == Type.BOOL)
				System.out.print(this.evalBool(hm));
			else if (hm.searchFonction(nom, arguments.size()).getType() == Type.INT)
				System.out.print(this.evalInt(hm));
			else
				System.out.print("None");
			System.out.print("]");

			System.out.print("{");
			for (int i = 0; i < arguments.size(); i++) {
				arguments.get(i).debug(hm);
				if (i != arguments.size() - 1)
					System.out.print(",");
			}
			System.out.print("}");
		}

	}

	@Override
	public String toString() {
		if (arguments == null)
			return "Var(" + nom + ")";
		else {
			String s = "Fonction(" + nom + ") {";
			for (int i = 0; i < arguments.size(); i++) {
				s = s + arguments.get(i);
				if (i != arguments.size() - 1) {
					s = s + ",";
				}
			}
			return s + "}";
		}
	}
}

class Lire extends Expr {

	@Override
	public int evalInt(ValueEnvironnement hm) {
		return SmartInterpreter.lire();
	}

	public void setType(ValueEnvironnement hm) {
		type = Type.INT;
	}

	@Override
	public void debug(ValueEnvironnement hm) {
		System.out.print("Lire[Value:" + evalInt(hm) + "]");
	}

	@Override
	public String toString() {
		return "Lire";
	}
}

enum Type {
	BOOL, INT
}