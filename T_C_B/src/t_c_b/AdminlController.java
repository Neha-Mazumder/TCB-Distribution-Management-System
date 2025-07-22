/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://.netbeans/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package t_c_b;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Youtech BD
 */
public class AdminlController implements Initializable {

    @FXML
    private TextField tcb_id;
    @FXML
    private PasswordField password;
    @FXML
    private Button admin_login;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: Add any initialization code if needed
    }    

    @FXML
    private void login_button(ActionEvent event) throws IOException {
        String tcbId = tcb_id.getText();
        String pass = password.getText();

        if (tcbId.isEmpty() || pass.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter both TCB ID and Password");
            return;
        }

        Connection conn = database.connectDb();
        if (conn != null) {
            try {
                String query = "SELECT is_admin FROM users WHERE TCB_ID = ? AND Password = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, tcbId);
                stmt.setString(2, pass);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    boolean isAdmin = rs.getBoolean("is_admin");
                    Parent root;
                    String title;

                    if (isAdmin) {
                        root = FXMLLoader.load(getClass().getResource("adminPanel.fxml"));
                        title = "Admin Panel";
                    } else {
                        root = FXMLLoader.load(getClass().getResource("DashBoard.fxml"));
                        title = "User Dashboard";
                    }

                    Stage stage = new Stage();
                    stage.setTitle(title);
                    stage.setScene(new Scene(root));
                    stage.show();

                    // Close login window
                    Stage loginStage = (Stage) admin_login.getScene().getWindow();
                    loginStage.close();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid TCB ID or Password");
                }

                conn.close();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Error connecting to database: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to database");
        }
    }
    
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}