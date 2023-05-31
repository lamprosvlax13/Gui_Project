package com.example.BackEnd;

import com.example.database.SQLConnection;
import com.example.FrontEnd.AppController;
import com.example.Utils.Utills;
import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class Engine {
    private FactoryDiagram factoryDiagram;
    private CreatorSqlQuery managerSqlQuery;
    private AppController appController;
    private Diagram diagram;
    private SQLConnection sqlConnection;
    private RecordManager recordManager;

    private String typeDiagram;


    private  Optional<ResultSet> resultSqlQuery ;


    public Engine (FactoryDiagram factory, CreatorSqlQuery managerSqlQuery ,AppController appController, String typeDiagram){
        this.factoryDiagram = factory;
        this.managerSqlQuery = managerSqlQuery;
        this.typeDiagram = typeDiagram;
        this.recordManager =  new RecordManager(typeDiagram);
        this.appController =  appController;
        setSqlConnection();
    }


    public void createSqlQuery(){
        managerSqlQuery.createSqlQueryByTable();
        managerSqlQuery.createTotalSqlQuery();
    }

    public int  callExecuteSqlQuery()  {
        String sqlQuery = managerSqlQuery.getFinalQuiry();
        System.out.print(sqlQuery);
        resultSqlQuery = this.sqlConnection .executeQueryStatement(sqlQuery);
        if(resultSqlQuery == null ){
            return -1;
        }
        printResultSqlQuery();
        return 1;

    }

    public void manageResultSqlQuery(){



                String [] countries = managerSqlQuery.getCountriesText();
                String [] fields = managerSqlQuery.getPointerInArrayTable();
                recordManager.setCountriesArray(countries);
                recordManager.setFields(fields);
                recordManager.setFieldNameInXaxis(managerSqlQuery.getNameFieldXaxis());
                this.recordManager.setResultSqlQuery(resultSqlQuery);
                try {
                    int status = recordManager.createRecord();
                    if(status ==1){
                        createDiagram();
                    }else{
                        ErrorAllerts.getInstance().messageDialog("Not data from query ", Alert.AlertType.INFORMATION);
                    }

                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }




    }


    public void printResultSqlQuery(){
        try {
            Utills.printQueryResult(resultSqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createDiagram() throws SQLException {

            diagram =  factoryDiagram.createDiagram();
            diagram.setAppController(appController);
            diagram.setFieldsNameArray(managerSqlQuery.getPointerInArrayTable());
            diagram.setFieldNameInXaxis(managerSqlQuery.getNameFieldXaxis());
            diagram.setPositionFieldsXaxis(this.recordManager.getPositionFieldsXaxis());
            diagram.setListNumberxAxisValues(this.recordManager.getListNumberXaxis());
            ArrayList<Record> recordList =  this.recordManager.getListRecord();
            diagram.createDiagram(recordList);

    }

    public void setSqlConnection() {
        this.sqlConnection = AppController.getInstance();
    }
}
