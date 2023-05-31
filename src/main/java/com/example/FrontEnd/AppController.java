package com.example.FrontEnd;

import com.example.Utils.Utills;
import com.example.database.SQLConnection;
import com.example.BackEnd.ErrorAllerts;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.Chart;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AppController implements Initializable {


    private static SQLConnection sqlConnection;

    private  TabPane tabPane;

    @FXML
    private BorderPane borderPane;

    @FXML
    public void getNewQueryFromUser(ActionEvent event) throws IOException, SQLException {

        Dialog querryInputDialog = new Dialog<>();
        querryInputDialog.setTitle("Insert new query here.");
        querryInputDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);

        GridPane dialogPane = new GridPane();
        TextArea textAreaQuery = new TextArea();
        dialogPane.add(textAreaQuery,0,0);

        querryInputDialog.getDialogPane().setContent(dialogPane);

        querryInputDialog.setResultConverter(new Callback<ButtonType, String>() {
            @Override
            public String call(ButtonType b) {

                if (b == ButtonType.OK) {
                    return String.valueOf(textAreaQuery.getText() );
                }

                return null;
            }
        });


        Optional<String> result = querryInputDialog.showAndWait();

        Optional<ResultSet> resultSet;
        if (result.isPresent() && (!result.get().equals("")) ) {
            System.out.println(":"+result.get()+":");
            resultSet = this.getInstance().executeQueryStatement(result.get());
            Utills.printQueryResult(resultSet);
        }else{

            ErrorAllerts.getInstance().messageDialog("The given query is empty.", Alert.AlertType.INFORMATION);

        }
    }

    public static SQLConnection getInstance(){
        if(sqlConnection == null){
            sqlConnection=new SQLConnection();
        }
        return sqlConnection;

    }


    @FXML
    public void connectToDatabase(ActionEvent event) {
        this.sqlConnection = new SQLConnection();
    }

    @FXML
    public void createTables(ActionEvent event) {
        if(sqlConnection!=null){
            sqlConnection.createTable();
        } else {
            ErrorAllerts.getInstance().messageDialog("Please initialize connection to mysql server", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void dropDatabase(ActionEvent event) {
        if(sqlConnection!=null){
            sqlConnection.closeConnectionAndDeleteDB();
        }else {
            ErrorAllerts.getInstance().messageDialog("Please initialize connection to mysql server", Alert.AlertType.ERROR);
        }

    }

    @FXML
    public void loadData(ActionEvent event) {
        if(sqlConnection!=null){
            sqlConnection.insertData();
        }else {
            ErrorAllerts.getInstance().messageDialog("Please initialize connection to mysql server", Alert.AlertType.ERROR);
        }

    }


    @FXML
    void createDiagram(ActionEvent event) {

        try {
            Stage newWindow = new Stage();
            newWindow.setTitle("New Scene");
            FXMLLoader loader =new FXMLLoader(getClass().getResource("shmantiko.fxml")); //new FXMLLoader(getClass().getResource("test.fxml"));

            newWindow.setScene(new Scene(loader.load()));
            Listener controller  = loader.getController();
            controller.setParentController(this);
            newWindow.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle ) {
        tabPane = (TabPane) borderPane.getCenter();
    }


    public void addTab(String chartName, Chart chart) {
        tabPane.getTabs().add(new Tab(chartName, chart));
        borderPane.setCenter(tabPane);
    }
}
