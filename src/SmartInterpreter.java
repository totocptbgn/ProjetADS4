import java.io.IOException;

public class SmartInterpreter implements Interpreter {

	private static Grid grid;
	private static String console = "";

	public static void avancer(int dist) throws NotAllowedMoveException {
		for (int i = 0; i < dist; i++) {
			grid.avancer(1);
			if (grid.getCurrentStringValue().equals("#")){
				throw new NotAllowedMoveException("Vous ne pouvez pas aller dans un obstacle. Obstacle = [x:" + grid.getPosX() + ", y:" + grid.getPosY() + "] (line: " + SmartParser.getLine() + ").");
			}
		}
		writeConsole("> Le Robot avance de " + dist + " case(s) " + getDirectionMessage(grid.getDir()) + ".");
	}

	public static void tourner(int rot) {
		grid.tourner(rot);
		writeConsole("> Le Robot se tourne " + getDirectionMessage(grid.getDir()) + ".");
	}

	public static void ecrire(int val) {
		grid.ecrire(val);
		writeConsole("> Le Robot écrit la valeur " + val + " sur la case de position [x:" + grid.getPosX() + ", y:" + grid.getPosY() + "].");
	}

	public static int lire() {
		return grid.lire();
	}
	
	public static int getWidth() {
		return grid.getSizeX();
	}
	
	public static int getHeight() {
		return grid.getSizeY();
	}

	public void run(Program prog, Grid initGrid) {
		grid = initGrid;
		try {
			prog.eval();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NotAllowedMoveException e){
			writeConsole("> Exception in thread NotAllowedMoveException: " + e.getMessage());
		}
		writeConsole("> Fin de l'execution.");
	}
	
	public void setGrille(Grid initGrid) {
		grid = initGrid;
	}

	private static void writeConsole(String s){
		console += s + "\n";
	}

	public String getConsole() {
		return console + "\n";
	}

	private static String getDirectionMessage(int dir){
		switch (dir){
			case 0: return "vers la droite";
			case 1: return "vers le haut";
			case 2: return "vers le gauche";
			case 3: return "vers le bas";
			default: return "";
		}
	}
}
