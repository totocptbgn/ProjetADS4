import java.io.IOException;
import java.util.ArrayList;

public interface Instr {
	void eval(ValueEnvironnement hm) throws IOException, ExecutionException;
	void debug(ValueEnvironnement hm) throws IOException, ExecutionException;
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

	private boolean hasElse=false;
	private Program elseBody;

	public void setType(ValueEnvironnement hm) throws IOException {
		condition.setType(hm);
		body.setType(hm);
		if (hasElse){
			elseBody.setType(hm);
		}
	}

	public If(Expr ie,Program body,Program elseBody) {
		this.condition = ie;
		this.body = body;
		if(elseBody!=null) {
			this.hasElse = true;
			this.elseBody=elseBody;
		}
	}

	public void eval(ValueEnvironnement hm) throws IOException, ExecutionException {
		if (condition.evalBool(hm)) {
			body.eval(hm);
		} else {
			if (hasElse){
				elseBody.eval(hm);
			}
		}
	}

	public void debug(ValueEnvironnement hm) throws IOException, ExecutionException {
		System.out.print("If (");
		condition.debug(hm);
		System.out.println(")[");
		body.debug(hm);
		System.out.println(Block.getIndent()+"]");
		if (hasElse){
			System.out.println(Block.getIndent()+"Else [");
			elseBody.debug(hm);
			System.out.println(Block.getIndent()+"]");
		}
	}

	@Override
	public String toString() {
		String s = "If(" + condition + ")[\n" + body + Block.getIndent() + "]";
		if (hasElse){
			s += Block.getIndent()+"Else [\n" + elseBody + Block.getIndent() +"]";
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
		Type type=value.getType();
		//System.out.println(name+" de type "+type);
		if(hm.defined(name)==null) {
			if(type==Type.BOOL) {
				hm.newBoolean(name, value.evalBool(hm));
			}
			else if(type==Type.INT) {
				hm.newInteger(name, value.evalInt(hm));
			}
		}
		else {
			throw new ExecutionException("La variable " + name + " existe déjà dans le bloc.");
		}
	}

	@Override
	public void debug(ValueEnvironnement hm) throws IOException, ExecutionException {
		if (hm.defined(name) == null) {
			System.out.print("New ");
			System.out.print(name + "=");
			value.debug(hm);
			if (value.getType() == Type.BOOL) {
				hm.newBoolean(name, value.evalBool(hm));
				System.out.println("["+value.evalBool(hm)+"]");
			}
			else if (value.getType() == Type.INT) {
				hm.newInteger(name, value.evalInt(hm));
				System.out.println("[" + value.evalInt(hm) + "]");
			}
		}
		else {
			throw new ExecutionException("La variable " + name + " existe déjà dans le bloc.");
		}
	}

}

class Assign implements Instr {
	protected String name;
	protected Expr value;
	
	public Assign(String nom,Expr val) {
		this.name = nom;
		this.value = val;
	}

	public void setType(ValueEnvironnement hm) throws IOException {	
		value.setType(hm);
		if (value.getType()==Type.BOOL)
			hm.newBoolean(name,true);
		else if (value.getType()==Type.INT)
			hm.newInteger(name,0);
	}

	@Override
	public void eval(ValueEnvironnement hm) throws IOException, ExecutionException {
		Type type=value.getType();
		//System.out.println(name+" de type "+type);
		if(hm.defined(name)==null || type==hm.defined(name)) {
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
	public void debug(ValueEnvironnement hm) throws IOException, ExecutionException {
		Type type=value.getType();
		if(hm.defined(name)==null || type==hm.defined(name)) {
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
			throw new IOException("Type non compatible "+name+" de type "+hm.exists(name)+" n'est pas de type "+type);
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
		System.out.println(Block.getIndent()+"]");
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

	public void eval(ValueEnvironnement hm) throws IOException, ExecutionException {
		hm.open();
		for (Instr instr : list) {
			instr.eval(hm);
		}
		hm.close();
	}

	public void setType(ValueEnvironnement hm) throws IOException {
		hm.open();
		for (Instr instr : list) {
			instr.setType(hm);
		}
		hm.close();
	}

	public static String getIndent() {
		String ens = "";
		for(int i = 0; i < indent; i++) {
			ens = ens + " ";
		}
		return ens;
	}

	public void debug(ValueEnvironnement hm) throws IOException, ExecutionException {
		indent = indent+1;
		hm.open();
		for (Instr instr : list) {
			System.out.print(getIndent());
			instr.debug(hm);
		}
		hm.close();
		indent = indent-1;
	}

	public void add(Instr instr) {
		list.add(0, instr);
	}


	public String toString() {
		indent = indent+1;
		String ens = "";
		for (Instr instr : list) {
			ens = ens + getIndent() + instr.toString() + "\n";
		}
		indent = indent-1;
		return ens;
	}
}

class Fonction implements Instr {
	private String nom;
	private ArrayList<String> arguments;
	private Program body;
	
	public Fonction(String nom,ArrayList<String> arguments, Program body) {
		this.nom=nom;
		this.arguments=arguments;
		this.body=body;
	}
	
	@Override
	public void eval(ValueEnvironnement hm) throws IOException, ExecutionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(ValueEnvironnement hm) throws IOException, ExecutionException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setType(ValueEnvironnement hm) throws IOException {
		// TODO Auto-generated method stub
		
	}
}
