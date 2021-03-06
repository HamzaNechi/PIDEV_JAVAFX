/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import entite.User;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author nechi
 */
public class DashboardOrgController implements Initializable {

    @FXML
    private BorderPane BorderPane;
    @FXML
    private JFXButton logout;
    @FXML
    private AnchorPane slider;
    @FXML
    private JFXButton cpt;
    @FXML
    private Circle circle;
    @FXML
    private AnchorPane body;
    
    @FXML
    private AnchorPane dashboard;
    
    @FXML
    private JFXButton course;
    
    
    
    private static User user = new User();
    
    UserService us=new UserService();
    ProfilController pc=new ProfilController();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(this.user.getUser_id() > 0){
           try{
                URL path = new URL("http://127.0.0.1:8000/images/user/"+this.user.getImage_name());
                URLConnection connection = path.openConnection();
                InputStream inputStream = connection.getInputStream();
                Image image = new Image(inputStream);
                circle.setFill(new ImagePattern(image));
                cpt.setText(this.user.getUsername());

            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }    

     @FXML
   void onLogOut() throws IOException{ 
       
             try {
                 final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/vitrine/vitrine.fxml"));
                 final Node node= fxmlLoader.load();
                AnchorPane pane=new AnchorPane(node);
                dashboard.getChildren().setAll(pane);
                 us.logOut(this.user);
                  
             } catch (SQLException ex) {
                 Logger.getLogger(UpdateUserController.class.getName()).log(Level.SEVERE, null, ex);
             }

    }
   
   
   
   @FXML
    void OpenCourse(ActionEvent event) {
        CourseOrgController coc=new CourseOrgController();
        coc.setConnected(this.user);
        final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/CourseOrg.fxml"));
        final Node node;
        try {
            node = fxmlLoader.load();
            AnchorPane pane=new AnchorPane(node);
            body.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }



    @FXML
    private void openProfil(ActionEvent event) {
        pc.setConnected(user);
            System.out.println("openProfil"+user.toString());
      
               final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Profil.fxml"));
        final Node node;
        try {
            node = fxmlLoader.load();
            AnchorPane pane=new AnchorPane(node);
            body.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(AcceuilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    

    public void ConnectedAcceuil(User u) {
        this.user.setUser_id(u.getUser_id());
        this.user.setUsername(u.getUsername());
        this.user.setEmail(u.getEmail());
        this.user.setPassword(u.getPassword());
        this.user.setRole(u.getRole());
        this.user.setImage_name(u.getImage_name());
        this.user.setImg(u.getImg());
        this.user.setStatus(u.getStatus());
        this.user.setTel(u.getTel());
    }
    
}
