package DAO;

import Model.Country;
import Model.FirstLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**
 * Handles DB interactions for the Division Table
 */
public class DBFirstLevelDivisions {
    /**
     * @return Returns all First Level Divisions
     */
    public static ObservableList<FirstLevel> getFirstLevelDivisions() {
        ObservableList<FirstLevel> allFirstLevel = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * from first_level_divisions";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("Country_ID");
                FirstLevel f = new FirstLevel(divisionId, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
                allFirstLevel.add(f);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return allFirstLevel;
    }

    /**
     * @param targetCountry Returns the country by target ID
     * @return
     */
    public static ObservableList<FirstLevel> getFirstLevelByCountryID(Country targetCountry){
        int targetCountryId = targetCountry.getId();
        ObservableList<FirstLevel> firstLevelList = FXCollections.observableArrayList();
        try {
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = " + targetCountryId;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("Country_ID");
                FirstLevel f = new FirstLevel(divisionId, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
                firstLevelList.add(f);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return firstLevelList;
    }

    /**
     * @param divisionID Returns Division based on the ID
     * @return
     */
    public static FirstLevel getDivisionByID (int divisionID) {
        FirstLevel returnDivision = null;
        try {
            String sql = "SELECT * from first_level_divisions WHERE Division_ID = " + divisionID;
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int divisionId = rs.getInt("Division_ID");
                String division = rs.getString("Division");
                LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
                String createdBy = rs.getString("Created_By");
                LocalDateTime lastUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int countryId = rs.getInt("Country_ID");
                returnDivision = new FirstLevel(divisionId, division, createDate, createdBy, lastUpdate, lastUpdatedBy, countryId);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return returnDivision;
    }
}
