package Controller;

import bo.BoFactory;
import bo.custom.UserBo;
import com.jfoenix.controls.JFXTextField;
import dto.userDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Objects;


public class loginFormController {

    public TextField txtPassword;
    public TextField txtUserName;
    public AnchorPane mainPane;
    public ImageView imgPassword;
    public JFXTextField txtShowPassword;
    public Button btnLogIn;

    private UserBo userBO;

    public void initialize() throws IOException {
        userBO = (UserBo) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.USER);
    }

    public void btnLogOnAction(ActionEvent actionEvent) throws IOException, SQLException, ClassNotFoundException {
        userDto dto = userBO.search(txtUserName.getText());
        checkup(dto);
    }

    private void checkup(userDto dto) throws SQLException, ClassNotFoundException, IOException {
        if(dto!=null){
               String passcode = txtPassword.getText();

            if(passcode.equals(dto.getPassword())){
                // log in successful
                System.out.printf("successful");
                Stage stage = (Stage) mainPane.getScene().getWindow();
                Parent root = FXMLLoader.load(this.getClass().getClassLoader().getResource("./MainView/dashboardForm.fxml"));
                stage.setScene(new Scene(root));
                stage.show();
            }else  new Alert(Alert.AlertType.ERROR,"Password is incorrect!").show();
        }else new Alert(Alert.AlertType.ERROR,"User not found!").show();
    }

    public void signUpOnAction(ActionEvent actionEvent) throws IOException {
        Stage LoginForm = (Stage) mainPane.getScene().getWindow();
        LoginForm.close();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("../MainView/signUp.fxml")));
        LoginForm.setScene(scene);
        LoginForm.show();
    }


    public void checkCredential(ActionEvent actionEvent) {
        btnLogIn.fire();
    }

    public void pwViewPressed(MouseEvent event) {
        txtShowPassword.setText(txtPassword.getText());
        txtShowPassword.setVisible(true);
        txtPassword.setVisible(false);
    }

    public void pwViewReleased(MouseEvent event) {
        txtShowPassword.setVisible(false);
        txtPassword.requestFocus();
        txtPassword.setVisible(true);
    }
}
