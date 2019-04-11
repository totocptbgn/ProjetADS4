import java.util.Map;

public interface IntExpr {
    int eval(Map<String,Integer> hm);

    class PosInt implements IntExpr {
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

    class Minus implements IntExpr {
        private final IntExpr arg0;

        public Minus(IntExpr arg0) {
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

    class Ope implements IntExpr {
        private final BinOp op;
        private final IntExpr arg0, arg1;

        public Ope(BinOp op, IntExpr arg0, IntExpr arg1) {
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

    /* (Pour le Bonus)
    static class Var implements IntExpr {
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
