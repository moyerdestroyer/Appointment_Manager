package Controllers;

import DAO.DBAppointments;
import DAO.DBContacts;
import DAO.DBUser;
import DAO.TimeConversion;
import Model.Appointment;
import Model.Contact;
import Model.User;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
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
import java.time.*;
import java.time.temporal.ChronoField;
import java.util.TimeZone;

/** This is the main appointment view controller, displays all appointments <br>
 * <i>Contains Lambda Descriptions</i>
 */
public class AppointmentViewController {
    User loggedInUser;
    ObservableList<Appointment> allAppointments = DBAppointments.returnAllAppointments();
    ObservableList<Contact> allContacts = DBContacts.returnAllContacts();
    /**
     * The Initialize function is called everytime this screen is loaded. It gets all the data from the DAO, and creates the observable lists to display. The Initialize function utilizes multiple lambdas, mostly for setting up table views.<br>
     * <b>Lambda 1:</b> The first lamba is a simple Lambda expressions that calls TimeConversion.dateToString on the returned values of the appointments. The converted dates are then wrapped in a read-only String which allows it to be added to the Table as a String.<br>
     * <b>Lambda 2:</b> This Lambda sets the predicated for filteredMonthData (FilteredList). The predicate returns true if the Appointment in the list is in the same month and year as the user local time.<br>
     * <b>Lambda 3:</b> This lambda sets up the predicate for the Week Table. It returns true if the appointment is in the same week of the year as on the user system.<br>
     * <b>Lambda 4:</b> This ensures that only one selection can be made from either of the 3 tables
     */
    public void initialize() {
        All_Id_Column.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("id"));
        All_Title_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        All_Description_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        All_Location_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        All_Contact_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactId"));
        All_Type_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        //Lambda 1
        All_Start_Column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(TimeConversion.dateToString(cellData.getValue().getStart())));
        All_End_Column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(TimeConversion.dateToString(cellData.getValue().getEnd())));
        All_Customer_Id_Column.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        All_Appointment_Table.setItems(allAppointments);
        Month currentMonth = LocalDate.now().getMonth();
        int currentYear = LocalDate.now().getYear();
        //Lambda 2
        FilteredList<Appointment> filteredMonthData = new FilteredList<>(allAppointments, p -> true);
        filteredMonthData.setPredicate(Appointment -> {
            if (Appointment.getStart().getMonth() == currentMonth && Appointment.getStart().getYear() == currentYear) {
                return true;
            } else if (Appointment.getStart().getMonth() == null) {
                return false;
            }
            return false;
        });
        Month_Id_Column.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("id"));
        Month_Title_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        Month_Description_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        Month_Location_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        Month_Contact_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactId"));
        Month_Type_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        Month_Start_Column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(TimeConversion.dateToString(cellData.getValue().getStart())));
        Month_End_Column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(TimeConversion.dateToString(cellData.getValue().getEnd())));
        Month_Customer_Id_Column.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        Month_Appointment_Table.setItems(filteredMonthData);

        int weekOfYear = LocalDate.now().get(ChronoField.ALIGNED_WEEK_OF_YEAR);
        //Lambda 3
        FilteredList<Appointment> filteredWeekData = new FilteredList<>(allAppointments, p -> true);
        filteredWeekData.setPredicate(Appointment -> {
            return Appointment.getStart().get(ChronoField.ALIGNED_WEEK_OF_YEAR) == weekOfYear;
        });
        Week_Id_Column.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("id"));
        Week_Title_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        Week_Description_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        Week_Location_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        Week_Contact_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("contactId"));
        Week_Type_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        Week_Start_Column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(TimeConversion.dateToString(cellData.getValue().getStart())));
        Week_End_Column.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(TimeConversion.dateToString(cellData.getValue().getEnd())));
        Week_Customer_Id_Column.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        Week_Appointment_Table.setItems(filteredWeekData);
        //Sets Selection Mode
        All_Appointment_Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Week_Appointment_Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Month_Appointment_Table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        All_Appointment_Table.getSelectionModel().selectFirst();
        //Lambda 4
        All_Appointment_Table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Week_Appointment_Table.getSelectionModel().clearSelection();
                Month_Appointment_Table.getSelectionModel().clearSelection();
            }
        });
        Week_Appointment_Table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                All_Appointment_Table.getSelectionModel().clearSelection();
                Month_Appointment_Table.getSelectionModel().clearSelection();
            }
        });
        Month_Appointment_Table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                Week_Appointment_Table.getSelectionModel().clearSelection();
                All_Appointment_Table.getSelectionModel().clearSelection();
            }
        });
        /* Setup Contact Choicebox - Converts ID to names for easier selection*/
        Contact_ChoiceBox.setItems(allContacts);
        Contact_ChoiceBox.setConverter(new StringConverter<Contact>() {
            @Override
            public String toString(Contact object) {
                return object.getName();
            }

            @Override
            public Contact fromString(String string) {
                return null;
            }
        });
    }

    /**
     * @param username Gets the loggedInUser by passing the username String
     */
    public void passUser(String username) {
        this.loggedInUser = DBUser.returnUserByName(username);
        /*
        Loops through appointments to find if one 15 minutes in the future
         */
        boolean immediateAppointment = false;
        for (int i=0; i<allAppointments.size(); i++) {
            LocalDateTime plusFifteen = LocalDateTime.now().plusMinutes(15);
            if (allAppointments.get(i).getStart().isBefore(plusFifteen) && allAppointments.get(i).getStart().isAfter(LocalDateTime.now())) {
                Alert error = new Alert(Alert.AlertType.WARNING);
                error.setTitle("Alert");
                error.setContentText("Scheduled appointment: " + allAppointments.get(i).getId() + allAppointments.get(i).getStart());
                error.getDialogPane().setMinWidth(500);
                error.getDialogPane().setMinHeight(150);
                error.showAndWait();
                immediateAppointment = true;
            }
            if (immediateAppointment) {
                break;
            }
        }
        if (!immediateAppointment) {
            Alert issue = new Alert(Alert.AlertType.WARNING);
            issue.setTitle("Alert");
            issue.setContentText("No Upcoming Appointments");
            issue.showAndWait();
        }
    }

    @FXML
    private TableView<Appointment> All_Appointment_Table;

    @FXML
    private TableColumn<Appointment, Integer> All_Id_Column;

    @FXML
    private TableColumn<Appointment, String> All_Title_Column;

    @FXML
    private TableColumn<Appointment, String> All_Description_Column;

    @FXML
    private TableColumn<Appointment, String> All_Location_Column;

    @FXML
    private TableColumn<Appointment, String> All_Contact_Column;

    @FXML
    private TableColumn<Appointment, String> All_Type_Column;

    @FXML
    private TableColumn<Appointment, String> All_Start_Column;

    @FXML
    private TableColumn<Appointment, String> All_End_Column;

    @FXML
    private TableColumn<Appointment, Integer> All_Customer_Id_Column;

    @FXML
    private TableView<Appointment> Month_Appointment_Table;

    @FXML
    private TableColumn<Appointment, Integer> Month_Id_Column;

    @FXML
    private TableColumn<Appointment, String> Month_Title_Column;

    @FXML
    private TableColumn<Appointment, String> Month_Description_Column;

    @FXML
    private TableColumn<Appointment, String> Month_Location_Column;

    @FXML
    private TableColumn<Appointment, String> Month_Contact_Column;

    @FXML
    private TableColumn<Appointment, String> Month_Type_Column;

    @FXML
    private TableColumn<Appointment, String> Month_Start_Column;

    @FXML
    private TableColumn<Appointment, String> Month_End_Column;

    @FXML
    private TableColumn<Appointment, Integer> Month_Customer_Id_Column;

    @FXML
    private TableView<Appointment> Week_Appointment_Table;

    @FXML
    private TableColumn<Appointment, Integer> Week_Id_Column;

    @FXML
    private TableColumn<Appointment, String> Week_Title_Column;

    @FXML
    private TableColumn<Appointment, String> Week_Description_Column;

    @FXML
    private TableColumn<Appointment, String> Week_Location_Column;

    @FXML
    private TableColumn<Appointment, String> Week_Contact_Column;

    @FXML
    private TableColumn<Appointment, String> Week_Type_Column;

    @FXML
    private TableColumn<Appointment, String> Week_Start_Column;

    @FXML
    private TableColumn<Appointment, String> Week_End_Column;

    @FXML
    private TableColumn<Appointment, Integer> Week_Customer_Id_Column;

    @FXML
    private Button Select_Button;

    @FXML
    private Button Add_Button;

    @FXML
    private Button Delete_Button;

    @FXML
    private Button Customer_View_Button;

    @FXML
    private Button Generate_Report_Button;

    @FXML
    private Button Quit_Button;

    @FXML
    private TextField Id_Textfield;

    @FXML
    private TextField Title_Textfield;

    @FXML
    private TextField Description_Textfield;

    @FXML
    private TextField Location_Textfield;

    @FXML
    private TextField Type_Textfield;

    @FXML
    private TextField Start_Textfield;

    @FXML
    private TextField End_Textfield;

    @FXML
    private TextField Customer_Id_Textfield;

    @FXML
    private ChoiceBox<Contact> Contact_ChoiceBox;

    @FXML
    private TextField User_Id_Textfield;

    @FXML
    private Button Save_Button;

    /**
     * @param event Clears all fields and sets the ID field to "Auto
     */
    @FXML
    void AddAppointmentAction(ActionEvent event) {
        Id_Textfield.setText("Auto");
        Title_Textfield.clear();
        Description_Textfield.clear();
        Location_Textfield.clear();
        Type_Textfield.clear();
        Start_Textfield.clear();
        End_Textfield.clear();
        User_Id_Textfield.clear();
    }

    /**
     * @param event Gets the selcted appointment from the table, and enters all data into the textfields below
     */
    @FXML
    void SelectAppointmentAction(ActionEvent event) {
        Appointment selectedAppointment = getSelectedAppointment();
        Id_Textfield.setText(String.valueOf(selectedAppointment.getId()));
        Title_Textfield.setText(selectedAppointment.getTitle());
        Description_Textfield.setText(selectedAppointment.getDescription());
        Location_Textfield.setText(selectedAppointment.getLocation());
        Type_Textfield.setText(selectedAppointment.getType());
        Start_Textfield.setText(TimeConversion.dateToString(selectedAppointment.getStart()));
        End_Textfield.setText(TimeConversion.dateToString(selectedAppointment.getEnd()));
        Customer_Id_Textfield.setText(String.valueOf(selectedAppointment.getCustomerId()));
        Contact_ChoiceBox.getSelectionModel().select(selectedAppointment.getContactId()-1);
        User_Id_Textfield.setText(String.valueOf(selectedAppointment.getUserId()));
    }

    /**
     * @param event Loads the Customer view, also passing the loggedin user to the window controller
     * @throws IOException
     */
    @FXML
    void CustomerViewAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Customer_View.fxml"));
        Parent root = (Parent) loader.load();
        Stage primaryStage = (Stage)((Node) event.getSource()).getScene().getWindow();
        primaryStage.setTitle("Customer View");
        primaryStage.setScene(new Scene(root));
        CustomerViewController control = loader.getController();
        control.pass_user(loggedInUser);
        primaryStage.show();
    }

    /**
     * @param event Attempts to delete the selected appointment, then calls refreshTables() to update the view
     */
    @FXML
    void DeleteAppointmentAction(ActionEvent event) {
        Appointment appointmentToDelete = getSelectedAppointment();
        try {
            errorMessage(DBAppointments.deleteAppointment(appointmentToDelete));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        refreshTables();
    }

    /**
     * @param event Loads Report_View.fxml, shown as a pop-up
     * @throws IOException
     */
    @FXML
    void GenerateReportAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/Report_View.fxml"));
        Parent root = (Parent) loader.load();
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Report");
        primaryStage.setScene(new Scene(root));
        primaryStage.showAndWait();

    }

    /**
     * @param event Quits the program
     */
    @FXML
    void QuitButtonAction(ActionEvent event) {
        Platform.exit();
    }

    /**
     * @param event calls makeAppointmentFromFields() as a verification check, then passes the appointment object to either addAppointment or updateAppointment, based on whether the ID shows "Auto"
     */
    @FXML
    void SaveButtonAction(ActionEvent event) {
        Appointment appointmentFields = makeAppointmentFromFields();
        if (Id_Textfield.textProperty().getValue().equals("Auto")) {
            System.out.println(DBAppointments.addAppointment(appointmentFields));
        } else {
            System.out.println(DBAppointments.updateAppointment(appointmentFields));
        }
        refreshTables();
    }

    /**
     * @return Returns the selected appointment in the table
     */
    public Appointment getSelectedAppointment()  {
        Appointment selectedAppointment = null;
        if(All_Appointment_Table.getSelectionModel().getSelectedItems().size() > 0) {
            selectedAppointment = All_Appointment_Table.getSelectionModel().getSelectedItem();
        } else if (Week_Appointment_Table.getSelectionModel().getSelectedItems().size() > 0) {
            selectedAppointment = Week_Appointment_Table.getSelectionModel().getSelectedItem();
        } else if (Month_Appointment_Table.getSelectionModel().getSelectedItems().size() > 0) {
            selectedAppointment = Month_Appointment_Table.getSelectionModel().getSelectedItem();
        }
        return selectedAppointment;
    }

    /**
     * Generates an appointment from the input fields. If there is an error, returns null.
     * Created appointment has an ID of 1 if the text shows "Auto", in order to accommodate the int requirement
     */
    public Appointment makeAppointmentFromFields () {
        Appointment returnAppointment = null;
        int idInt;
        if (Id_Textfield.textProperty().getValue().equals("Auto")) {
            idInt = 1;
        } else {
            idInt = Integer.parseInt(Id_Textfield.getText());
        }
        String titleString = Title_Textfield.getText();
        if (titleString.contains("'") || titleString.isEmpty()) {
            errorMessage("Error Getting Title");
            return returnAppointment;
        }
        String descriptionString = Description_Textfield.getText();
        if (descriptionString.contains("'") || descriptionString.isEmpty()) {
            errorMessage("Error Getting Description");
            return returnAppointment;
        }
        String locationString = Location_Textfield.getText();
        if (locationString.contains("'") || locationString.isEmpty()) {
            errorMessage("Error Getting Location");
            return returnAppointment;
        }
        String typeString = Type_Textfield.getText();
        if (typeString.contains("'") || typeString.isEmpty()) {
            errorMessage("Error Getting Type");
            return returnAppointment;
        }
        LocalDateTime startDate;
        try {
            startDate = TimeConversion.stringToDate(Start_Textfield.getText());
        } catch (Exception e) {
            errorMessage("Start Date Error: put in format - yyyy-MM-dd HH:mm:ss");
            return returnAppointment;
        }
        LocalDateTime endDate;
        try {
            endDate = TimeConversion.stringToDate(End_Textfield.getText());
        } catch (Exception e) {
            errorMessage("End Date Error: put in format - yyyy-MM-dd HH:mm:ss");
            return returnAppointment;
        }
        int customerIdInt;
        try {
            customerIdInt = Integer.parseInt(Customer_Id_Textfield.getText());
        } catch (Exception e) {
            errorMessage("Error Getting Customer ID");
            return returnAppointment;
        }
        int contactIdInt = Contact_ChoiceBox.getSelectionModel().getSelectedItem().getId();
        /* Check Dates against EST office hours, then check for overlapping appointments for customers */
        ZonedDateTime startTimeWithZone = startDate.atZone(TimeZone.getDefault().toZoneId());
        LocalTime startTime = startTimeWithZone.withZoneSameInstant(ZoneId.of("-05:00")).toLocalTime();
        LocalTime openHours = LocalTime.parse("08:00:00");
        LocalTime closeHours = LocalTime.parse("22:00:00");
        if (startTime.compareTo(openHours) < 0 || startTime.compareTo(closeHours) > 0) {
            errorMessage("Outside of Office Hours");
            return returnAppointment;
        }
        for (int i = 0; i < allAppointments.size(); i++) {
            if (allAppointments.get(i).getCustomerId() == customerIdInt){
                //Check if Start and end dates are in range
                if (allAppointments.get(i).getStart().isAfter(startDate) && allAppointments.get(i).getStart().isBefore(endDate)) {
                    errorMessage("Start time interferes with this customer's previously scheduled appointment");
                    return returnAppointment;
                }
                if (allAppointments.get(i).getEnd().isBefore(endDate) && allAppointments.get(i).getStart().isAfter(startDate)) {
                    errorMessage("End time intereferes with this customer's previously scheduled appointment");
                    return returnAppointment;
                }
            }
        }

        returnAppointment = new Appointment(idInt, titleString, descriptionString, locationString, typeString, startDate, endDate, TimeConversion.now(), loggedInUser.getName(), TimeConversion.now(), loggedInUser.getName(), customerIdInt, loggedInUser.getId(), contactIdInt);
        return returnAppointment;
    }

    /**
     * Removes and adds all appointments in order to show the tables again
     */
    public void refreshTables() {
        this.allAppointments.removeAll();
        this.allAppointments = DBAppointments.returnAllAppointments();
        this.initialize();
    }

    /**
     * @param message Basic error message pop-up, input string to display as an error
     */
    public void errorMessage(String message) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("ERROR");
        error.setContentText(message);
        error.getDialogPane().setMinWidth(400);
        error.getDialogPane().setMinHeight(150);
        error.showAndWait();
    }

}
