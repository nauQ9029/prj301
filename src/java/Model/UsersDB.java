/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import static Model.DatabaseInfo.DBURL;
import static Model.DatabaseInfo.DRIVERNAME;
import static Model.DatabaseInfo.PASSDB;
import static Model.DatabaseInfo.USERDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author plmin
 */
public class UsersDB implements DatabaseInfo {

    public static Connection getConnect() {
        try {
            Class.forName(DRIVERNAME);
        } catch (ClassNotFoundException e) {
            System.out.println("Error loading driver" + e);
        }
        try {
            Connection con = DriverManager.getConnection(DBURL, USERDB, PASSDB);
            return con;
        } catch (SQLException e) {
            System.out.println("Error: " + e);
        }
        return null;
    }

    public boolean isUserExists(String username, String email) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? OR email = ?";
        try (Connection connection = getConnect(); PreparedStatement checkUserStmt = connection.prepareStatement(query)) {
            checkUserStmt.setString(1, username);
            checkUserStmt.setString(2, email);
            try (ResultSet rs = checkUserStmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void addUser(Users user) throws SQLException {
        String query = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try (Connection connection = getConnect(); PreparedStatement insertUserStmt = connection.prepareStatement(query)) {
            insertUserStmt.setString(1, user.getUsername());
            insertUserStmt.setString(2, user.getPassword());
            insertUserStmt.setString(3, user.getEmail());
            insertUserStmt.executeUpdate();
        }
    }
}
