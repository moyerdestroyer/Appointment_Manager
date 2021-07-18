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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller for Login_Form.fxml
 */
public class LoginFormController {
    /**
     * Initialization function calls startConnection and attempts to create the "activity_log.txt" file
     * Sets Location on bottom text based on system Default
     */
    public void initialize() {
        DBConnection.startConnection();
        FileHandler.createFile();
        Location_Label.setText(String.valueOf(ZoneId.systemDefault()));
    }

    /**
     * Error message for login, displays on invalid login procedure
     */
    public void errorMessage() {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setWidth(600);
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

    /**
     * @param event Checks username, password on user data in the DB. If true, goes to Appointment_View.fxml and passes the user
     *              Also appends to the user activity log
     * @throws IOException
     */
    @FXML
    public void LoginButtonAction(ActionEvent event) throws IOException {
        String username = Username_Text.getText();
        String password = Password_Text.getText();
        boolean loginSuccessful = DBUser.login(username, password);
        FileHandler.appendFile("Login Attempt: " + username + " - " + TimeConversion.dateToString(LocalDateTime.now()) + " " + loginSuccessful);
        if (loginSuccessful) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Appointment_View.fxml"));
            Parent root = (Parent) loader.load();
            Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
            primaryStage.setTitle("Appointment View");
            primaryStage.setScene(new Scene(root));
            AppointmentViewController control = loader.getController();
            control.passUser(username);
            primaryStage.show();
        } else {
            errorMessage();
        }
    }
}