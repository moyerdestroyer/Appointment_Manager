package Controllers;

import DAO.*;
import Model.*;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginFormController {
    public void initialize() {
        DBConnection.startConnection();
    }

    public void errorMessage() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setWidth(400);
        Locale defaultLocale = Locale.getDefault();
        ResourceBundle defaultBundle = ResourceBundle.getBundle("Resources.language", defaultLocale);
        error.setTitle(defaultBundle.getString("error"));
        error.setHeaderText(defaultBundle.getString("messagetitle"));
        error.setContentText(defaultBundle.getString("message"));
        error.showAndWait();
    }

    @FXML
    private TitledPane Title_Pane;

    @FXML
    private Button Login_Button;

    @FXML
    private Label Username_Label;

    @FXML
    private TextField Username_Text;

    @FXML
    private Label Password_Label;

    @FXML
    private TextField Password_Text;

    @FXML
    private Label Location_Label;

    @FXML
    public void LoginButtonAction(ActionEvent event) throws IOException {
        String username = Username_Text.getText();
        String password = Password_Text.getText();
        System.out.println(DBUser.login(username, password));
        if (DBUser.login(username, password)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Appointment_View.fxml"));
            Parent root = (Parent) loader.load();
            Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
            primaryStage.setTitle("Appointment View");
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } else {
            errorMessage();
        }
    }

}