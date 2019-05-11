import java.io.IOException;
import java.util.ArrayList;

public class Program {
	private ArrayList<Instr> list;
	
	public Program() {
		this.list = new ArrayList<>();
	}

	public void eval() throws IOException, ExecutionException {
		ValueEnvironnement hm = new ValueEnvironnement();
		this.initVariables(hm);
		this.setType(hm);
		hm = new ValueEnvironnement();
		this.initVariables(hm);
		for (Instr instr : list) {
			instr.eval(hm);
		}
		hm.close();
	}
	
	private void initVariables(ValueEnvironnement hm) {
		hm.addInteger("Width", SmartInterpreter.getWidth());
		hm.addInteger("Height", SmartInterpreter.getHeight());
	}
	
	public void eval(ValueEnvironnement hm) throws IOException, ExecutionException {
		for (Instr instr : list) {
			instr.eval(hm);
		}
	}

	public void setType(ValueEnvironnement hm) throws IOException, ExecutionException {
		for (Instr instr : list) {
			instr.setType(hm);
		}
	}

	public void debug() throws IOException, ExecutionException {
		ValueEnvironnement hm = new ValueEnvironnement();
		this.initVariables(hm);
		this.setType(hm);
		hm = new ValueEnvironnement();
		this.initVariables(hm);
		for (Instr instr : list) {
			instr.debug(hm);
		}
		hm.close();
	}
	
	public void debug(ValueEnvironnement hm) throws IOException, ExecutionException {
		for (Instr instr : list) {
			if (!(instr instanceof Block))
				System.out.print(Block.getIndent());
			instr.debug(hm);
		}
	}
	
	public void add(Instr instr) {
		list.add(0, instr);
	}

	public String toString() {
		StringBuilder ens = new StringBuilder();
		for (Instr instr : list) {
			if (instr instanceof Block)
				ens.append(instr.toString());
			else
				ens.append(Block.getIndent()).append(instr.toString());
		}
		return ens.toString();
	}
}
