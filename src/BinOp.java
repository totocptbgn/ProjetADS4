public enum BinOp {
    PLUS {
        public int apply(int x, int y) { return x + y; }
        public void debug() {
        	System.out.print("+");
        }
    },
    MINUS {
        public int apply(int x, int y) { return x - y; }
        public void debug() {
        	System.out.print("-");
        }
    };
    public abstract int apply(int x, int y);
    public abstract void debug();
}
