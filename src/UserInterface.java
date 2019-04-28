import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class UserInterface extends Application {

	public static void runUI(){
		UserInterface.launch(UserInterface.class);
	}

	@Override
	public void start(Stage primaryStage) {
		Color bgColor = Color.color(0.045, 0.065, 0.115);
		Color elemColor = Color.color(0.30, 0.30, 0.30);

		// Création de la scène
		Group root = new Group();
		Scene scene = new Scene(root, 1280, 720, bgColor);

		// Création de la grille
		Rectangle grid = new Rectangle(600, 300);
		grid.setFill(elemColor);
		grid.setX(600);
		grid.setY(100);

		// Création de la fentrêtre de code
		Rectangle code = new Rectangle(450, 550);
		code.setFill(elemColor);
		code.setX(75);
		code.setY(100);

		// Remplissage de la grille

		// Fin de la mise en place
		root.getChildren().addAll(grid, code);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
