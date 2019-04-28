import java.io.IOException;
import java.util.Arrays;

public class SmartInterpreter implements Interpreter {

	private static Grid grid;
	private static String console = "";

	public SmartInterpreter() {
		grid = null;
		console = "";
	}

	public static void avancer(int dist) throws ExecutionException {
		int savePosX = grid.getPosX();
		int savePosY = grid.getPosY();
		for (int i = 0; i < dist; i++) {
			grid.avancer(1);
			if (grid.getCurrentStringValue().equals("#")){
				int posX = grid.getPosX();
				int posY = grid.getPosY();
				grid.setPosX(savePosX);
				grid.setPosY(savePosY);
				throw new ExecutionException("Vous ne pouvez pas aller dans un obstacle. Obstacle = [x:" + posX + ", y:" + posY + "].");
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
		} catch (ExecutionException e){
			//writeConsole("> Exception in thread ExecutionException: " + e.getMessage());
			e.printStackTrace();
		}
		writeConsole("> Fin de l'execution.");
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
			case 2: return "vers la gauche";
			case 3: return "vers le bas";
			default: return "";
		}
	}
}
