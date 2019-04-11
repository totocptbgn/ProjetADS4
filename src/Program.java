import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Program {
	
	private ArrayList<Instr> liste;
	
	public Program() {
		this.liste = new ArrayList<>();
	}
	
	public void eval(Map<String,Integer> hm) throws IOException {
		for (Instr instr : liste) {
			instr.eval(hm);
		}
	}
	
	public void debug() {
		Map<String,Integer> hm=new HashMap<String,Integer>();
		for (Instr instr : liste) {
			instr.debug(hm);
		}
	}
	
	public void add(Instr instr) {
		liste.add(0, instr);
	}
	
	public String toString() {
		String ens = "";
		for (Instr instr : liste) {
			ens = ens + instr.toString() + "\n";
		}
		return ens;
	}
}
