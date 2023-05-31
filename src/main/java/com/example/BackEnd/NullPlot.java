package com.example.BackEnd;

import com.example.FrontEnd.AppController;
import javafx.scene.control.Alert;

import java.util.ArrayList;

public class NullPlot extends Diagram{//implements Diagram{

    @Override
    public void addChartInWindow() {

    }

    @Override
    public void createChart(ArrayList<Record> recordList, int position) {

    }

    @Override
    public void createDiagram(ArrayList<Record> recordList) {
        ErrorAllerts.getInstance().messageDialog("NOT Selected Type DIagram\n", Alert.AlertType.ERROR);
    }



    @Override
    public void setAppController(AppController appController) {

    }

    @Override
    public void setFieldsNameArray(String[] pointerInArrayTable) {

    }

    @Override
    public void setFieldNameInXaxis(String nameFieldXaxis) {

    }





    @Override
    public void setPositionFieldsXaxis(int positionFieldsXaxis) {

    }



    @Override
    public void setListNumberxAxisValues(ArrayList<Number> listNumberXaxis) {

    }

}
