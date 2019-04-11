
import java.util.Map;
import java.util.List;


public interface Instr {
    public void eval(Map<String,Integer> hm);
    static class Assign implements Instr {
        private String var;
        private IntExpr value;

        public Assign(String var, IntExpr value) {
            this.var = var;
            this.value = value;
        }

        @Override
        public String toString() {
            return "Assign(" + var + ", " + value + ")";
        }
        public void eval(Map<String,Integer> hm) {
          hm.put(var,value.eval(hm));
        }
        public void compile(List<String> vals,int ind) {
          for(int i=0;i<ind;i++) {
            System.out.print(" ");
          }
          for(int i=0;i<vals.size();i++) {
            if(vals.get(i).equals(var)) {
              System.out.print(var+"=");
              value.compile(vals);
              System.out.println(";");
              return;
            }
          }
          System.out.print("int "+var+"=");
          vals.add(var);
          value.compile(vals);
          System.out.println(";");
        }
    }

    static class Print implements Instr {
        private IntExpr value;

        public Print(IntExpr value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Print(" + value + ")";
        }

        public void eval(Map<String,Integer> hm) {
          System.out.println(value.eval(hm));
        }
        public void compile(List<String> vals,int ind) {
          for(int i=0;i<ind;i++) {
            System.out.print(" ");
          }
          System.out.print("System.out.println(");
          value.compile(vals);
          System.out.println(");");
        }
    }

    static class If implements Instr {
        private BoolExpr exp;
        private List<Instr> body;

        public If(BoolExpr b, List<Instr> body) {
            this.body = body;
            this.exp=b;
        }

        @Override
        public String toString() {
            return "If(" +exp+ ", " + body + ")";
        }
        public void eval(Map<String,Integer> hm) {
          if(exp.eval(hm)) {
            for(int i=body.size()-1;i>=0;i--) {
              body.get(i).eval(hm);
            }
          }
        }
        public void compile(List<String> vals,int ind) {
          for(int i=0;i<ind;i++) {
            System.out.print(" ");
          }
          System.out.print("if(");
          exp.compile(vals);
          System.out.println("){");
          for(int i=body.size()-1;i>=0;i--) {
            body.get(i).compile(vals,ind+2);
          }
          for(int i=0;i<ind;i++) {
            System.out.print(" ");
          }
          System.out.println("}");
        }
    }

    static class While implements Instr {
    	private BoolExpr expr;
        private List<Instr> body;

        public While(BoolExpr expr, List<Instr> body) {
            this.body = body;
            this.expr=expr;
        }

        @Override
        public String toString() {
            return "While(" + expr + ", " + body + ")";
        }
        public void eval(Map<String,Integer> hm) {
          while(expr.eval(hm)) {
            for(int i=body.size()-1;i>=0;i--) {
              body.get(i).eval(hm);
            }
          }
        }
        public void compile(List<String> vals,int ind) {
          for(int i=0;i<ind;i++) {
            System.out.print(" ");
          }
          System.out.print("while(");
          expr.compile(vals);
          System.out.println("){");
          for(int i=body.size()-1;i>=0;i--) {
            body.get(i).compile(vals,ind+2);
          }
          for(int i=0;i<ind;i++) {
            System.out.print(" ");
          }
          System.out.println("}");
        }
    }
    static class For implements Instr {
        private String var;
        private IntExpr start, end;
        private List<Instr> body;

        public For(String var, IntExpr start, IntExpr end, List<Instr> body) {
            this.var = var;
            this.start = start;
            this.end = end;
            this.body = body;
        }

        public void eval(Map<String,Integer> hm) {
            int deb=start.eval(hm);
            int fin=end.eval(hm);
            for(int j=deb;j<fin;j++) {
                hm.put(var,j);
                for(int i=body.size()-1;i>=0;i--) {
                  body.get(i).eval(hm);
                }
            }

        }

        public void compile(List<String> vals,int ind) {
          for(int i=0;i<ind;i++) {
            System.out.print(" ");
          }
          System.out.print("for(int "+var+"=");
          vals.add(var);
          start.compile(vals);
          System.out.print(";"+var+"<");
          end.compile(vals);
          System.out.println(";"+var+"++) {");
          for(int i=body.size()-1;i>=0;i--) {
            body.get(i).compile(vals,ind+2);
          }
          for(int i=0;i<ind;i++) {
            System.out.print(" ");
          }
          System.out.println("}");
        }

        @Override
        public String toString() {
            return
                "For(" + var + ", " + start + ", " + end + ", "+ body + ")";
        }
    }
}
