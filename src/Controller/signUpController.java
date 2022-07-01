package Controller;

import bo.BoFactory;
import bo.custom.UserBo;
import dto.userDto;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class signUpController {
    public TextField pwdAgain;
    public TextField pwd;
    public TextField username;
    public AnchorPane mainPane;

    private UserBo userBO;

    public void initialize() throws IOException {
        userBO = (UserBo) BoFactory.getBoFactory().getBO(BoFactory.BOTypes.USER);
    }


    public void signup(ActionEvent actionEvent) throws SQLException, ClassNotFoundException {
        if(username.getText().length()>=3 & pwd.getText().length()>=8) {
            userDto dto = new userDto();
            if (pwd.getText().equalsIgnoreCase(pwdAgain.getText())) {
                dto.setPassword(pwd.getText());
                dto.setUsername(username.getText());
            }
            userBO.save(dto);
            clearUI();
        }else new Alert(Alert.AlertType.WARNING,"Please check the credential length!").show();
    }

    private void clearUI(){
        username.clear();
        pwd.clear();
        pwdAgain.clear();
    }

    public void navigateToBack(MouseEvent event) throws IOException {
        URL resource = this.getClass().getResource("../MainView/loginForm.fxml");
        Parent mainPane = FXMLLoader.load(resource);
        Scene scene = new Scene(mainPane);
        Stage primaryStage = (Stage) (this.mainPane.getScene().getWindow());
        primaryStage.setScene(scene);
        primaryStage.centerOnScreen();
        Platform.runLater(() -> primaryStage.sizeToScene());
    }
}
