import java.util.HashMap;
import java.util.Map;

public class ValueEnvironnement {
	private Map<String,Integer> integers;
	private Map<String,Boolean> booleans;
	public ValueEnvironnement () {
		integers=new HashMap<String,Integer>();
		booleans=new HashMap<String,Boolean>();
	}
	public void addInteger(String nom,int value) {
		integers.put(nom,value);
	}
	public void addBoolean(String nom,boolean value) {
		booleans.put(nom,value);
	}

	public Type exists(String nom) {
		if (integers.get(nom) != null) {
			return Type.INT;
		}
		else if (booleans.get(nom) != null) {
			return Type.BOOL;
		}
		return null;
	}
	public int getInteger(String nom) {
		return integers.get(nom);
		
	}

	public boolean getBoolean(String nom) {
		return booleans.get(nom);
	}

}
