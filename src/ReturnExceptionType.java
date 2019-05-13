public class ReturnExceptionType extends TypeException {
	private Type type;

	public ReturnExceptionType(Type t) {
		super("Un return doit ce trouver dans une fonction.");
		type = t;
	}
	public Type getType() {
		return type;
	}
	
}
