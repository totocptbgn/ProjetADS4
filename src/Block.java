import java.io.IOException;
import java.util.ArrayList;

public class Block {
	private ArrayList<Instr> list;
	
	public Block() {
		this.list = new ArrayList<>();
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
	
	public void debug(ValueEnvironnement hm) throws IOException {
		System.out.println("  ");
		for (Instr instr : list) {
			instr.debug(hm);
		}
	}
	
	public void add(Instr instr) {
		list.add(0, instr);
	}

	public String toString() {
		String ens = "  ";
		for (Instr instr : list) {
			ens = ens + instr.toString() + "\n  ";
		}
		return ens;
	}
}