/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package t_c_b;

import java.io.IOException;
import java.net.URL;
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

public class FXMLDocumentController implements Initializable {

    @FXML
    private TextField user;

    @FXML
    private PasswordField pass;

    @FXML
    private Button login1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Optional initialization
    }

    @FXML
    private void loginBtn(ActionEvent event) {
        String username = user.getText();
        String password = pass.getText();

        // Hardcoded validation
        if (username.equals("admin") && password.equals("1234")) {
            try {
                // Load Dashboard.fxml
                Parent root = FXMLLoader.load(getClass().getResource("DashBoard.fxml"));
                Stage stage = new Stage();
                stage.setTitle("Dashboard");
                stage.setScene(new Scene(root));
                stage.show();

                // Close current login window
                Stage currentStage = (Stage) login1.getScene().getWindow();
                currentStage.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Show error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Incorrect username or password!");
            alert.showAndWait();
        }
    }
}
