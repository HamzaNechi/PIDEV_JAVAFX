/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import entite.*;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import service.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author manaa
 */
public class CourseOrgController implements Initializable {

    @FXML
    private AnchorPane body;
    @FXML
    private TableView<course> table;

    @FXML
    private TableColumn<course, String> nomCourse;
    @FXML
    private TableColumn<course, Date> Datecourse;
    @FXML
    private TableColumn<course, String> circuitcourse;
    @FXML
    private TableColumn<course, Integer> saisoncourse;
    @FXML
    private TableColumn<course, String> responsable;
    
    
    @FXML
    private TextField recherche;
    
    @FXML
    private Button part;
    
    public static User organisateur=new User();
    


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RefreshTable();
        System.out.println("Course org controller + " + this.organisateur.getUser_id());
    }





    private void RefreshTable() {
        CourseService es = new CourseService();

        //RemplissageTableau
        ObservableList<course> tab = FXCollections.observableArrayList();
        tab.addAll(es.readOrg(this.organisateur.getUser_id()));
        nomCourse.setCellValueFactory(new PropertyValueFactory("course_nom"));
        Datecourse.setCellValueFactory(new PropertyValueFactory("date"));
        circuitcourse.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCircuit_circuit_id().getNom()));
        saisoncourse.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSaison_year().getYear()));
        responsable.setCellValueFactory(celData -> new SimpleObjectProperty<>(celData.getValue().getUsers().getUsername()));
        table.setItems(tab);

    }
    
    
    @FXML
    private void ChercherCourse(KeyEvent event) {
        CourseService cs = new CourseService();
        ObservableList<course> tab = FXCollections.observableArrayList();
        tab.addAll(cs.SearchOrg(recherche.getText(),this.organisateur.getUser_id()));

        nomCourse.setCellValueFactory(new PropertyValueFactory("course_nom"));
        Datecourse.setCellValueFactory(new PropertyValueFactory("date"));
       circuitcourse.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCircuit_circuit_id().getNom()));
        saisoncourse.setCellValueFactory(celData -> new SimpleObjectProperty<>(celData.getValue().getSaison_year().getYear()));
        responsable.setCellValueFactory(celData -> new SimpleObjectProperty<>(celData.getValue().getUsers().getUsername()));
        table.setItems(tab);

    }
    
    
    
    @FXML
    void OpenPart() {
        
         
        ParticipationController pc = new ParticipationController();
        course c = new course();
        c=table.getSelectionModel().getSelectedItem();
        pc.setCourse(c);
         
        
        
        try {
            final FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Participation.fxml"));
            final Node node;
            node = fxmlLoader.load();
            AnchorPane pane=new AnchorPane(node);
            body.getChildren().setAll(pane);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
         
      
        
    }

    void setConnected(User user) {
        this.organisateur.setUser_id(user.getUser_id());
        this.organisateur.setRole(user.getRole());
    }


}
