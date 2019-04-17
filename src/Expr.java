import java.util.Map;

public interface Expr {

	void debug(Map<String,Integer> hm);
    int eval(Map<String,Integer> hm);

    class PosInt implements Expr {

    	private final int value;

        public PosInt(int value) {
            this.value = value;
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

    class Minus implements Expr {

    	private final Expr arg0;

		public Minus(Expr arg0) {
			this.arg0 = arg0;
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

    class Ope implements Expr {

    	private final BinOp op;
		private final Expr arg0, arg1;

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

    class Lire implements Expr {
		@Override
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
