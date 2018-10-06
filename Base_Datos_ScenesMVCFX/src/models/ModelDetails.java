package models;

/**
 *
 * @author Azaelmglw
 */

public class ModelDetails {
    
    private final ModelMain model_main; 
    
    public ModelDetails(ModelMain model_main){
        this.model_main = model_main;
    }
    
    public ModelMain getModelMain(){
        return model_main;
    } 
}