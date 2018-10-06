package controllers;

import models.ModelMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 *
 * @author Azaelmglw
 */

public class ControllerMain implements Initializable {
    
    private final ModelMain model_main;
    
    public ControllerMain(ModelMain model_main){
        this.model_main = model_main;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
    }
    
    @FXML 
    private Button start_btn;
    
    @FXML
    private TextField name_tfield;
    
    @FXML
    private TextField email_tfield;
    
    @FXML
    private TextField telephone_tfield;
    
    @FXML 
    private void FirstValue(ActionEvent event){
        model_main.MoveToFirst();
        GetValues();
    }
    
    @FXML 
    private void PreviousValue(ActionEvent event){
        model_main.MoveToPrevious();
        GetValues();
    }
    
    @FXML 
    private void NextValue(ActionEvent event){
        model_main.MoveToNext();
        GetValues();
    }
    
    @FXML 
    private void LastValue(ActionEvent event){
        model_main.MoveToLast();
        GetValues();
    }
    
    @FXML
    private void ShowUserDetails(ActionEvent event){
        model_main.setDetailsBool(true);
        SwitchPrimaryStageRoot(model_main.getParent(1));
        
    }
  
    @FXML
    private void Start(ActionEvent event){
        model_main.ObtainUserData();
        GetValues();
        start_btn.setVisible(false);
    }
    
    @FXML
    private void GetValues(){
        name_tfield.setText(model_main.getName());
        email_tfield.setText(model_main.getEmail());
        telephone_tfield.setText(model_main.getTelephone()); 
    }
    
    public void SwitchPrimaryStageRoot(Parent root){
        model_main.getPrimaryStage().getScene().setRoot(root);
    }
}