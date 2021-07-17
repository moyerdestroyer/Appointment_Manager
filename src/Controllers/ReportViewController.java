package Controllers;

import DAO.DBAppointments;
import DAO.DBContacts;
import DAO.DBUser;
import Model.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;


public class ReportViewController {
    ObservableList<Appointment> allAppointments;
    ObservableList<User> allUsers;
    ObservableList<Contact> allContacts;

    public void initialize() {
        allAppointments = DBAppointments.returnAllAppointments();
        allUsers = DBUser.returnAllUsers();
        allContacts = DBContacts.returnAllContacts();

        Contact_Choicebox.setItems(allContacts);
        Contact_Choicebox.setConverter(new StringConverter<Contact>() {
            @Override
            public String toString(Contact contact) {
                return contact.getName();
            }

            @Override
            public Contact fromString(String s) {
                return null;
            }
        });

        Contact_Id_Column.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("id"));
        Contact_Title_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        Contact_Description_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        Contact_Start_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("startString"));
        Contact_End_Column.setCellValueFactory(new PropertyValueFactory<Appointment, String>("endString"));
        Contact_Customer_Id_Column.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));

        FilteredList<Appointment> filteredAppointments = new FilteredList<>(allAppointments, a -> true);
        Contact_Table.setItems(filteredAppointments);
        Contact_Choicebox.getSelectionModel().selectedItemProperty().addListener(obs -> {
            filteredAppointments.setPredicate(Appointment -> {
                return Appointment.getContactId() == Contact_Choicebox.getSelectionModel().getSelectedItem().getId();
            });
        });
        Contact_Choicebox.getSelectionModel().selectFirst();

        //Setup Appointment description list
        ObservableList<String> monthData = FXCollections.observableArrayList();
        Month currentMonth = LocalDate.now().getMonth();
        ObservableList<Appointment> monthAppointments = FXCollections.observableArrayList();
        for (Appointment allAppointment : allAppointments) {
            if (currentMonth == allAppointment.getStart().getMonth()) {
                monthAppointments.add(allAppointment);
            }
        }
        for (int i=0; i < monthAppointments.size(); i++) {
            String description = monthAppointments.get(i).getDescription();
            int count = 0;
            for (Appointment monthAppointment : monthAppointments) {
                if (monthAppointment.getDescription().equals(description)) {
                    count++;
                }
            }
            monthData.add(description + " - " + count);
        }
        Month_Appointment_List.setItems(monthData);

        ObservableList<String> userData = FXCollections.observableArrayList();
        for (int i = 0; i < allUsers.size(); i++) {
            int count = 0;
            for(int j = 0; j < allAppointments.size(); j++) {
                if (allUsers.get(i).getId() == allAppointments.get(j).getUserId()) {
                    count ++;
                }
            }
            userData.add(allUsers.get(i).getName() + " - " + count);
        }
        User_Appointment_List.setItems(userData);
    }

    @FXML
    private ListView<String> Month_Appointment_List;

    @FXML
    private ListView<String> User_Appointment_List;

    @FXML
    private ChoiceBox<Contact> Contact_Choicebox;

    @FXML
    private TableView<Appointment> Contact_Table;

    @FXML
    private TableColumn<Appointment, Integer> Contact_Id_Column;

    @FXML
    private TableColumn<Appointment, String> Contact_Title_Column;

    @FXML
    private TableColumn<Appointment, String> Contact_Description_Column;

    @FXML
    private TableColumn<Appointment, String> Contact_Start_Column;

    @FXML
    private TableColumn<Appointment, String> Contact_End_Column;

    @FXML
    private TableColumn<Appointment, Integer> Contact_Customer_Id_Column;

    @FXML
    private Button Quit_Button;

    @FXML
    void QuitButtonAction(ActionEvent event) throws IOException {
        Node  source = (Node)  event.getSource();
        Stage stage  = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
