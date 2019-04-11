
import java.util.Map;
import java.util.List;

public interface IntExpr {
	public int eval(Map<String,Integer> hm);
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
           if(hm.get(name)==null) {
             hm.put(name,0);
           }
          return hm.get(name);
        }
        public void compile(List<String> vals) {
          if(vals.contains(name)) {
          System.out.print(name);
          }
          else {
            System.out.print("0");
          }
        }
    }

    static class PosInt implements IntExpr {
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
        public void compile(List<String> vals) {
          System.out.print(value);
        }
    }

    static class Minus implements IntExpr {
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
        public void compile(List<String> vals) {
          System.out.print("-");
          arg0.compile(vals);
        }
    }

    static class BinOp implements IntExpr {
        private final arith.BinOp op;
        private final IntExpr arg0, arg1;

        public BinOp(arith.BinOp op, IntExpr arg0, IntExpr arg1) {
            this.op = op;
            this.arg0 = arg0;
            this.arg1 = arg1;
        }

        @Override
        public String toString() {
            return "BinOp(" + op + ", " + arg0 + ", " + arg1 + ")";
        }

        public int eval(Map<String,Integer> hm) {
          return op.apply(arg0.eval(hm),arg1.eval(hm));
        }
        public void compile(List<String> vals) {
          arg0.compile(vals);
          op.compile();
          arg1.compile(vals);
        }

    }
}
