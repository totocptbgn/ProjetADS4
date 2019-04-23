import java.io.IOException;
import java.util.ArrayList;

public class Program {
	private ArrayList<Block> list;
	
	public Program() {
		this.list = new ArrayList<>();
	}

	public void eval() throws IOException {
		ValueEnvironnement hm = new ValueEnvironnement();
		this.initVariables(hm);
		this.setType(hm);
		hm = new ValueEnvironnement();
		this.initVariables(hm);
		for (Block block : list) {
			block.eval(hm);
		}
	}
	
	private void initVariables(ValueEnvironnement hm) {
		hm.addInteger("Width", SmartInterpreter.getWidth());
		hm.addInteger("Height", SmartInterpreter.getHeight());
	}
	
	public void eval(ValueEnvironnement hm) throws IOException {
		for (Block block : list) {
			block.eval(hm);
		}
	}
	
	
	public void setType(ValueEnvironnement hm) throws IOException {
		for (Block block : list) {
			block.setType(hm);
		}
	}

	public void debug() throws IOException {
		ValueEnvironnement hm = new ValueEnvironnement();
		this.initVariables(hm);
		this.setType(hm);
		hm = new ValueEnvironnement();
		this.initVariables(hm);
		for (Block block : list) {
			block.debug(hm);
		}
	}
	
	public void debug(ValueEnvironnement hm) throws IOException {
		for (Block block : list) {
			block.debug(hm);
		}
	}
	
	public void add(Block block) {
		list.add(0, block);
	}

	public String toString() {
		String ens = "";
		for (Block block : list) {
			ens = ens + block.toString() + "\n";
		}
		return ens;
	}
}
