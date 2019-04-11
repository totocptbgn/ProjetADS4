
import java.util.Map;
import java.io.IOException;
import java.util.List;


public interface Instr {
    public void eval(Map<String,Integer> hm) throws IOException;
    static class Commande implements Instr {
    	private String commande;
        private IntExpr expression;

        public Commande(String commande, IntExpr ie) {
            this.expression=ie;
            this.commande=commande;
        }

        @Override
        public String toString() {
            return commande+"("+ expression + ")";
        }
        public void eval(Map<String,Integer> hm) throws IOException {
        	switch(commande) {
        		case "Avancer":
        			break;
        		case "Tourner":
        			break;
        		case "Ecrire":
        			break;
        		default:
        			throw new IOException("Commande introuvable");
        	}
        }
        
    }
}
