import java.io.IOException;
import java.util.ArrayList;

public class Program {
	private ArrayList<Instr> list;
	
	public Program() {
		this.list = new ArrayList<>();
	}

	public void eval() throws IOException {
		ValueEnvironnement hm = new ValueEnvironnement();
		this.setType(hm);
		System.out.println("fin de setType");
		hm = new ValueEnvironnement();
		for (Instr instr : list) {
			instr.eval(hm);
		}
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
		ValueEnvironnement hm=new ValueEnvironnement();
		this.setType(hm);
		hm = new ValueEnvironnement();
		for (Instr instr : list) {
			instr.debug(hm);
		}
	}
	
	public void debug(ValueEnvironnement hm) throws IOException {
		hm = new ValueEnvironnement();
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
