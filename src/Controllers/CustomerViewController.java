package Controllers;

import Model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class CustomerViewController {
    ObservableList<Customer> allCustomers;
    public void initialize() {
        allCustomers = FXCollections.observableArrayList(new Customer(1, "Dude", "123 Street", "12345", "123-456-7890", LocalDateTime.now(), "You!", LocalDateTime.now(), "Somebody Else", 43));

        ID_Column.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        Country_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("countryName"));
        Division_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("divisionId"));
        Name_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        Address_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        Postal_Code_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        Phone_Number_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        Customer_Table.setItems(allCustomers);
    }

    @FXML
    private Label Title_Label;

    @FXML
    private TableView<Customer> Customer_Table;

    @FXML
    private TableColumn<Customer, Integer> ID_Column;

    @FXML
    private TableColumn<Customer, String> Country_Column;

    @FXML
    private TableColumn<Customer, String> Division_Column;

    @FXML
    private TableColumn<Customer, String> Name_Column;

    @FXML
    private TableColumn<Customer, String> Address_Column;

    @FXML
    private TableColumn<Customer, String> Postal_Code_Column;

    @FXML
    private TableColumn<Customer, String> Phone_Number_Column;

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
        Platform.exit();
    }

    @FXML
    void SaveButtonAction(ActionEvent event) {

    }

}