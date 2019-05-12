import java.io.IOException;
import java.util.ArrayList;

public interface Instr {
	void eval(ValueEnvironnement hm) throws ExecutionException;

	void debug(ValueEnvironnement hm) throws ExecutionException;

	void setType(ValueEnvironnement hm) throws TypeException;
}

class Commande implements Instr {

	private String commande;
	private Expr expression;

	public Commande(String commande, Expr ie) {
		this.expression = ie;
		this.commande = commande;
	}

	public void setType(ValueEnvironnement hm) throws TypeException {
		expression.setType(hm);
		if (expression.getType() != Type.INT)
			throw new TypeException("La commande "+this+" attent un entier, "+expression+" est de type "+expression.getType());
	}

	public void eval(ValueEnvironnement hm) throws ExecutionException {
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

	public void debug(ValueEnvironnement hm) throws ExecutionException {
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

	public void setType(ValueEnvironnement hm) throws TypeException {
		ReturnExceptionType e=null;
		condition.setType(hm);
		if (condition.getType() != Type.BOOL)
			throw new TypeException("La condition "+condition+" n'est pas un booleen (type:"+condition.getType()+")");
		try {
			body.setType(hm);
		}
		catch(ReturnExceptionType er) {
			e=er;
		}
		if (hasElse) {
			try {
				elseBody.setType(hm);
			}
			catch(ReturnExceptionType er) {
				if(e!=null && e.getType()!=er.getType()) throw new TypeException("2 returns de type different (types:"+e.getType()+" et "+er.getType()+") dans \n"+this);
				e=er;
			}
		}
		if(e!=null) throw e;
	}

	public If(Expr ie, Program body, Program elseBody) {
		this.condition = ie;
		this.body = body;
		if (elseBody != null) {
			this.hasElse = true;
			this.elseBody = elseBody;
		}
	}

	public void eval(ValueEnvironnement hm) throws ExecutionException {
		if (condition.evalBool(hm)) {
			body.eval(hm);
		} else {
			if (hasElse) {
				elseBody.eval(hm);
			}
		}
	}

	public void debug(ValueEnvironnement hm) throws ExecutionException {
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
	public void eval(ValueEnvironnement hm) throws ExecutionException {
		Type type = value.getType();
		if (type == Type.BOOL) {
			hm.newBoolean(name, value.evalBool(hm));
		} else if (type == Type.INT) {
			hm.newInteger(name, value.evalInt(hm));
		}
	}

	@Override
	public void debug(ValueEnvironnement hm) throws ExecutionException {
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

	public void setType(ValueEnvironnement hm) throws TypeException {
		value.setType(hm);
		Type type = value.getType();
		if (hm.defined(name) == null) {
			if (type == Type.BOOL) {
				hm.newBoolean(name, false);
			} else if (type == Type.INT) {
				hm.newInteger(name, 0);
			}
		} else {
			throw new TypeException("La variable " + name + " existe déjà dans le bloc.");
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

	public void setType(ValueEnvironnement hm) throws TypeException {
		value.setType(hm);
		Type type = value.getType();
		if (hm.defined(name) == null || type == hm.defined(name)) {
			if (type == Type.BOOL) {
				hm.addBoolean(name, false);
			} else if (type == Type.INT) {
				hm.addInteger(name, 0);
			}
		} else {
			throw new TypeException(
					"Type non compatible " + name + " de type " + hm.exists(name) + " n'est pas de type " + type);
		}
	}

	@Override
	public void eval(ValueEnvironnement hm) throws ExecutionException {
		Type type = value.getType();
		if (type == Type.BOOL) {
			hm.addBoolean(name, value.evalBool(hm));
		} else if (type == Type.INT) {
			hm.addInteger(name, value.evalInt(hm));
		}
	}

	@Override
	public void debug(ValueEnvironnement hm) throws ExecutionException {
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

	public void setType(ValueEnvironnement hm) throws TypeException {
		condition.setType(hm);
		if (condition.getType() != Type.BOOL)
			throw new TypeException("La condition "+condition+" n'est pas un booleen (type:"+condition.getType()+")");
		body.setType(hm);
	}

	public void eval(ValueEnvironnement hm) throws ExecutionException {
		while (condition.evalBool(hm)) {
			body.eval(hm);
		}
	}

	public void debug(ValueEnvironnement hm) throws ExecutionException {
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

	public void eval(ValueEnvironnement hm) throws ExecutionException {
		hm.open();
		ReturnException er=null;
		for (int i=0;i<list.size();i++) {
			list.get(i).eval(hm);
		}
		hm.close();
		if(er!=null) throw er;
	}

	public void setType(ValueEnvironnement hm) throws TypeException {
		hm.open();
		ReturnExceptionType er=null;
		for (int i=0;i<list.size();i++) {
			try {
				list.get(i).setType(hm);
			} catch (ReturnExceptionType re) {
				if(er!=null && re.getType()!=er.getType()) throw new TypeException("2 returns de type different (types:"+re.getType()+" et "+er.getType()+") dans \n"+this);
				er=re;
			}
		}
		hm.close();
		if(er!=null)
			throw er;
	}

	public static String getIndent() {
		String ens = "";
		for (int i = 0; i < indent; i++) {
			ens = ens + " ";
		}
		return ens;
	}

	public void debug(ValueEnvironnement hm) throws ExecutionException {
		indent = indent + 1;
		hm.open();
		for (int i=0;i<list.size();i++) {
			System.out.print(getIndent());
			list.get(i).debug(hm);
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
	private int inUse = 0;
	
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

	public int evalInt(ValueEnvironnement hm, ArrayList<Expr> array) throws ExecutionException {
		if(inUse!=0) {
			for (int i = 0; i < array.size(); i++) {
				body.remove();
			}
		}
		for (int i = 0; i < array.size(); i++) {
			body.add(new New(this.arguments.get(i), array.get(i)));
		}
		inUse+=1;
		try {
			body.eval(hm);
		} catch (ReturnException re) {
			return re.getIntRes();
		} finally {
			inUse-=1;
			if(inUse==0) {
				for (int i = 0; i < array.size(); i++) {
					body.remove();
				}
			}
		}
		return -1;

	}

	public boolean evalBool(ValueEnvironnement hm, ArrayList<Expr> array) throws ExecutionException {
		
		if(inUse!=0) {
			for (int i = 0; i < array.size(); i++) {
				body.remove();
			}
		}
		for (int i = 0; i < array.size(); i++) {
			body.add(new New(this.arguments.get(i), array.get(i)));

		}
		inUse+=1;
		try {
			body.eval(hm);
		} catch (ReturnException re) {
			return re.getBoolRes();
		} finally {
			inUse-=1;
			if(inUse==0) {
				for (int i = 0; i < array.size(); i++) {
					body.remove();
				}
			}
		}
		return false;
	}

	public void eval(ValueEnvironnement hm, ArrayList<Expr> array) throws ExecutionException {
		if(inUse!=0) {
			for (int i = 0; i < array.size(); i++) {
				body.remove();
			}
		}
		for (int i = 0; i < array.size(); i++) {
			body.add(new New(this.arguments.get(i), array.get(i)));
		}
		inUse+=1;
		try {
			body.eval(hm);
		} catch (ReturnException re) {}
		finally {
			inUse-=1;
			if(inUse==0) {
				for (int i = 0; i < array.size(); i++) {
					body.remove();
				}
			}
		}
	}

	@Override
	public void debug(ValueEnvironnement hm) {
		hm.newFonction(this);
		System.out.print(this);

	}

	public void debug(ValueEnvironnement hm, ArrayList<Expr> arguments) throws ExecutionException {
		String s = "def " + nom + " (";
		for (int i = 0; i < arguments.size(); i++) {
			s = s + arguments;
			if (i != arguments.size() - 1) {
				s = s + ", ";
			}
		}
		s = s + "):\n";
		System.out.print(s);
		if(inUse!=0) {
			for (int i = 0; i < arguments.size(); i++) {
				body.remove();
			}
		}
		for (int i = 0; i < arguments.size(); i++) {
			body.add(new New(this.arguments.get(i), arguments.get(i)));
		}
		inUse+=1;
		try {
			body.debug(hm);
		} finally {
			inUse-=1;
			if(inUse==0) {
				for (int i = 0; i < arguments.size(); i++) {
					body.remove();
				}
			}
		}
	}

	@Override
	public void setType(ValueEnvironnement hm) {
		hm.newFonction(this);
	}

	public void setType(ValueEnvironnement hm, ArrayList<Expr> arguments) throws TypeException {
		for (int i = 0; i < arguments.size(); i++) {
			for (int j = i + 1; j < arguments.size(); j++) {
				if (arguments.get(i).equals(arguments.get(j)))
					throw new TypeException("Argument "+arguments.get(i)+" en double dans la declaration de la fonction " + nom+ " à "+arguments.size()+" argument(s)");
			}
		}
		if(inUse!=0) {
			for (int i = 0; i < arguments.size(); i++) {
				body.remove();
			}
		}
		for (int i = 0; i < arguments.size(); i++) {
			body.add(new New(this.arguments.get(i), arguments.get(i)));
		}
		//System.out.println(this);
		inUse+=1;
		Type type = null;
		try {
			body.setType(hm);
		} catch (ReturnExceptionType re) {
			type = re.getType();
			
			if(re.getType()!=type)
				throw new TypeException("Plusieurs types de retour ("+type+" et "+t+") pour " + nom + " à "+arguments.size()+" argument(s)");

		} finally {
			inUse-=1;
			if(inUse==0) {
				for (int i = 0; i < arguments.size(); i++) {
					body.remove();
				}
			}
		}
		if (!typeDefine) {
			typeDefine = true;
		} else if (type != t) {
			throw new TypeException("Plusieurs types de retour ("+type+" et "+t+") pour " + nom + " à "+arguments.size()+" argument(s)");
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
	public void eval(ValueEnvironnement hm) throws ExecutionException {
		Type t = expression.getType();
		if (t == Type.INT)
			throw new ReturnException(expression.evalInt(hm));
		else {
			throw new ReturnException(expression.evalBool(hm));
		}
	}

	@Override
	public void debug(ValueEnvironnement hm) throws ExecutionException {
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
	public void setType(ValueEnvironnement hm) throws TypeException {
		expression.setType(hm);
		throw new ReturnExceptionType(expression.getType());
	}

	public String toString() {
		return "return " + expression + ";";
	}
}

class Call implements Instr {
	private ArrayList<Expr> arguments;
	private String nom;
	private boolean isUsed=false;

	public Call(String nom, ArrayList<Expr> arguments) {
		this.nom = nom;
		this.arguments = arguments;
	}

	@Override
	public void eval(ValueEnvironnement hm) throws ExecutionException {
		isUsed=false;
		Fonction f = hm.searchFonction(nom, arguments.size());
		f.eval(hm, arguments);

	}

	public void debug(ValueEnvironnement hm) throws ExecutionException {
		isUsed=false;
		Fonction f = hm.searchFonction(nom, arguments.size());
		
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
	public void setType(ValueEnvironnement hm) throws TypeException {
		
		if(!isUsed) {
			isUsed=true;
			Fonction f = hm.searchFonction(nom, arguments.size());
			if(f==null) {
				throw new TypeException("La fonction "+nom+" à "+arguments.size()+" argument(s) n'existe pas");
			}
			f.setType(hm, arguments);
		}
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

	public void setType(ValueEnvironnement hm) throws TypeException {
		ReturnExceptionType e=null;
		try {
			bodyTry.setType(hm);
		}
		catch(ReturnExceptionType er) {
			e=er;
		}
		try {
			bodyCatch.setType(hm);
		}
		catch(ReturnExceptionType er) {
			if(e!=null && e.getType()!=er.getType()) throw new TypeException("2 returns de type different dans "+this+" (types:"+e.getType()+" et "+er.getType()+")");
			e=er;
		}
		if(e!=null) throw e;
			
		
	}

	public TryCatch(Program bodyTry, Program bodyCatch) {
		this.bodyTry = bodyTry;
		this.bodyCatch = bodyCatch;
	}

	public void eval(ValueEnvironnement hm) throws ExecutionException {
		try {
			bodyTry.eval(hm);
		} catch (ExecutionException e) {
			bodyCatch.eval(hm);
		}
	}

	public void debug(ValueEnvironnement hm) throws ExecutionException {
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
