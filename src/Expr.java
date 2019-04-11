import java.util.Map;

public interface Expr {
    int eval(Map<String,Integer> hm);

    class PosInt implements Expr {
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
