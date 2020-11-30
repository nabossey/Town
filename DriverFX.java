import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A GUI application that sets the FXMainPane as the main pane
 * @author Professor Jeannette Kartchner
 */
public class DriverFX extends Application {  
	/**
	 * The main method for the GUI JavaFX version
	 * @param args not used
	 */
	public static void main(String[] args) {
		launch(args);   
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		//  instantiate the FXMainPane, name it root
		FXMainPane root = new FXMainPane();
		//  set the scene to hold root
		stage.setScene(new Scene(root, 600,800));
		//set stage title
		stage.setTitle("Traveling Student");
		//display the stage
		stage.show();
	
	}

}