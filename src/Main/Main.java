package Main;

import DAO.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login_Form.fxml"));
        ResourceBundle languageBundle = ResourceBundle.getBundle("Resources.language");
        loader.setResources(languageBundle);
        Parent root = loader.load();
        primaryStage.setTitle("Appointment Program");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        DBConnection.closeConnection();
    }
}
