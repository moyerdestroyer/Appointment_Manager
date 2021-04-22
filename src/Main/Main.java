package Main;

import Controllers.LoginFormController;
import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Timestamp;
import java.util.Date;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Login_Form.fxml"));
        Parent root = (Parent) loader.load();
        LoginFormController controller = loader.getController();
        primaryStage.setTitle("Inventory Program");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void main(String[] args) {
        Date tempDate = new Date(111111111);
        Customer test = new Customer(1, "test", "123 Jump St.", "12345", "123-456-7890", tempDate, "Me", new Timestamp(System.currentTimeMillis()), "Also Me", 22);
        System.out.println(test);
        System.out.println(test.getAddress());
        System.out.println(test.getLastUpdated());
        System.out.println(test.getDateCreated());
        launch(args);
    }
}
