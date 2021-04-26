package Controllers;

import Model.Appointment;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ReportViewController {

    @FXML
    private TableView<Appointment> Appointment_Month_Table;

    @FXML
    private TableColumn<?, ?> Appointment_Month_Type_Column;

    @FXML
    private TableColumn<?, ?> Appointment_Month_Number_Column;

    @FXML
    private TableView<?> Appointment_User_Table;

    @FXML
    private TableColumn<?, ?> Appointment_User_User_Column;

    @FXML
    private TableColumn<?, ?> Appointment_User_Number_Column;

    @FXML
    private ChoiceBox<?> Contact_Choicebox;

    @FXML
    private TableView<?> Contact_Table;

    @FXML
    private TableColumn<?, ?> Contact_Id_Column;

    @FXML
    private TableColumn<?, ?> Contact_Title_Column;

    @FXML
    private TableColumn<?, ?> Contact_Description_Column;

    @FXML
    private TableColumn<?, ?> Contact_Start_Column;

    @FXML
    private TableColumn<?, ?> Contact_End_Column;

    @FXML
    private TableColumn<?, ?> Contact_Customer_Id_Column;

    @FXML
    private Button Quit_Button;

    @FXML
    void QuitButtonAction(ActionEvent event) {

    }

}
