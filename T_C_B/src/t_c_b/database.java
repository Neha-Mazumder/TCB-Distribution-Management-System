/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package t_c_b;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author PC
 */
public class database {
    public static Connection connectDb() {
        try {
            String url = "jdbc:mysql://localhost:3306/tcb"; // Change this!
            String user = "root"; // Change if needed
            String password = ""; // Change if needed

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Connection Error: " + e.getMessage());
            return null;
        }
    }
}