package DAO;

import Model.Customer;
import Model.FirstLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Performs all Customer DB interactions
 */
public class DBCustomers {
    /**
     * @return Returns all Customers from DB customer table
     */
    public static ObservableList<Customer> returnAllCustomers() {
        ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM customers";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_Id");
                Customer c = new Customer(customerId, customerName, address, postalCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
                allCustomers.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    /**
     * @param targetDivision Returns customers based on the division id
     * @return
     */
    public static ObservableList<Customer> returnCustomerByDivision(FirstLevel targetDivision) {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();
        int targetDivisionId = targetDivision.getId();
        try {
            String sql = "SELECT * FROM customers WHERE Division_ID = " + targetDivisionId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int divisionId = rs.getInt("Division_ID");
                Customer c = new Customer(customerId, customerName, address, postalCode, phoneNumber, createDate, createdBy, lastUpdate, lastUpdatedBy, divisionId);
                customerList.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customerList;
    }

    /**
     * @param customerToSave Adds the Customer to the DB
     * @return
     */
    public static String addCustomer(Customer customerToSave) {
        String concatString;
        String name = "'" + customerToSave.getName() + "', ";
        String address = "'" + customerToSave.getAddress() + "', ";
        String postal = "'" + customerToSave.getPostalCode() + "', ";
        String phone = "'" + customerToSave.getPhoneNumber() + "', ";
        String create = "now(), ";
        String createdBy = "'" + customerToSave.getCreatedBy() + "', ";
        String last = " now(), ";
        String updateBy = "'" + customerToSave.getLastUpdatedBy() + "', ";
        String divisionId = String.valueOf(customerToSave.getDivisionId());
        concatString = name + address + postal + phone + create + createdBy + last + updateBy + divisionId;
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES (" + concatString + ")";
        System.out.println(sql);
        String returnString = "";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            boolean successful = ps.execute();
            returnString = "Customer added: " + successful;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    /**
     * @param customerToSave Updates customer to DB based on Customer ID
     * @return
     */
    public static String updateCustomer(Customer customerToSave) {
        String concatString;
        String name = "Customer_Name = '" + customerToSave.getName() + "', ";
        String address = "Address = '" + customerToSave.getAddress() + "', ";
        String postal = "Postal_Code = '" + customerToSave.getPostalCode() + "', ";
        String phone = "Phone = '" + customerToSave.getPhoneNumber() + "', ";
        String last = "Last_Update = now(), ";
        String updateBy = "Last_Updated_By = '" + customerToSave.getLastUpdatedBy() + "', ";
        String divisionId = "Division_ID = " + customerToSave.getDivisionId();
        concatString = name + address + postal + phone + last + updateBy + divisionId;
        String sql = "UPDATE customers SET " + concatString + " WHERE Customer_ID = " + customerToSave.getId();
        String returnString = "";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            boolean successful = ps.execute();
            returnString = "Customer updated: " + successful;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnString;
    }

    /**
     * @param customerToDelete Deletes Customer from DB based on Customer ID
     * @return
     */
    public static String deleteCustomer(Customer customerToDelete) {
        String sql = "DELETE FROM customers WHERE Customer_ID = " + customerToDelete.getId();
        String returnString = "";
        try {
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            boolean successful = ps.execute();
            returnString = "Issues Deleting Customer: " + successful;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return returnString;
    }
}
