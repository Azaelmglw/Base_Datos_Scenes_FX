package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

/**
 *
 * @author Azaelmglw
 */

public class ModelMain {
    /*  Parents array list position:
    [0] -> main    |    [1] -> user_details |
    */
    
    /*Text Formatter array list position
    [0] -> text_formatter   |   [1] -> telephone_formatter
    */
    
    /*  Alerts array list position:
    [0] -> confirmation_alert  |    [1] -> error_alert  |
    */
    
    /*  Application Output array list position:
    [0] -> name   |   [1] -> email  |   [2] -> telephone    |
    */
    
    private final Stage primaryStage;
    private List<Parent> parents = new ArrayList<>(5);
    private List<TextFormatter> text_formatters = new ArrayList<>(5);
    private List<Alert> alerts = new ArrayList<>(5);
    private List<String> app_output = new ArrayList<>(5);
    
    private Optional <ButtonType> result;
    
    private Connection psql_connection;
    private PreparedStatement psql_prepared_statement;
    private ResultSet psql_result_set;
    
    private BooleanProperty details_bool = new SimpleBooleanProperty(false);
    
    public ModelMain(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    
    public void ObtainUserData(){
        try {
            PSQLPrepareStatement("SELECT id_contacto AS ID, nombre AS Name, email AS Email, teléfono AS Telephone FROM contactos ORDER BY id_contacto");
            
            PSQLExecuteQueryPS();
            psql_result_set.first();
            SetValues();
        } 
        catch (SQLException e){
            getAlert(1).setHeaderText("Error 001: An error has ocurred while obtaining the users data. " + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void AddUser(){
        try{
            if(VerifyUserInput()){
                PSQLPrepareStatement("INSERT INTO contactos(nombre, email, teléfono) VALUES(?,?,?);");
                psql_prepared_statement.setString(1, getName());
                psql_prepared_statement.setString(2, getEmail());
                psql_prepared_statement.setString(3, getTelephone());
                PSQLExecuteUpdatePS();
                ObtainUserData();
            }
        }
        catch(SQLException e){
            getAlert(1).setHeaderText("Error 002: An error has ocurred while adding the users data. " + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void ModifyUser(){
        try{
            if(VerifyUserInput()){
                PSQLPrepareStatement("UPDATE contactos SET nombre = ?, email = ?, teléfono = ? WHERE id_contacto = ?;");
                psql_prepared_statement.setString(1, getName());
                psql_prepared_statement.setString(2, getEmail());
                psql_prepared_statement.setString(3, getTelephone());
                psql_prepared_statement.setInt(4, getID());
                PSQLExecuteUpdatePS();
                ObtainUserData();
            }
        }
        catch(SQLException e){
            getAlert(1).setHeaderText("Error 003: An error has ocurred while modifying the users data. " + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void DeleteUser(){
        try{
            if(VerifyUserInput()){
                PSQLPrepareStatement("DELETE FROM contactos WHERE id_contacto = ?;");
                psql_prepared_statement.setInt(1, getID());
                PSQLExecuteUpdatePS();
                ObtainUserData();
            }
        }
        catch(SQLException e){
            getAlert(1).setHeaderText("Error 004: An error has ocurred while deleting the users data. " + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void MoveToFirst(){
        try {
            if (psql_result_set.isFirst()){

            } 
            else{
                psql_result_set.first();
                SetValues();
            }
        }
        catch (SQLException e){
            getAlert(1).setHeaderText("Error 005: A problem has ocurred while moving to the first value." + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void MoveToPrevious(){
        try {
            if (psql_result_set.isFirst()){

            } 
            else{
                psql_result_set.previous();
                SetValues();
            }
        }
        catch (SQLException e){
            getAlert(1).setHeaderText("Error 006: A problem has ocurred while moving to the previous value." + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void MoveToNext(){
        try {
            if (psql_result_set.isLast()){

            } 
            else{
                psql_result_set.next();
                SetValues();
            }
        }
        catch (SQLException e){
            getAlert(1).setHeaderText("Error 007: A problem has ocurred while moving to the next value." + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void MoveToLast(){
        try {
            if (psql_result_set.isLast()){

            } 
            else{
                psql_result_set.last();
                SetValues();
            }
        }
        catch (SQLException e){
            getAlert(1).setHeaderText("Error 008: A problem has ocurred while moving to the last value." + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void SetValues() {
        try{
            app_output.add(0, psql_result_set.getString("ID"));
            app_output.add(1, psql_result_set.getString("Name"));
            app_output.add(2, psql_result_set.getString("Email"));
            app_output.add(3, psql_result_set.getString("Telephone"));
        }
        catch(SQLException e){
            getAlert(1).setHeaderText("Error 009: A problem has ocurred while moving to the last value." + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void DeleteConfirmation(){
        getAlert(0).setTitle("Confirmation Required");
        getAlert(0).setHeaderText("Are you sure you want to delete this user?");
        getAlert(0).setContentText("Choose one of the following options.");
        result = getAlert(0).showAndWait();
    }
    
    public boolean VerifyUserInput(){
        if(getName().isEmpty() || getEmail().isEmpty() || getTelephone().isEmpty()){
            getAlert(1).setHeaderText("No value to be added.");
            getAlert(1).showAndWait();
            return false;
        }
        else if(!getEmail().contains("@")){
            getAlert(1).setHeaderText("Incorrect email.");
            getAlert(1).showAndWait();
            return false;
        }
        else{
            return true;
        }
    }
    
    public void PSQLConnect() {
        try{
            Class.forName("org.postgresql.Driver");
            psql_connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/agenda_mvc", "postgres", "postgres");
        } 
        catch(SQLException e){
            getAlert(1).setHeaderText("Error 010: A problem has ocurred connecting to the database. " + e);
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
            getAlert(1).setHeaderText("Error 011: A problem has ocurred while Preparing the Statement." + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void PSQLExecuteQueryPS(){
        try{
            PSQLConnect();
            psql_result_set = psql_prepared_statement.executeQuery();
            psql_connection.close();
        } 
        catch (SQLException e){
            getAlert(1).setHeaderText("Error 012: A problem has ocurred while Executing the Query. " + e);
            getAlert(1).showAndWait();
        }
    }
    
    public void PSQLExecuteUpdatePS(){
        try{
            psql_prepared_statement.executeUpdate();
            psql_connection.close();
            psql_prepared_statement.close();
        } 
        catch (SQLException e){
            getAlert(1).setHeaderText("Error 013: A problem has ocurred while Executing the Sentence." + e);
            getAlert(1).showAndWait();
        }
    }
    
    public Optional getResult(){
        return result;
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
    
    public TextFormatter getTextFormatter(int text_formatter_position){
        return text_formatters.get(text_formatter_position);
    }

    public void setTextFormatter(int text_formatter_position, TextFormatter text_formatter){
        this.text_formatters.add(text_formatter_position, text_formatter);
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
    
    public int getID(){
        return Integer.parseInt(app_output.get(0));
    }
    
    public String getName(){
        return app_output.get(1);
    }
    
    public void setName(String name){
        app_output.add(1, name);
    }
    
    public String getEmail(){
        return app_output.get(2);
    }
    
    public void setEmail(String email){
        this.app_output.add(2, email);
    }
    
    public String getTelephone(){
        return app_output.get(3);
    }
    
    public void setTelephone(String telephone){
        System.out.println("Telephone length ->" + telephone.length());
        if(telephone.length() != 10){
            if(telephone.length() == 13 && telephone.charAt(3) == '-' && telephone.charAt(7) == '-'&& telephone.charAt(10) == '-'){
                app_output.add(3, telephone);
            }
            else{
                getAlert(1).setHeaderText("Incorrect telephone number.");
                getAlert(1).showAndWait();
            }
        }
        else{
            String telephone_formatting = "";
            for(int x = 0; x < telephone.length(); x ++){
                telephone_formatting += telephone.charAt(x);
                if(x == 2 || x == 5 || x == 7){
                    telephone_formatting += "-";
                }
            }
            app_output.add(3, telephone_formatting);
        }   
    }
   
    public BooleanProperty getDetailsBool(){
        return details_bool;
    }

    public Connection getPsqlConnection() {
        return psql_connection;
    }

    public PreparedStatement getPsqlPreparedStatement() {
        return psql_prepared_statement;
    }

    public ResultSet getPsqlResultSet() {
        return psql_result_set;
    }
    
    public void setDetailsBool(boolean details_bool){
        this.details_bool.setValue(details_bool);
    }  
}