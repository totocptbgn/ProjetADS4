import java.util.Map;

public abstract class Expr {
	private Type type;
	abstract void debug(Map<String,Integer> hm);
    abstract int eval(Map<String,Integer> hm);
    abstract void setType();
    public Type getType() {
    	return type;
    }

    class PosInt extends Expr {
    	
    	private final int value;

        public PosInt(int value) {
            this.value = value;
        }
        
        public void setType() {
        	type=Type.INT;
        }

		@Override
		public int eval(Map<String,Integer> hm) {
			return value;
		}

		@Override
		public void debug(Map<String,Integer> hm) {
			System.out.print(value);
		}

		@Override
        public String toString() {
            return "PosInt(" + value + ")";
        }
    }

    class Minus extends Expr {

    	private final Expr arg0;

		public Minus(Expr arg0) {
			this.arg0 = arg0;
		}
		
		public void setType() {
        	type=Type.INT;
        }

		@Override
		public int eval(Map<String,Integer> hm) {
			return -arg0.eval(hm);
		}

		@Override
    	public void debug(Map<String,Integer> hm) {
        	System.out.print("-");
        	arg0.debug(hm);
        }

        @Override
        public String toString() {
            return "Minus(" + arg0 + ")";
        }
    }

    class Ope extends Expr {

    	private final BinOp op;
		private final Expr arg0, arg1;
		
		public void setType() {
        	type=Type.INT;
        }
		
		public Ope(BinOp op, Expr arg0, Expr arg1) {
			this.op = op;
			this.arg0 = arg0;
			this.arg1 = arg1;
		}

		@Override
		public int eval(Map<String,Integer> hm) {
			return op.apply(arg0.eval(hm), arg1.eval(hm));
		}

		@Override
		public void debug(Map<String,Integer> hm) {
    		System.out.print("(");
    		arg0.debug(hm);
        	op.debug();
        	arg1.debug(hm);
        	System.out.print(")");
        	System.out.print("[Value:"+this.eval(hm));
    		System.out.print("]");
        }

        @Override
        public String toString() {
            return "Ope(" + op + ", " + arg0 + ", " + arg1 + ")";
        }
    }

    class Lire extends Expr {
		@Override
		public void setType() {
        	type=Type.INT;
        }
		
    	public int eval(Map<String,Integer> hm) {
            return SmartInterpreter.lire();
        }

		@Override
		public void debug(Map<String,Integer> hm) {
			System.out.print("Lire[Value:"+eval(hm)+"]");
		}

		@Override
		public String toString() {
			return "Lire";
		}
    }

	/*
    class Var implements Expr {
        private final String name;

        public Var(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Var(" + name + ")";
        }

		@Override
		public int eval(Map<String,Integer> hm) {
			hm.putIfAbsent(name, 0);
			return hm.get(name);
		}

		@Override
		public void debug(Map<String, Integer> hm) {}
	}
	*/
}

enum Type {
	BOOL,INT;
}
