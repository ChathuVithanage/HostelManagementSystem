package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class loginFormController {

    public TextField txtPassword;
    public TextField txtUserName;
    public AnchorPane mainPane;

    public void initialize(){
    }

    public void btnLogOnAction(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().equals("chathu") & txtPassword.getText().equals("1234")) {
            Stage LoginForm = (Stage) mainPane.getScene().getWindow();
            LoginForm.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../MainView/dashboardForm.fxml")));
            LoginForm.setScene(scene);
            LoginForm.show();
        }
        else{
            txtPassword.setText(null);
            txtUserName.setText(null);
            Alert alert = new Alert(Alert.AlertType.ERROR,"Sorry! Something went wrong");
            alert.show();
        }
    }

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        Stage LoginForm = (Stage) mainPane.getScene().getWindow();
        LoginForm.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../MainView/signUp.fxml")));
        LoginForm.setScene(scene);
        LoginForm.show();
    }
}
