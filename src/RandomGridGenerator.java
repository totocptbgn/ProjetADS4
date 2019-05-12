import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class RandomGridGenerator {

	public static void generateGrid(String filename, int width, int heigth) {
		StringBuilder s = new StringBuilder();
		Random rand = new Random();
		String [] cases = {"-9", "-8", "-7", "-6", "-5", "-4", "-3", "-2", "-1", "0",
				           "1", "2", "3", "4", "5", "6", "7", "8", "9", "*", "#"};

		s.append(" ").append(heigth).append(" ").append(width).append(" 0 0 0\n");
		for (int i = 0; i < heigth; i++) {
			for (int j = 0; j < width; j++) {
				String r;
				if (j == 0 && i == heigth - 1){
					r = cases[rand.nextInt(19)];
				} else {
					r = cases[rand.nextInt(21)];
				}
				if (r.length() == 2){
					s.append(r).append(" ");
				} else {
					s.append(" ").append(r).append(" ");
				}
			}
			s.append("\n");
		}
		File f = new File("src/gridPerso/" + filename);
		try {
			f.createNewFile();
			PrintWriter pw = new PrintWriter(f);
			pw.print(s.toString());
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Fichier `" + filename  + "` généré !\n");
		System.out.println("Aperçu de la grille :");
		System.out.println(s.toString());
	}
}
