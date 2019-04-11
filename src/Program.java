import java.io.IOException;
import java.util.ArrayList;
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
	
	public String toString() {
		String ens = "";
		for (Instr instr : liste) {
			ens = ens + instr.toString();
		}
		return ens;
	}
}
