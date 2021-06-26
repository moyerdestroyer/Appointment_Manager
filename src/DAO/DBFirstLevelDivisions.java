package DAO;

import Model.Country;
import Model.FirstLevel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class DBFirstLevelDivisions {
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
    public static ObservableList<FirstLevel> getFirstLevelByCountry(Country targetCountry){
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
}
