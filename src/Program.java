import java.io.IOException;
import java.util.ArrayList;

public class Program {
	private ArrayList<Instr> list;
	
	public Program() {
		this.list = new ArrayList<>();
	}

	public void eval() throws IOException {
		ValueEnvironnement hm = new ValueEnvironnement();
		this.initVariables(hm);
		this.setType(hm);
		hm = new ValueEnvironnement();
		this.initVariables(hm);
		for (Instr instr : list) {
			instr.eval(hm);
		}
	}
	
	private void initVariables(ValueEnvironnement hm) {
		hm.addInteger("Width", SmartInterpreter.getWidth());
		hm.addInteger("Height", SmartInterpreter.getHeight());
	}
	
	public void eval(ValueEnvironnement hm) throws IOException {
		for (Instr instr : list) {
			instr.eval(hm);
		}
	}
	
	
	public void setType(ValueEnvironnement hm) throws IOException {
		for (Instr instr : list) {
			instr.setType(hm);
		}
	}

	public void debug() throws IOException {
		ValueEnvironnement hm = new ValueEnvironnement();
		this.initVariables(hm);
		this.setType(hm);
		hm = new ValueEnvironnement();
		this.initVariables(hm);
		for (Instr instr : list) {
			instr.debug(hm);
		}
	}
	
	public void debug(ValueEnvironnement hm) throws IOException {
		for (Instr instr : list) {
			instr.debug(hm);
		}
	}
	
	public void add(Instr instr) {
		list.add(0, instr);
	}

	public String toString() {
		String ens = "";
		for (Instr instr : list) {
			ens = ens + instr.toString() + "\n";
		}
		return ens;
	}
}
