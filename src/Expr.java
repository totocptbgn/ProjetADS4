import java.io.IOException;
import java.util.Map;

public abstract class Expr {
	Type type;

	abstract void debug(Map<String,Integer> hm) throws IOException;
    public int evalInt(Map<String,Integer> hm) throws IOException {
    	throw new IOException("Pas un entier");
    }
    public boolean evalBool(Map<String,Integer> hm) throws IOException {
    	throw new IOException("Pas un booleen");
    }
    /*public boolean eval(Map<String,Integer> hm) {
    	throw new IOException("Pas un entier");
    }
    */
    abstract void setType();
    public Type getType() {
    	return type;
    }
}
class True extends Expr {
    	public void setType() {
        	type=Type.BOOL;
        }

    	@Override
		public boolean evalBool(Map<String,Integer> hm) throws IOException {
			return true;
		}

		@Override
		void debug(Map<String, Integer> hm) throws IOException {
			System.out.println("True");
			
		}
}
class False extends Expr {
	public void setType() {
		type=Type.BOOL;
	}

	@Override
	public boolean evalBool(Map<String,Integer> hm) throws IOException {
		return false;
	}

	@Override
	public void debug(Map<String,Integer> hm) throws IOException {
		System.out.print("False");
	}

	@Override
	public String toString() {
		return "False";
	}
}
class PosInt extends Expr {

	private final int value;

	public PosInt(int value) {
		this.value = value;
	}

	public void setType() {
		type = Type.INT;
	}

	@Override
	public int evalInt(Map<String,Integer> hm) {
		return value;
	}

	@Override
	public void debug(Map<String,Integer> hm) throws IOException {
		System.out.print(value);
	}

	@Override
	public String toString() {
		return "PosInt(" + value + ")";
	}
}

class Ope extends Expr {

    	private final BinOp op;
		private final Expr arg0, arg1;

		public void setType() {
        	type = op.getType();
        	arg0.setType();
        	arg1.setType();
        }

		public Ope(BinOp op, Expr arg0, Expr arg1) {
			this.op = op;
			this.arg0 = arg0;
			this.arg1 = arg1;
		}

		@Override
		public int evalInt(Map<String,Integer> hm) throws IOException {
			if (op.getType() == Type.INT) {
				if (arg0.getType() == Type.INT && arg1.getType() == Type.INT)
					return op.applyInt(arg0.evalInt(hm), arg1.evalInt(hm));
				else {
					throw new IOException("Arguments pas entier");
				}
			}
			else {
				throw new IOException("Pas un entier");
			}

		}

		public boolean evalBool(Map<String,Integer> hm) throws IOException {
			if(op.getType()==Type.BOOL) {
				if(arg0.getType()==Type.BOOL && arg1.getType()==Type.BOOL)
					return op.applyBool(arg0.evalBool(hm), arg1.evalBool(hm));
				else if(arg0.getType()==Type.INT && arg1.getType()==Type.INT)
					return op.applyBool(arg0.evalInt(hm), arg1.evalInt(hm));
				else {
					throw new IOException("Types non supportés");
				}
			}
			else {
				throw new IOException("Pas un entier");
			}
		}

		public void debug(Map<String,Integer> hm) throws IOException {
    		System.out.print("(");
    		arg0.debug(hm);
        	op.debug();
        	arg1.debug(hm);
        	System.out.print(")");
        	if(op.getType()==Type.BOOL) {
        		System.out.print("[Value:"+this.evalInt(hm));
        	}
        	else if(op.getType()==Type.INT) {
        		System.out.print("[Value:"+this.evalBool(hm));
        	}
    		System.out.print("]");
        }

	@Override
	public String toString() {
		return "Ope(" + op + "," + arg0 + ","  + arg1 + ")";
	}
}

class Minus extends Expr {

	private final Expr arg0;

	public Minus(Expr arg0) {
		this.arg0 = arg0;
	}
	
	public void setType() {
    	type=Type.INT;
    	arg0.setType();
    }

	@Override
	public int evalInt(Map<String,Integer> hm) throws IOException {
		return -arg0.evalInt(hm);
	}

	@Override
	public void debug(Map<String,Integer> hm) throws IOException {
    	System.out.print("-");
    	arg0.debug(hm);
    }

    @Override
    public String toString() {
        return "Minus(" + arg0 + ")";
    }
}

class Lire extends Expr {
	@Override
	public void setType() {
		type=Type.INT;
	}

	public int evalInt(Map<String,Integer> hm) throws IOException {
		return SmartInterpreter.lire();
	}

	@Override
	public void debug(Map<String,Integer> hm) throws IOException {
		System.out.print("Lire[Value:"+evalInt(hm)+"]");
	}

	@Override
	public String toString() {
		return "Lire";
	}
	
}
   
enum Type {
	BOOL, INT
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
    

