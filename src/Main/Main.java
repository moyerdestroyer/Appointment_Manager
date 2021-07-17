package Main;

import DAO.DBConnection;
import DAO.TimeConversion;
import Model.Zone;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;


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


    public static void main(String[] args) {
        //Testing

        //Testing
        launch(args);
        DBConnection.closeConnection();
    }
}
