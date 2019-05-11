
public class ReturnException extends ExecutionException {
	private Type type;
	private Integer intRes=null;
	private Boolean boolRes=null;
	public ReturnException(int i) {
		super("Un return doit ce trouver dans une fonction");
		type=Type.INT;
		intRes=i;
	}
	public ReturnException(boolean b) {
		super("Un return doit ce trouver dans une fonction");
		type=Type.BOOL;
		boolRes=b;
	}
	public Type getType() {
		return type;
	}
	
	public int getIntRes() {
		return intRes;
	}
	
	public boolean getBoolRes() {
		return boolRes;
	}
}
