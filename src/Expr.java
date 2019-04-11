import java.util.Map;

public interface Expr {
	void debug(Map<String,Integer> hm);
    int eval(Map<String,Integer> hm);

    class PosInt implements Expr {
        public void debug(Map<String,Integer> hm) {
        	System.out.print(value);
        }
    	private final int value;
        

        public PosInt(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "PosInt(" + value + ")";
        }
        public int eval(Map<String,Integer> hm) {
          return value;
        }
    }

    class Minus implements Expr {
    	public void debug(Map<String,Integer> hm) {
        	System.out.print("-");
        	arg0.debug(hm);
        }
    	private final Expr arg0;

        public Minus(Expr arg0) {
            this.arg0 = arg0;
        }

        @Override
        public String toString() {
            return "Minus(" + arg0 + ")";
        }
        public int eval(Map<String,Integer> hm) {
          return -arg0.eval(hm);
        }
    }

    class Ope implements Expr {
    	public void debug(Map<String,Integer> hm) {
    		System.out.print("(");
    		arg0.debug(hm);
        	op.debug();
        	arg1.debug(hm);
        	System.out.print(")");
        	System.out.print("[Value:"+this.eval(hm));
    		System.out.print("]");
        }
    	private final BinOp op;
        private final Expr arg0, arg1;

        public Ope(BinOp op, Expr arg0, Expr arg1) {
            this.op = op;
            this.arg0 = arg0;
            this.arg1 = arg1;
        }

        @Override
        public String toString() {
            return "Ope(" + op + ", " + arg0 + ", " + arg1 + ")";
        }

        public int eval(Map<String,Integer> hm) {
          return op.apply(arg0.eval(hm), arg1.eval(hm));
        }
    }

    class Lire implements Expr {
    	public void debug(Map<String,Integer> hm) {
    		System.out.print("Lire[Value:"+eval(hm)+"]");
    	}
        @Override
        public String toString() {
            return "Lire";
        }

        public int eval(Map<String,Integer> hm) {
            return SmartInterpreter.lire(); // A FAIRE
        }
    }

    /* (Pour le Bonus)
    static class Var implements Expr {
        private final String name;

        public Var(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Var(" + name + ")";
        }
        public int eval(Map<String,Integer> hm) {
            hm.putIfAbsent(name, 0);
          return hm.get(name);
        }
    }
    */
}
