import java.util.HashMap;
import java.util.Map;

public class ValueEnvironnement {
	private Map<String,Integer> Integers;
	private Map<String,Boolean> Booleans;
	public ValueEnvironnement () {
		Integers=new HashMap<String,Integer>();
		Booleans=new HashMap<String,Boolean>();
	}
	public void addInteger(String nom,int value) {
		Integers.put(nom,value);
	}
	public void addBoolean(String nom,boolean value) {
		Booleans.put(nom,value);
	}
	public Type exists(String nom) {
		if(Integers.get(nom)!=null) {
			return Type.INT;
		}
		else if(Booleans.get(nom)!=null) {
			return Type.BOOL;
		}
		return null;
	}
	
}
