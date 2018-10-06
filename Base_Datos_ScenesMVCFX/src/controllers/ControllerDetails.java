package controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import models.ModelDetails;

/**
 *
 * @author Azaelmglw
 */

public class ControllerDetails implements Initializable {
    
    private final ModelDetails model_details;
    private final ControllerMain controller_main;
    
    public ControllerDetails(ArrayList models, ArrayList controllers){
        this.model_details = (ModelDetails)models.get(1);
        this.controller_main = (ControllerMain)controllers.get(0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        model_details.getModelMain().getDetailsBool().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue == true){
                    SetValues();
                }
            }
        });
    }
    
    @FXML
    private Label name_lbl;
    
    @FXML
    private Label email_lbl;
    
    @FXML
    private Label telephone_number_lbl;

    @FXML
    public void CloseUserDetails(ActionEvent event){
        controller_main.SwitchPrimaryStageRoot(model_details.getModelMain().getParent(0));
        CleanScene();
        model_details.getModelMain().getDetailsBool().setValue(false);
    }
    
    public void SetValues(){
        name_lbl.setText("Name: "  + model_details.getModelMain().getName());
        email_lbl.setText("Email: " + model_details.getModelMain().getEmail());
        telephone_number_lbl.setText("Telephone: " + model_details.getModelMain().getTelephone());
    }
    
    public void CleanScene(){
        name_lbl.setText("");
        email_lbl.setText("");
        telephone_number_lbl.setText("");
    }   
}