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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class LoginFormController {
    public void initialize() {
        DBConnection.startConnection();

        //TEST CODE!!!!!!!!!!
        ObservableList<Country> allCountries = DBCountries.getAllCountries();
        for (Country allCountry : allCountries) {
            System.out.println(allCountry.getCountry());
        }
        ObservableList<Appointment> allAppointments = DBAppointments.returnAllAppointments();
        for (Appointment allAppointment : allAppointments) {
            System.out.println(allAppointment.getTitle());
        }
        ObservableList<Contact> allContacts = DBContacts.returnAllContacts();
        for (Contact allContact : allContacts) {
            System.out.println(allContact.getName());
        }
        ObservableList<Customer> allCustomers = DBCustomers.returnAllCustomers();
        for (Customer allCustomer : allCustomers) {
            System.out.println(allCustomer.getName());
        }
        ObservableList<User> allUsers = DBUser.returnAllUsers();
        for (User allUser : allUsers) {
            System.out.println(allUser.getName());
        }
        //////END TEST CODE!!!!!!!
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Appointment_View.fxml"));
        Parent root = (Parent) loader.load();
        Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("Appointment View");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

}