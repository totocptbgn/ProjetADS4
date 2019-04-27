import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ValueEnvironnement {

	private ArrayList<Map<String, Integer>> integers;
	private ArrayList<Map<String,Boolean>> booleans;

	public ValueEnvironnement () {
		integers = new ArrayList<>();
		booleans = new ArrayList<>();
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
		for (Map<String, Integer> integer : integers) {
			if (integer.get(nom) != null)
				return integer.get(nom);
		}
		return null;
	}

	public Boolean getBoolean(String nom) {
		for (Map<String, Boolean> aBoolean : booleans) {
			if (aBoolean.get(nom) != null)
				return aBoolean.get(nom);
		}
		return null;
	}

	public void open() {
		integers.add(0, new HashMap<>());
		booleans.add(0, new HashMap<>());
	}
	
	public void close() {
		integers.remove(0);
		booleans.remove(0);
	}
}
