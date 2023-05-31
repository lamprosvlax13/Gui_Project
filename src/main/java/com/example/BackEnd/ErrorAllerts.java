package com.example.BackEnd;

import javafx.scene.control.Alert;

public class ErrorAllerts {

    private  Alert allert;
    private static ErrorAllerts errorAllert;

    public ErrorAllerts()
    {
        allert = new Alert(Alert.AlertType.ERROR);

    }

    public void messageDialog(String message, Alert.AlertType alertType) {
        allert = new Alert(alertType);
        allert.setTitle("Information dialog");
        allert.setHeaderText("Information Alert");
        allert.setContentText(message);
        allert.show();
    }
    public static ErrorAllerts getInstance(){
        if(errorAllert == null){
            return new ErrorAllerts();
        }
        return  errorAllert;
    }




}
