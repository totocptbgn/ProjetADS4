import java.io.IOException;
import java.util.Map;

public abstract class Expr {
	Type type;

	abstract void debug(ValueEnvironnement hm) throws IOException;
    public int evalInt(ValueEnvironnement hm) throws IOException {
    	throw new IOException("Pas un entier");
    }
    public boolean evalBool(ValueEnvironnement hm) throws IOException {
    	throw new IOException("Pas un booleen");
    }
    /*public boolean eval(Map<String,Integer> hm) {
    	throw new IOException("Pas un entier");
    }
    */
    abstract void setType(ValueEnvironnement hm);
    public Type getType() {
    	return type;
    }
}
class True extends Expr {
    	public void setType(ValueEnvironnement hm) {
        	type = Type.BOOL;
        }

    	@Override
		public boolean evalBool(ValueEnvironnement hm) throws IOException {
			return true;
		}

		@Override
		void debug(ValueEnvironnement hm) throws IOException {
			System.out.println("True");
		}
}
class False extends Expr {
	public void setType(ValueEnvironnement hm) {
		type=Type.BOOL;
	}

	@Override
	public boolean evalBool(ValueEnvironnement hm) throws IOException {
		return false;
	}

	@Override
	public void debug(ValueEnvironnement hm) throws IOException {
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

	public void setType(ValueEnvironnement hm) {
		type = Type.INT;
	}

	@Override
	public int evalInt(ValueEnvironnement hm) {
		return value;
	}

	@Override
	public void debug(ValueEnvironnement hm) throws IOException {
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

		public void setType(ValueEnvironnement hm) {
        	type = op.getType();
        	arg0.setType(hm);
        	arg1.setType(hm);
        }

		public Ope(BinOp op, Expr arg0, Expr arg1) {
			this.op = op;
			this.arg0 = arg0;
			this.arg1 = arg1;
		}

		@Override
		public int evalInt(ValueEnvironnement hm) throws IOException {
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

		public boolean evalBool(ValueEnvironnement hm) throws IOException {
			if (op.getType() == Type.BOOL) {
				if (arg0.getType() == Type.BOOL && arg1.getType() == Type.BOOL)
					return op.applyBool(arg0.evalBool(hm), arg1.evalBool(hm));
				else if (arg0.getType() == Type.INT && arg1.getType() == Type.INT)
					return op.applyBool(arg0.evalInt(hm), arg1.evalInt(hm));
				else {
					throw new IOException("Types non supportés: arg0: " + arg0.getType() + " arg1: " + arg1.getType());
				}
			}
			else {
				throw new IOException("Pas un entier");
			}
		}

		public void debug(ValueEnvironnement hm) throws IOException {
    		System.out.print("(");
    		arg0.debug(hm);
        	op.debug();
        	arg1.debug(hm);
        	System.out.print(")");
        	if(op.getType()==Type.BOOL) {
        		System.out.print("[Value:"+this.evalBool(hm));
        	}
        	else if(op.getType()==Type.INT) {
        		System.out.print("[Value:"+this.evalInt(hm));
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

	public void setType(ValueEnvironnement hm) {
    	type = Type.INT;
    	arg0.setType(hm);
    }

	@Override
	public int evalInt(ValueEnvironnement hm) throws IOException {
		return -arg0.evalInt(hm);
	}

	@Override
	public void debug(ValueEnvironnement hm) throws IOException {
    	System.out.print("-");
    	arg0.debug(hm);
    }

    @Override
    public String toString() {
        return "Minus(" + arg0 + ")";
    }
}
class Var extends Expr {
	
	private String nom;
	@Override
	public void setType(ValueEnvironnement hm) {
		type=hm.exists(nom);
	}

	public int evalInt(ValueEnvironnement hm) throws IOException {
		if(hm.exists(nom)!=Type.INT) throw new IOException(nom+" pas un Entier");
		return hm.getInteger(nom);
	}
	
	public boolean evalBool(ValueEnvironnement hm) throws IOException {
		if(hm.exists(nom)!=Type.BOOL) throw new IOException(nom+" pas un Booleen");
		return hm.getBoolean(nom);
	}

	@Override
	public void debug(ValueEnvironnement hm) throws IOException {
		System.out.print("Var "+nom+"[Value:");
		if(hm.exists(nom)==Type.BOOL)
			System.out.print(hm.getBoolean(nom));
		else if(hm.exists(nom)==Type.INT)
			System.out.print(hm.getInteger(nom));
		else
			System.out.print("None");
	
		System.out.print("]");
	}

	@Override
	public String toString() {
		return "Var("+nom+")";
	}
}
class Lire extends Expr {
	
	@Override
	public int evalInt(ValueEnvironnement hm) throws IOException {
		return SmartInterpreter.lire();
	}
	
	public void setType(ValueEnvironnement hm) {
		type=Type.INT;
	}

	@Override
	public void debug(ValueEnvironnement hm) throws IOException {
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
