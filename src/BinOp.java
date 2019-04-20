import java.io.IOException;

public enum BinOp {
    PLUS {
        @Override
        public int applyInt(int x, int y) { return x + y; }
        public void debug() { System.out.print("+"); }
    },
    MINUS {
        @Override
        public int applyInt(int x, int y) { return x - y; }
        public void debug() { System.out.print("-"); }
    },
    ET {
        @Override
        public boolean applyBool(boolean a, boolean b) { return a && b; }
        public void debug() { System.out.print("Et"); }
    },
    OU {
        @Override
        public boolean applyBool(boolean a, boolean b) { return a || b; }
        public void debug() { System.out.print("Ou"); }
    },
    SUP {
        @Override
        public boolean applyBool(int x, int y) { return x > y; }
        public void debug() { System.out.print(">"); }
    },
    INF {
        @Override
        public boolean applyBool(int x, int y) { return x < y; }
        public void debug() { System.out.print("<"); }
    },
    EQ {
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
}
