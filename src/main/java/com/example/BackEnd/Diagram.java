package com.example.BackEnd;

import com.example.FrontEnd.AppController;

import java.util.ArrayList;

public abstract class Diagram {

    private ArrayList<Number> listNumberXaxis;
    private String []fieldsNameArray;
    private AppController appController;
    private int positionFieldsXaxis;
    private String fieldNameInXaxis;
    
    public Diagram(){

        this.listNumberXaxis = new ArrayList<Number>();
        this.fieldsNameArray = new String[0];
        this.appController = new AppController();
        this.positionFieldsXaxis = 0;
        this.fieldNameInXaxis = "";
    }


    public abstract void addChartInWindow();
    public abstract void createChart(ArrayList<Record> recordList, int position);
    public abstract void createDiagram(ArrayList<Record> recordList);



    public  void setFieldsNameArray(String[] pointerInArrayTable){
        this.fieldsNameArray = pointerInArrayTable;
    }
    public  void setFieldNameInXaxis(String nameFieldXaxis){
        this.fieldNameInXaxis =  nameFieldXaxis;
    }

    public  void setPositionFieldsXaxis(int positionFieldsXaxis){
        this.positionFieldsXaxis = positionFieldsXaxis;
    }
    public int getPositionFieldsXaxis() {
        return positionFieldsXaxis;
    }

    public  void setListNumberxAxisValues(ArrayList<Number> listNumberXaxis){
        this.listNumberXaxis = listNumberXaxis;
    }
    public ArrayList<Number> getListNumberXaxis (){
        return this.listNumberXaxis;
    }

    public String getNameField(int position){

        return this.fieldsNameArray[position];
    }
    public void setAppController(AppController appController) {
        this.appController = appController;
    }
    public String getFieldNameInXaxis() {
        return fieldNameInXaxis;
    }



    public String[] getFieldsNameArray() {
        return fieldsNameArray;
    }

    public AppController getAppController() {
        return appController;
    }


}
