package DAO;

import Model.Country;
import Model.FirstLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBCountries {
    public static ObservableList<Country> getAllCountries() {
        ObservableList<Country> allCountries = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from countries";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                Country c = new Country(countryId, countryName, createDate, createdBy, lastUpdate, lastUpdatedBy); //FInish this
                allCountries.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  allCountries;
    }
    public static Country getCountryById(int selectedCountryID) {
        Country selectedCountry = null;
        try {
            String sql = "SELECT * from countries WHERE Country_ID =" + selectedCountryID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int countryId = rs.getInt("Country_ID");
                String countryName = rs.getString("Country");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                selectedCountry = new Country(countryId, countryName, createDate, createdBy, lastUpdate, lastUpdatedBy);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return selectedCountry;
    }
}
