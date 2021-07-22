package Main;

import DAO.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * the Start function immediately opens the Login_Form.fxml, and detects default Locale on user computer, setting the appropriate resource bundle
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login_Form.fxml"));
        Locale defaultLocale = Locale.getDefault();
        loader.setResources(ResourceBundle.getBundle("Resources.language", defaultLocale));
        Parent root = loader.load();
        primaryStage.setTitle("Appointment Program");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    /**
     * @param args main function, closes connection at end of program
     */
    public static void main(String[] args) {
        launch(args);
        DBConnection.closeConnection();
    }
}
