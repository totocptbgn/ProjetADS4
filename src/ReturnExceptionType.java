
public class ReturnExceptionType extends TypeException {

	private Type type;

	public ReturnExceptionType(int i) {
		super("Un return doit ce trouver dans une fonction.");
		type = Type.INT;
	}
	public ReturnExceptionType(boolean b) {
		super("Un return doit ce trouver dans une fonction.");
		type = Type.BOOL;
	}
	public Type getType() {
		return type;
	}
	
}
