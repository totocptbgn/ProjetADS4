import java.io.IOException;

public enum BinOp {
    PLUS {
        @Override
        public int applyInt(int x, int y) { return x + y; }
        public void debug() { System.out.print("+"); }
        public Type getType() {
    		return Type.INT;
    	}
    },
    MINUS {
        @Override
        public int applyInt(int x, int y) { return x - y; }
        public void debug() { System.out.print("-"); }
        public Type getType() {
    		return Type.INT;
    	}
    },
	TIMES {
		@Override
		public int applyInt(int x, int y) { return x * y; }
		public void debug() { System.out.print("*"); }
		public Type getType() {
			return Type.INT;
		}
	},
	DIVIDE {
		@Override
		public int applyInt(int x, int y) { return x / y; }
		public void debug() { System.out.print("/"); }
		public Type getType() {
			return Type.INT;
		}
	},
	AND {
        @Override
        public boolean applyBool(boolean a, boolean b) { return a && b; }
        public void debug() { System.out.print("Et"); }
        public Type getType() {
    		return Type.BOOL;
    	}
    },
    OR {
        @Override
        public boolean applyBool(boolean a, boolean b) { return a || b; }
        public void debug() { System.out.print("Ou"); }
        public Type getType() {
    		return Type.BOOL;
    	}
    },
    SUP {
        @Override
        public boolean applyBool(int x, int y) { return x > y; }
        public void debug() { System.out.print(">"); }
        public Type getType() {
    		return Type.BOOL;
    	}
    },
    INF {
        @Override
        public boolean applyBool(int x, int y) { return x < y; }
        public void debug() { System.out.print("<"); }
        public Type getType() {
    		return Type.BOOL;
    	}
    },
    EQ {
    	public Type getType() {
    		return Type.BOOL;
    	}
        @Override
        public boolean applyBool(int x, int y) { return x == y; }
        @Override
        public boolean applyBool(boolean a, boolean b) { return a == b; }
        public void debug() { System.out.print("="); }
       
    };
	
    public int applyInt(int x, int y) throws IOException {
        throw new IOException("Cette expression ne peut renvoyer un int.");
    }

    public boolean applyBool(int x, int y) throws IOException {
        throw new IOException("Cette expression ne peut renvoyer un booléen.");
    }

    public boolean applyBool(boolean a, boolean b) throws IOException {
        throw new IOException("Cette expression ne peut renvoyer un booléen.");
    }

    public abstract void debug();
    
    
	public abstract Type getType();
}
