package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;

public class LoginFormController {

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
    public void LoginButtonAction(ActionEvent event) {
        System.out.println("Action Button Pressed!");
    }

}