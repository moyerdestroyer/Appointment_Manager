package DAO;

import Model.Appointment;
import Model.Customer;
import Model.Zone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBAppointments {
    public static ObservableList<Appointment> returnAllAppointments() {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM appointments";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment a = new Appointment(appointmentId, appointmentTitle, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
                allAppointments.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allAppointments;
    }
    public static ObservableList<Appointment> returnAppointmentByCustomer(Customer targetCustomer) {
        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
        int targetId = targetCustomer.getId();
        try {
            String sql = "SELECT * FROM appointments WHERE " + targetId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String appointmentTitle = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                LocalDateTime start = rs.getTimestamp("Start").toLocalDateTime();
                LocalDateTime end = rs.getTimestamp("End").toLocalDateTime();
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");
                Appointment a = new Appointment(appointmentId, appointmentTitle, description, location, type, start, end, createDate, createdBy, lastUpdate, lastUpdatedBy, customerId, userId, contactId);
                appointmentList.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentList;
    }
    public static String updateAppointment (Appointment appointmentToUpdate) {
        String title = "UPDATE appointments SET Title = '" + appointmentToUpdate.getTitle() + "', ";
        String description = "Description = '" + appointmentToUpdate.getDescription() + "', ";
        String location = "Location = '" + appointmentToUpdate.getLocation() + "', ";
        String type = "Type = '" + appointmentToUpdate.getType() + "', ";
        String start = "Start = '" + TimeConversion.dateToString(appointmentToUpdate.getStart(), Zone.UTC) + "', ";
        String end = "End = '" + TimeConversion.dateToString(appointmentToUpdate.getEnd(), Zone.UTC) + "', ";
        String lastUpdate = "Last_Update = now(), ";
        String lastUpdateBy = "Last_Updated_By = '" + DBUser.returnUserById(appointmentToUpdate.getUserId()).getName() + "', ";
        String customerId = "Customer_ID = " + appointmentToUpdate.getCustomerId() + ", ";
        String userID = "User_ID = " + appointmentToUpdate.getUserId() + ", ";
        String contactID = "Contact_ID = " + appointmentToUpdate.getContactId() + " ";
        String last = "WHERE Appointment_ID = " + appointmentToUpdate.getId();
        String sql = title + description + location + type + start + end + lastUpdate + lastUpdateBy + customerId + userID + contactID + last;
        String returnString = "";
        System.out.println(sql);
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            boolean successful = ps.execute();
            returnString = "Appointments updated: " + successful;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnString;
    }
    public static String addAppointment (Appointment appointmentToAdd) {
        String title = "'" + appointmentToAdd.getTitle() + "', ";
        String description = "'" + appointmentToAdd.getDescription() + "', ";
        String location = "'" + appointmentToAdd.getLocation() + "', ";
        String type = "'" + appointmentToAdd.getType() + "', ";
        String start = "'" + TimeConversion.dateToString(appointmentToAdd.getStart(), Zone.UTC) + "', ";
        String end = "'" + TimeConversion.dateToString(appointmentToAdd.getEnd(), Zone.UTC) + "', ";
        String create = " now(), ";
        String createdby = "'" + DBUser.returnUserById(appointmentToAdd.getUserId()).getName() + "', ";
        String update = " now(), ";
        String updatedby = "'" + DBUser.returnUserById(appointmentToAdd.getUserId()).getName() + "', ";
        String customerId = appointmentToAdd.getCustomerId() + ", ";
        String userId = appointmentToAdd.getUserId() + ", ";
        String contactId = String.valueOf(appointmentToAdd.getContactId());
        String concatString = title + description + location + type + start + end + create + createdby + update + updatedby + customerId + userId + contactId;
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES (" + concatString + ")";
        String returnString = "";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            boolean successful = ps.execute();
            returnString = "Appointment added: " + successful;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnString;
        //A working INSERT
        //INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID)
        //VALUES ('New Title', 'Cool thing', 'Right here', 'No type', '2021-06-04 19:00:00', '2021-06-04 20:00:00', NOW(), 'test', NOW(), 'test', 1, 2, 3);
    }
    public static String deleteAppointment (Appointment appointmentToDelete) {
        String returnString = "";
        String sql = "DELETE FROM appointments WHERE Appointment_ID = " + appointmentToDelete.getId();
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            boolean successful = ps.execute();
            returnString = "Appointment deleted: " + successful;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnString;
    }
}
