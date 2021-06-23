package DAO;

import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBUser {
    public static ObservableList<User> returnAllUsers() {
        ObservableList<User> allUsers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                User c = new User(userId, userName, password, createDate, createdBy, lastUpdate, lastUpdatedBy);
                allUsers.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }
    public static boolean login(String username, String password) {
        User selectedUser = null;
        try {
            String sql = "Select * FROM users WHERE User_Name = '" + username + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String pass = rs.getString("Password");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                selectedUser = new User(userId, userName, pass, createDate, createdBy, lastUpdate, lastUpdatedBy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (selectedUser == null) {
            return false;
        } if (selectedUser.getPassword().equals(password)) {
            return true;
        } else {
            return false;
        }
    }
    public static User returnUserByName(String username) {
        User selectedUser = null;
        try {
            String sql = "SELECT * FROM users WHERE User_Name = '" + username + "'";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String pass = rs.getString("Password");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                selectedUser = new User(userId, userName, pass, createDate, createdBy, lastUpdate, lastUpdatedBy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedUser;
    }
    public static User returnUserById(int id) {
        User selectedUser = null;
        try {
            String sql = "Select * FROM users WHERE User_ID = " + id;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("User_ID");
                String userName = rs.getString("User_Name");
                String pass = rs.getString("Password");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                selectedUser = new User(userId, userName, pass, createDate, createdBy, lastUpdate, lastUpdatedBy);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return selectedUser;
    }
}
