import java.io.IOException;

public enum BinOp {
	PLUS {
		@Override
		public int applyInt(int x, int y) {
			return x + y;
		}

		public void debug() {
			System.out.print("+");
		}

		public Type getType() {
			return Type.INT;
		}
		public void testType(Expr arg1, Expr arg2) throws TypeException {
			if(arg1.getType()!=arg2.getType() || arg1.getType()!=Type.INT) {
				throw new TypeException("Une operation + n'est pas possible entre "+arg1+ " (type "+arg1.getType()+") et "+arg2+" (type "+arg1.getType()+")");
			}
				
		}
	},
	MINUS {
		@Override
		public int applyInt(int x, int y) {
			return x - y;
		}

		public void debug() {
			System.out.print("-");
		}

		public Type getType() {
			return Type.INT;
		}
		public void testType(Expr arg1, Expr arg2) throws TypeException {
			if(arg1.getType()!=arg2.getType() || arg1.getType()!=Type.INT) {
				throw new TypeException("Une operation - n'est pas possible entre "+arg1+ " (type "+arg1.getType()+") et "+arg2+" (type "+arg1.getType()+")");
			}
				
		}
	},
	TIMES {
		@Override
		public int applyInt(int x, int y) {
			return x * y;
		}

		public void debug() {
			System.out.print("*");
		}

		public Type getType() {
			return Type.INT;
		}
		public void testType(Expr arg1, Expr arg2) throws TypeException {
			if(arg1.getType()!=arg2.getType() || arg1.getType()!=Type.INT) {
				throw new TypeException("Une operation * n'est pas possible entre "+arg1+ " (type "+arg1.getType()+") et "+arg2+" (type "+arg1.getType()+")");
			}
				
		}
	},
	DIVIDE {
		@Override
		public int applyInt(int x, int y) {
			return x / y;
		}

		public void debug() {
			System.out.print("/");
		}

		public Type getType() {
			return Type.INT;
		}
		public void testType(Expr arg1, Expr arg2) throws TypeException {
			if(arg1.getType()!=arg2.getType() || arg1.getType()!=Type.INT) {
				throw new TypeException("Une operation / n'est pas possible entre "+arg1+ " (type "+arg1.getType()+") et "+arg2+" (type "+arg1.getType()+")");
			}
				
		}
	},
	AND {
		@Override
		public boolean applyBool(boolean a, boolean b) {
			return a && b;
		}

		public void debug() {
			System.out.print("Et");
		}

		public Type getType() {
			return Type.BOOL;
		}
		public void testType(Expr arg1, Expr arg2) throws TypeException {
			if(arg1.getType()!=arg2.getType() || arg1.getType()!=Type.BOOL) {
				throw new TypeException("Une operation Et n'est pas possible entre "+arg1+ " (type "+arg1.getType()+") et "+arg2+" (type "+arg1.getType()+")");
			}
				
		}
	},
	OR {
		@Override
		public boolean applyBool(boolean a, boolean b) {
			return a || b;
		}

		public void debug() {
			System.out.print("Ou");
		}

		public Type getType() {
			return Type.BOOL;
		}
		public void testType(Expr arg1, Expr arg2) throws TypeException {
			if(arg1.getType()!=arg2.getType() || arg1.getType()!=Type.BOOL) {
				throw new TypeException("Une operation Ou n'est pas possible entre "+arg1+ " (type "+arg1.getType()+") et "+arg2+" (type "+arg1.getType()+")");
			}
				
		}
	},
	SUP {
		@Override
		public boolean applyBool(int x, int y) {
			return x > y;
		}

		public void debug() {
			System.out.print(">");
		}

		public Type getType() {
			return Type.BOOL;
		}
		public void testType(Expr arg1, Expr arg2) throws TypeException {
			if(arg1.getType()!=arg2.getType() || arg1.getType()!=Type.INT) {
				throw new TypeException("Une comparaison > n'est pas possible entre "+arg1+ " (type "+arg1.getType()+") et "+arg2+" (type "+arg1.getType()+")");
			}
				
		}
	},
	INF {
		@Override
		public boolean applyBool(int x, int y) {
			return x < y;
		}

		public void debug() {
			System.out.print("<");
		}

		public Type getType() {
			return Type.BOOL;
		}
		public void testType(Expr arg1, Expr arg2) throws TypeException {
			if(arg1.getType()!=arg2.getType() || arg1.getType()!=Type.INT) {
				throw new TypeException("Une comparaison < n'est pas possible entre "+arg1+ " (type "+arg1.getType()+") et "+arg2+" (type "+arg1.getType()+")");
			}
				
		}
	},
	EQ {
		public Type getType() {
			return Type.BOOL;
		}

		@Override
		public boolean applyBool(int x, int y) {
			return x == y;
		}

		@Override
		public boolean applyBool(boolean a, boolean b) {
			return a == b;
		}

		public void debug() {
			System.out.print("=");
		}
		public void testType(Expr arg1, Expr arg2) throws TypeException {
			if(arg1.getType()!=arg2.getType()) {
				throw new TypeException("Une comparaison = n'est pas possible entre "+arg1+ " (type "+arg1.getType()+") et "+arg2+" (type "+arg1.getType()+")");
			}
				
		}

	},
	NOTEQ {
		public Type getType() {
			return Type.BOOL;
		}

		@Override
		public boolean applyBool(int x, int y) {
			return x != y;
		}

		@Override
		public boolean applyBool(boolean a, boolean b) {
			return a != b;
		}

		public void debug() {
			System.out.print("!=");
		}
		
		public void testType(Expr arg1, Expr arg2) throws TypeException {
			if(arg1.getType()!=arg2.getType()) {
				throw new TypeException("Une comparaison != n'est pas possible entre "+arg1+ " (type "+arg1.getType()+") et "+arg2+" (type "+arg1.getType()+")");
			}
				
		}

	};
	
	public abstract void testType(Expr arg1, Expr arg2) throws TypeException;
	
	public int applyInt(int x, int y) throws ExecutionException {
		throw new ExecutionException("Cette expression ne peut renvoyer un int.");
	}

	public boolean applyBool(int x, int y) throws ExecutionException {
		throw new ExecutionException("Cette expression ne peut renvoyer un booléen.");
	}

	public boolean applyBool(boolean a, boolean b) throws ExecutionException {
		throw new ExecutionException("Cette expression ne peut renvoyer un booléen.");
	}

	public abstract void debug();

	public abstract Type getType();
}
