package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author Azaelmglw
 */

public class ModelMain {
    /*  Parents array list position:
    [0] -> main    |    [1] -> user_details |
    */
    
    /*  Alerts array list position:
    [0] -> confirmation_alert  |    [1] -> error_alert  |
    */
    
    /*  Application Output array list position:
    [0] -> name   |   [1] -> email  |   [2] -> telephone    |
    */
    
    private final Stage primaryStage;
    private List<Parent> parents = new ArrayList<>(5);
    private List<Alert> alerts = new ArrayList<>(5);
    private List<String> app_output = new ArrayList<>(5);
    
    private Connection psql_connection;
    private PreparedStatement psql_prepared_statement;
    private ResultSet psql_result_set;
    
    private BooleanProperty details_bool = new SimpleBooleanProperty(false);
    
    public ModelMain(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    
    public void ObtainUserData(){
        try {
            PSQLPrepareStatement("SELECT nombre AS Name, email AS Email, teléfono AS Telephone FROM contactos");
            
            PSQLExecuteQueryPS();
            psql_result_set.first();
            SetValues(psql_result_set.getString("Name"), psql_result_set.getString("Email"), psql_result_set.getString("Telephone"));
        } 
        catch (SQLException e){
            getAlert(1).setHeaderText("Error 005: An error has ocurred while obtaining the users data. " + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void MoveToFirst(){
        try {
            if (psql_result_set.isFirst()){

            } 
            else{
                psql_result_set.first();
                SetValues(psql_result_set.getString("Name"), psql_result_set.getString("Email"), psql_result_set.getString("Telephone"));
            }
        }
        catch (SQLException e){
            getAlert(1).setHeaderText("Error 006: A problem has ocurred while moving to the first value." + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void MoveToPrevious(){
        try {
            if (psql_result_set.isFirst()){

            } 
            else{
                psql_result_set.previous();
                SetValues(psql_result_set.getString("Name"), psql_result_set.getString("Email"), psql_result_set.getString("Telephone"));
            }
        }
        catch (SQLException e){
            getAlert(1).setHeaderText("Error 007: A problem has ocurred while moving to the previous value." + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void MoveToNext(){
        try {
            if (psql_result_set.isLast()){

            } 
            else{
                psql_result_set.next();
                SetValues(psql_result_set.getString("Name"), psql_result_set.getString("Email"), psql_result_set.getString("Telephone"));
            }
        }
        catch (SQLException e){
            getAlert(1).setHeaderText("Error 008: A problem has ocurred while moving to the next value." + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void MoveToLast(){
        try {
            if (psql_result_set.isLast()){

            } 
            else{
                psql_result_set.last();
                SetValues(psql_result_set.getString("Name"), psql_result_set.getString("Email"), psql_result_set.getString("Telephone"));
            }
        }
        catch (SQLException e){
            getAlert(1).setHeaderText("Error 009: A problem has ocurred while moving to the last value." + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void SetValues(String name, String email, String telephone) {
        app_output.add(0, name);
        app_output.add(1, email);
        app_output.add(2, telephone);
    }
    
    public void PSQLConnect() {
        try{
            Class.forName("org.postgresql.Driver");
            psql_connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/agenda_mvc", "postgres", "postgres");
        } 
        catch(SQLException e){
            getAlert(1).setHeaderText("Error 000: A problem has ocurred connecting to the database. " + e);
            getAlert(1).showAndWait();
        }
        catch(ClassNotFoundException e){
            getAlert(1).setHeaderText("I don't even know man" + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void PSQLPrepareStatement(String psql_query){
        try{
            PSQLConnect();
            psql_prepared_statement = psql_connection.prepareStatement(psql_query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } 
        catch (SQLException e){
            getAlert(1).setHeaderText("Error 002: A problem has ocurred while Preparing the Statement." + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void PSQLExecuteQueryPS(){
        try{
            PSQLConnect();
            psql_result_set = psql_prepared_statement.executeQuery();
        } 
        catch (SQLException e){
            getAlert(1).setHeaderText("Error 003: A problem has ocurred while Executing the Query. " + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void PSQLExecuteSentencePS(){
        try{
            psql_prepared_statement.execute();
            psql_connection.close();
            psql_prepared_statement.close();
        } 
        catch (SQLException e){
            getAlert(1).setHeaderText("Error 004: A problem has ocurred while Executing the Sentence." + e);
            getAlert(1).showAndWait();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public Parent getParent(int parent_position) {
        return parents.get(parent_position);
    }

    public void setParent(int parent_position, Parent parent) {
        this.parents.add(parent_position, parent);
    }    
    
    public Alert getAlert(int alert_position) {
        return alerts.get(alert_position);
    }

    public void setAlert(int alert_position, Alert alert) {
        this.alerts.add(alert_position, alert);
    }
    
    public List getAppOutput(){
        return app_output;
    }
    
    public String getName(){
        return app_output.get(0);
    }
    
    public void setName(String name){
        app_output.add(0, name);
    }
    
    public String getEmail(){
        return app_output.get(1);
    }
    
    public void setEmail(String email){
        this.app_output.add(1, email);
    }
    
    public String getTelephone(){
        return app_output.get(2);
    }
    
    public void setTelephone(String telephone){
        app_output.add(2, telephone);
    }
    
    
    public BooleanProperty getDetailsBool(){
        return details_bool;
    }
    
    public void setDetailsBool(boolean details_bool){
        this.details_bool.setValue(details_bool);
    }  
}