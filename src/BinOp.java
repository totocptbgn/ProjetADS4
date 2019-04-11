public enum BinOp {
    PLUS {
        public int apply(int x, int y) { return x + y; }
    },
    MINUS {
        public int apply(int x, int y) { return x - y; }
    };
    public abstract int apply(int x, int y);
}
