package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;
import connection.DButils;

public class SignUpController implements Initializable {
    @FXML
    private Button button_signup;
    @FXML
    private Button button_log_in;
    @FXML
    private TextField tf_username;
    @FXML
    private PasswordField ps_password;
    @FXML
    private PasswordField ps_confirm;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        button_signup.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String toggleName="user";
                String confirm=ps_confirm.getText();
                String pass=ps_password.getText();
                if (!tf_username.getText().trim().isEmpty() && !ps_password.getText().trim().isEmpty() && !ps_confirm.getText().trim().isEmpty() && (pass.equals(confirm))){
                    DButils.signUpUser(event,tf_username.getText(),ps_password.getText(),toggleName);

                }else {
                    System.out.println("please fill in all the teste field ");
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("please fill in all information to sign up");
                    alert.show();
                }
            }
        });
        button_log_in.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DButils.changeScene(event,"/view/sample.fxml","log in",null,null);
            }
        });

    }
}
