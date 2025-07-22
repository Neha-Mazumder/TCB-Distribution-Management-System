
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
 * @author PC
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField nid_number;
    @FXML
    private TextField tcb_id;
    @FXML
    private PasswordField password;
    @FXML
    private Button login_button;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO: Add any initialization code if needed
    }    

    @FXML
    private void loginBtn(ActionEvent event) throws IOException {
        String nidNumber = nid_number.getText().trim();
        String tcbId = tcb_id.getText().trim();
        String pass = password.getText().trim();

        // Validate input
        if (tcbId.isEmpty() || pass.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Form Error!", "Please enter TCB ID and Password");
            return;
        }

        Connection conn = database.connectDb();
        if (conn == null) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to connect to database");
            return;
        }

        try {
            String query;
            PreparedStatement stmt;
            boolean isAdminLogin = nidNumber.isEmpty();

            if (isAdminLogin) {
                // Admin login: TCB_ID and Password
                query = "SELECT is_admin FROM users WHERE TCB_ID = ? AND Password = ? AND is_admin = 1";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, tcbId);
                stmt.setString(2, pass);
            } else {
                // User login: NID_Number, TCB_ID, and Password
                query = "SELECT is_admin FROM users WHERE NID_Number = ? AND TCB_ID = ? AND Password = ? AND is_admin = 0";
                stmt = conn.prepareStatement(query);
                stmt.setString(1, nidNumber);
                stmt.setString(2, tcbId);
                stmt.setString(3, pass);
            }

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                boolean isAdmin = rs.getBoolean("is_admin");
                Parent root;
                String title;

                if (isAdmin) {
                    root = FXMLLoader.load(getClass().getResource("/t_c_b/adminPanal.fxml"));
                    title = "Admin Panel";
                } else {
                    root = FXMLLoader.load(getClass().getResource("/t_c_b/DashBoard.fxml"));
                    title = "User Dashboard";
                }

                Stage stage = new Stage();
                stage.setTitle(title);
                stage.setScene(new Scene(root));
                stage.show();

                // Close login window
                Stage loginStage = (Stage) login_button.getScene().getWindow();
                loginStage.close();
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", 
                    isAdminLogin ? "Invalid TCB ID or Password for admin" : "Invalid NID Number, TCB ID, or Password for user");
            }

            conn.close();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error connecting to database: " + e.getMessage());
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