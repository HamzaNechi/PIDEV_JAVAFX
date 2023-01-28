/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author manaa
 */
public class QRcodeController implements Initializable {

    @FXML
    private ImageView qrimage;
    public static int x;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String path="http://127.0.0.1:8000/images/QRcode/"+x+".jpg";
        Image img = new Image(path,true);
        qrimage.setImage(img);
// TODO
    }    

    void setQR(int id) {
      this.x=id;
    }
    
}
