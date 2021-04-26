package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomerViewController {

    @FXML
    private Label Title_Label;

    @FXML
    private TableView<?> Customer_Table;

    @FXML
    private TableColumn<?, ?> ID_Column;

    @FXML
    private TableColumn<?, ?> Country_Column;

    @FXML
    private TableColumn<?, ?> Division_Column;

    @FXML
    private TableColumn<?, ?> Name_Column;

    @FXML
    private TableColumn<?, ?> Address_Column;

    @FXML
    private TableColumn<?, ?> Postal_Code_Column;

    @FXML
    private TableColumn<?, ?> Phone_Number_Column;

    @FXML
    private Button Add_Button;

    @FXML
    private Button Delete_Button;

    @FXML
    private Button Appointment_View_Button;

    @FXML
    private Button Quit_Button;

    @FXML
    private TextField Id_Textfield;

    @FXML
    private ChoiceBox<?> Country_ChoiceBox;

    @FXML
    private ChoiceBox<?> Division_Choicebox;

    @FXML
    private TextField Name_Textfield;

    @FXML
    private TextField Address_Textfield;

    @FXML
    private TextField Postal_Code_Textfield;

    @FXML
    private TextField Phone_Number_Textfield;

    @FXML
    private Button Save_Button;

    @FXML
    void AddCustomerAction(ActionEvent event) {

    }

    @FXML
    void AppointmentViewAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Appointment_View.fxml"));
        Parent root = (Parent) loader.load();
        Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("Appointment View");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    @FXML
    void DeleteCustomerAction(ActionEvent event) {

    }

    @FXML
    void QuitButtonAction(ActionEvent event) {

    }

    @FXML
    void SaveButtonAction(ActionEvent event) {

    }

}