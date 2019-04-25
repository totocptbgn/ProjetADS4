import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ValueEnvironnement {

	private ArrayList<Map<String, Integer>> integers;
	private ArrayList<Map<String,Boolean>> booleans;

	public ValueEnvironnement () {
		integers = new ArrayList<Map<String,Integer>>();
		booleans = new ArrayList<Map<String,Boolean>>();
		this.open();
	}

	public void addInteger(String nom, int value) {
		integers.get(0).put(nom,value);
	}
	public void addBoolean(String nom, boolean value) {
		booleans.get(0).put(nom,value);
	}

	public Type exists(String nom) {
		if (this.getInteger(nom) != null) {
			return Type.INT;
		} else if (this.getBoolean(nom) != null) {
			return Type.BOOL;
		}
		return null;
	}
	
	public Type defined(String nom) {
		if (this.integers.get(0).get(nom) != null) {
			return Type.INT;
		} else if (this.booleans.get(0).get(nom) != null) {
			return Type.BOOL;
		}
		return null;
	}
	
	public Integer getInteger(String nom) {
		for(int i=0;i<integers.size();i++) {
			if(integers.get(i).get(nom)!=null)
				return integers.get(i).get(nom);
		}
		return null;
		
	}

	public Boolean getBoolean(String nom) {
		for(int i=0;i<booleans.size();i++) {
			if(booleans.get(i).get(nom)!=null)
				return booleans.get(i).get(nom);
		}
		return null;
	}
	public void open() {
		integers.add(0, new HashMap<String,Integer>());
		booleans.add(0, new HashMap<String,Boolean>());
	}
	
	public void close() {
		integers.remove(0);
		booleans.remove(0);
	}
}
