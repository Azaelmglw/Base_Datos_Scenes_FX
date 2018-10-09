package controllers;

import models.ModelMain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
        name_tfield.focusedProperty().addListener((observable) -> {
            if(name_tfield.getTextFormatter() == null){
                System.out.println("First Name container doesn't have a TextFormatter currently.");
                name_tfield.setTextFormatter(model_main.getTextFormatter(0));
                telephone_tfield.setTextFormatter(model_main.getTextFormatter(1));
            }
        });
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
    private void NewUser(ActionEvent event){
        name_tfield.setText("");
        email_tfield.setText("");
        telephone_tfield.setText("");
    }
    
    @FXML 
    private void AddUser(ActionEvent event){
        SetValues();
        UnformatInputFields();
        model_main.AddUser();
        GetValues();
    }
    
    @FXML 
    private void ModifyUser(ActionEvent event){
        SetValues();
        UnformatInputFields();
        model_main.ModifyUser();
        GetValues();
    }
    
    @FXML 
    private void DeleteUser(ActionEvent event){
        model_main.DeleteConfirmation();
        if(model_main.getResult().get() == ButtonType.NO ){
            
        }
        else{
            SetValues();
            UnformatInputFields();
            model_main.DeleteUser();
            GetValues();
        }
    }
    
    @FXML 
    private void FirstValue(ActionEvent event){
        UnformatInputFields();
        model_main.MoveToFirst();
        GetValues();
    }
    
    @FXML 
    private void PreviousValue(ActionEvent event){
        UnformatInputFields();
        model_main.MoveToPrevious();
        GetValues();
    }
    
    @FXML 
    private void NextValue(ActionEvent event){
        UnformatInputFields();
        model_main.MoveToNext();
        GetValues();
    }
    
    @FXML 
    private void LastValue(ActionEvent event){
        UnformatInputFields();
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
        UnformatInputFields();
        model_main.ObtainUserData();
        GetValues();
        start_btn.setVisible(false);
    }
    
    private void UnformatInputFields(){
        name_tfield.setTextFormatter(null);
        telephone_tfield.setTextFormatter(null);
    }
    
    private void GetValues(){
        name_tfield.setText(model_main.getName());
        email_tfield.setText(model_main.getEmail());
        telephone_tfield.setText(model_main.getTelephone()); 
    }
    
    private void SetValues(){
        model_main.setName(name_tfield.getText());
        model_main.setEmail(email_tfield.getText());
        model_main.setTelephone(telephone_tfield.getText());
    }
    
    public void SwitchPrimaryStageRoot(Parent root){
        model_main.getPrimaryStage().getScene().setRoot(root);
    }
}