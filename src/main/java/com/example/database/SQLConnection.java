package com.example.database;

import com.example.Utils.Utills;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;

public class SQLConnection {
    private String dbUrl;
    private String User ;
    private String Password ;

    private final String schemaName = "final_18927391238998";

    private Connection connection;
    private Statement statement;
    public SQLConnection()  {
        Properties properties = null;
        try {
            properties = Utills.readPropertiesFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ///Load from file sqlConnection.properties in resources folder
        this.dbUrl = properties.getProperty("dbUrl");
        this.User = properties.getProperty("User");
        this.Password = properties.getProperty("Password");
        establishConnection();

    }

    public void  initializeDatabase(){
        executeStatement("CREATE DATABASE "+schemaName);
    }

    private void loadData() {
        ArrayList<String> statments =
                readSqlStatments("src/main/resources/loadData.sql");
        for (String statment:statments) {
            executeStatement(statment);
        }

    }

    public void establishConnection() {

        try {
            this.connection = DriverManager.getConnection( this.dbUrl, this.User , this.Password);
            this.statement = connection.createStatement();

            if (checkDatabaseExists(schemaName) == false){
                initializeDatabase();

            }else {
                System.out.println("Database" + schemaName + "already exists.");
            }
            connection.close();
            this.connection = DriverManager.getConnection( this.dbUrl+schemaName+"?allowLoadLocalInfile=true", this.User , this.Password);
            this.statement = connection.createStatement();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void insertData() {
        ArrayList<String> statements = readSqlStatments("src/main/resources/loadData.sql");
        statements.stream().forEach(System.out::println);
        for (String statement:statements) {
            executeStatement(statement);
        }
    }

    public boolean executeStatement(String statementToExecute){
        boolean state = false;
        try {
            state = statement.execute(statementToExecute);
            System.out.println("Successful execution of: " + statementToExecute);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return state;

    }

    public Optional<ResultSet> executeQueryStatement(String statementToExecute){
        Optional<ResultSet> resultSet;
        try {
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = Optional.ofNullable(stmt.executeQuery(statementToExecute));

            System.out.println("Successful execution of: " + statementToExecute);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return resultSet;

    }

    public boolean checkDatabaseExists(String databaseName){
        try {
            ResultSet resultSet = connection.getMetaData().getCatalogs();

            while (resultSet.next()) {

                String dbName = resultSet.getString(1);
                if(dbName.equals(databaseName)) {
                    System.out.println(dbName + " " + databaseName);
                    return true;
                }
            }
            resultSet.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> readSqlStatments(String sqlFileName){
        //read all sql for every table from sql file
        StringBuilder builder = new StringBuilder();
        ArrayList<String> tables = new ArrayList<>();

        try {
            BufferedReader buffer=new BufferedReader(
                    new FileReader(sqlFileName));
            String fileLine;
            while ((fileLine = buffer.readLine()) != null) {
                if(fileLine.equals("----")){
                    System.out.println(builder.toString());
                    tables.add(builder.toString());
                    builder.setLength(0);
                }else {
                    builder.append(fileLine).append("\n");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return tables;
    }
    public void createTable(){
        ArrayList<String> tables = readSqlStatments("src/main/resources/createTables.sql");

        for (String table:tables) {
            executeStatement(table);
        }

    }

    public void closeConnectionAndDeleteDB(){
        executeStatement("DROP DATABASE "+schemaName);
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
