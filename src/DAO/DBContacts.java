package DAO;

import Model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBContacts {
    public static ObservableList<Contact> returnAllContacts() {
        ObservableList<Contact> allContacts = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM Contacts";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID");
                String contactName = rs.getString("Name");
                String email = rs.getString("Email");
                Contact c = new Contact(contactId, contactName, email);
                allContacts.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContacts;
    }
}
