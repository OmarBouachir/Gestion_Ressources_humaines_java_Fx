package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.control.Button;
import connection.DButils;


public class LoggedInAdminController implements Initializable {
    @FXML
    private Button button_logout;
    @FXML
    private Button btncrud;

    @FXML
    private Button notification;

    @FXML
    private Button domaine;
    @FXML
    private Button profile;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        button_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DButils.changeScene(event,"/view/sample.fxml","log in",null,null);
            }
        });
        btncrud.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DButils.changeScene(event,"/view/crud.fxml","CRUD",null,null);
            }
        });
        domaine.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DButils.changeScene(event,"/view/domaine.fxml","You In Domaine Mangement",null,null);
            }
        });
        profile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DButils.changeScene(event,"/view/profile.fxml","You Are In Profile Mangement",null,null);
            }
        });
        notification.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                DButils.changeScene(event,"/view/notification.fxml","You Are In Notification Mangement",null,null);
            }
        });

    }
}
