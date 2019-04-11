import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Program{

	private ArrayList<Instr> liste;

	public Program() {
		this.liste = new ArrayList<>();
	}
	
	public void eval(Map<String,Integer> hm) throws IOException {
		for(int i=0;i<liste.size();i++) {
			liste.get(i).eval(hm);
		}
	}
	
	public void add(Instr instr) {
		liste.add(instr);
	}
	
	public String toString() {
		String ens="";
		for(int i=0;i<liste.size();i++) {
			ens=ens+liste.get(i).toString();
		}
		return ens;
	}
}
