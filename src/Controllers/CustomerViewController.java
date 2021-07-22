package Controllers;

import DAO.*;
import Model.Country;
import Model.Customer;
import Model.FirstLevel;
import Model.User;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;

/**
 * Customer View class, uses customer, country, and division data. User data is passed in with pass_user
 */
public class CustomerViewController {
    User loggedInUser;
    ObservableList<Customer> allCustomers;
    ObservableList<Country> allCountries;
    ObservableList<FirstLevel> allDivisions;

    /**
     * Updates all lists from the DB, then sets up the table
     */
    public void initialize() {
        allCustomers = DBCustomers.returnAllCustomers();
        allCountries = DBCountries.getAllCountries();
        allDivisions = DBFirstLevelDivisions.getFirstLevelDivisions();

        ID_Column.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("id"));
        Country_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("countryName"));
        Division_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("divisionId"));
        Name_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("name"));
        Address_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
        Postal_Code_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
        Phone_Number_Column.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
        Customer_Table.setItems(allCustomers);

        Country_ChoiceBox.setConverter(new StringConverter<Country>() {
            @Override
            public String toString(Country country) {
                return country.getCountry();
            }

            @Override
            public Country fromString(String s) {
                return null;
            }
        });
        Country_ChoiceBox.setItems(allCountries);
        
        FilteredList<FirstLevel> filteredDivisions = new FilteredList<>(allDivisions, d -> true);
        Division_Choicebox.setItems(filteredDivisions);
        Country_ChoiceBox.getSelectionModel().selectedItemProperty().addListener(obs -> {
            filteredDivisions.setPredicate(FirstLevel -> {
                return FirstLevel.getCountryId() == Country_ChoiceBox.getSelectionModel().getSelectedItem().getId();
            });
        });
        Country_ChoiceBox.getSelectionModel().selectFirst();
        Division_Choicebox.setConverter(new StringConverter<FirstLevel>() {
            @Override
            public String toString(FirstLevel firstLevel) {
                return firstLevel.getDivision();
            }

            @Override
            public FirstLevel fromString(String s) {
                return null;
            }
        });
    }

    public void pass_user(User loggedInUser) {
        this.loggedInUser = loggedInUser;
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
    private Button Select_Customer_Button;

    @FXML
    private Button Appointment_View_Button;

    @FXML
    private Button Quit_Button;

    @FXML
    private TextField Id_Textfield;

    @FXML
    private ChoiceBox<Country> Country_ChoiceBox;

    @FXML
    private ChoiceBox<FirstLevel> Division_Choicebox;

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

    /**
     * Clears the fields and adds "Auto" to the textfield
     */
    @FXML
    void AddCustomerAction(ActionEvent event) {
        Id_Textfield.setText("Auto");
        Name_Textfield.clear();
        Address_Textfield.clear();
        Postal_Code_Textfield.clear();
        Phone_Number_Textfield.clear();
        Country_ChoiceBox.getSelectionModel().selectFirst();
        Division_Choicebox.getSelectionModel().selectFirst();
    }

    /**
     * Gets the selected customer from table, and fills in the info into the textfields
     * Makes a call to the Database in order to get country by id, and fill in the division choicebox
     */
    @FXML
    void SelectCustomerAction(ActionEvent event) {
        Customer selectedCustomer = Customer_Table.getSelectionModel().getSelectedItem();
        Id_Textfield.setText(String.valueOf(selectedCustomer.getId()));
        Name_Textfield.setText(selectedCustomer.getName());
        Address_Textfield.setText(selectedCustomer.getAddress());
        Postal_Code_Textfield.setText(selectedCustomer.getPostalCode());
        Phone_Number_Textfield.setText(selectedCustomer.getPhoneNumber());
        FirstLevel theDivison = DBFirstLevelDivisions.getDivisionByID(selectedCustomer.getDivisionId());
        Country_ChoiceBox.getSelectionModel().clearAndSelect(DBCountries.getCountryById(theDivison.getCountryId()).getId() - 1);
        for (int i = 0; i < Division_Choicebox.getItems().size(); i++) {
            if (Division_Choicebox.getItems().get(i).getId() == theDivison.getId()){
                Division_Choicebox.getSelectionModel().select(i);
            }
        }
    }

    /**
     * @param event Switches the view to Appointment_View.fxml, passes user as well
     * @throws IOException
     */
    @FXML
    void AppointmentViewAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Appointment_View.fxml"));
        Parent root = (Parent) loader.load();
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("Appointment View");
        primaryStage.setScene(new Scene(root));
        AppointmentViewController control = loader.getController();
        control.passUser(loggedInUser.getName());
        primaryStage.show();
    }

    /**
     * @param event Attempts to delete the customer, based on the selected customer
     */
    @FXML
    void DeleteCustomerAction(ActionEvent event) {
        Customer customerToDelete = Customer_Table.getSelectionModel().getSelectedItem();
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("Delete");
        message.setContentText(DBAppointments.deleteAppointmentByCustomer(customerToDelete) +"\n" + DBCustomers.deleteCustomer(customerToDelete));
        message.showAndWait();
        refreshTables();
    }

    /**
     * @param event Quits the program
     */
    @FXML
    void QuitButtonAction(ActionEvent event) {
        Platform.exit();
    }

    /**
     * @param event Calls addCustomer or updateCustomer functions, base on the id in the textfield
     */
    @FXML
    void SaveButtonAction(ActionEvent event) {
        Customer customerToSave = getCustomerFromFields();
        if (Id_Textfield.getText().contains("Auto")) {
            DBCustomers.addCustomer(customerToSave);
            refreshTables();
        } else {
            DBCustomers.updateCustomer(customerToSave);
            refreshTables();
        }
    }


    /**
     * @return Generates Customer from the textfields, returns null if there is a problem.
     */
    public Customer getCustomerFromFields() {
        Customer returnCustomer = null;
        int idInt;
        if (Id_Textfield.getText().equals("Auto")) {
            idInt = 1;
        } else {
            idInt = Integer.parseInt(Id_Textfield.getText());
        }
        String name;
        try {
            name = Name_Textfield.getText();
        } catch (Exception e) {
            return returnCustomer;
        }
        String address;
        try {
            address = Address_Textfield.getText();
        } catch (Exception e) {
            return returnCustomer;
        }
        String postalCode;
        try {
            postalCode = Postal_Code_Textfield.getText();
        } catch (Exception e) {
            return returnCustomer;
        }
        String phoneNumber;
        if (!Phone_Number_Textfield.getText().matches(".*[a-z].*")) {
            try {
                phoneNumber = Phone_Number_Textfield.getText();
            } catch (Exception e) {
                return returnCustomer;
            }
        } else {
            return returnCustomer;
        }
        FirstLevel division;
        try {
            division = Division_Choicebox.getSelectionModel().getSelectedItem();
        } catch (Exception e) {
            return returnCustomer;
        }

        returnCustomer = new Customer(idInt, name, address, postalCode, phoneNumber, TimeConversion.now(), loggedInUser.getName(), TimeConversion.now(), loggedInUser.getName(), division.getId());
        return returnCustomer;
    }


    /**
     * Refreshes all lists and initializes the view
     */
    public void refreshTables() {
        this.allCustomers.removeAll();
        this.allDivisions.removeAll();
        this.allCountries.removeAll();
        this.allCustomers = DBCustomers.returnAllCustomers();
        this.allCountries = DBCountries.getAllCountries();
        this.allDivisions = DBFirstLevelDivisions.getFirstLevelDivisions();
        initialize();
    }
}