package connection;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.sql.*;
import models.Users;
import controllers.LoggedInAdminController;

public class DButils {

    Users a=new Users();
    public static void changeScene(ActionEvent event,String fxmlFile,String Title,String username,String Type){

        Parent root=null;
        if(username !=null && Type!=null){
            try {
                FXMLLoader loader=new FXMLLoader(DButils.class.getResource(fxmlFile));
                root=loader.load();
                LoggedInAdminController loggedInAdminController =loader.getController();

            }catch (IOException e){
                e.printStackTrace();
            }

        }else {
            try {
                root=FXMLLoader.load(DButils.class.getResource(fxmlFile));
            }catch (IOException e){
                e.printStackTrace();
            }

        }
        Stage stage=(Stage)((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(Title);
        stage.setScene(new Scene(root));
        stage.show();

    }

    public static void signUpUser(ActionEvent event,String username,String password,String Type){
        Connection connection=null;
        PreparedStatement psInsert=null;
        PreparedStatement psCheckUserExists=null;
        ResultSet resultSet=null;
        try {
            connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/Jfxusers","root","");
            psCheckUserExists=connection.prepareStatement("SELECT * FROM user WHERE username=?");
            psCheckUserExists.setString(1,username);
            resultSet=psCheckUserExists.executeQuery();
            if (resultSet.isBeforeFirst()){
                System.out.println("User already exist");
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("you can not use this username");
                alert.show();
            }else{
                psInsert=connection.prepareStatement("INSERT INTO user (username,password,type) VALUES (?,?,?)");
                psInsert.setString(1,username);
                psInsert.setString(2,password);
                psInsert.setString(3,Type);
                psInsert.executeUpdate();
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Succesfull operation");
                alert.show();

                //changeScene(event,"loggedin.fxml","Welcome",username,Type);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (resultSet != null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (psCheckUserExists != null){
                try {
                    psCheckUserExists.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(psInsert !=null){
                try {
                    psInsert.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if(connection !=null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }
    public static void loginUser(ActionEvent event,String username,String password){
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        try{
            connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/Jfxusers","root","");
            preparedStatement=connection.prepareStatement("select password,type FROM user WHERE username=?");
            preparedStatement.setString(1,username);
            resultSet=preparedStatement.executeQuery();
            if (!resultSet.isBeforeFirst()){
                System.out.println("user not found in data base");
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setContentText("provided credentials are incorect!");
                alert.show();
            }else{
                while (resultSet.next()){
                    String retrievedPassword=resultSet.getString("password");
                    String retrievedType=resultSet.getString("type");
                    if (retrievedPassword.equals(password) && retrievedType.equals("admin")){
                        changeScene(event,"/view/loggedInAdmin.fxml","Welcome "+username,null,null);
                    }else if (retrievedPassword.equals(password) && retrievedType.equals("user")){
                        changeScene(event,"/view/loggedinuser.fxml","welcome "+username,null,null);

                    }else {
                        System.out.println("password did not match");
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("the provided credentials are incorect!");
                        alert.show();
                    }
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            if (resultSet !=null){
                try {
                    resultSet.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (preparedStatement !=null){
                try {
                    preparedStatement.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
            if (connection!=null){
                try {
                    connection.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }

    }


    public static Connection conDB()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Jfxusers","root","");
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("ConnectionUtil : "+ex.getMessage());
            return null;
        }
    }
}





