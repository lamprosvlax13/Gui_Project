package com.example.BackEnd;

import com.example.FrontEnd.AppController;
import javafx.scene.chart.*;

import java.util.*;

public class ScatterPlot extends Diagram{//implements Diagram {


    private NumberAxis xAxis;
    private NumberAxis yAxis;
    private ScatterChart<Number,Number> scatterChart;


    public ScatterPlot(){
        this.xAxis = new NumberAxis();
        this.yAxis =  new NumberAxis();
        this.scatterChart = new ScatterChart<Number,Number>(this.xAxis,this.yAxis);

    }



    @Override
    public void createChart(ArrayList<Record> recordList, int position) {

        XYChart.Series<Number, Number> series;

        setTitleLineChart("Statistics");
        String [] fieldsNameArray = super.getFieldsNameArray();
        int positionFieldsXaxis = super.getPositionFieldsXaxis();
        setLabelXaxis(fieldsNameArray[positionFieldsXaxis]);
        int i=0;
        String fieldsInYaxis = "";
        for(String name :fieldsNameArray){
            if(i!=positionFieldsXaxis){
                fieldsInYaxis+=name+",";
            }
            i++;
        }
        fieldsInYaxis=fieldsInYaxis.substring(0,fieldsInYaxis.length()-1);
        setLabelYaxis(fieldsInYaxis);



        for(Record record : recordList) {
            series = new XYChart.Series<>();
            series.setName(record.getCountryName()+"_"+getNameField(position));
                for (Map.Entry<Integer, ArrayList<String>> entry : record.getMapYearValues().entrySet()) {

                    Integer year = entry.getKey();
                    ArrayList<String> values = entry.getValue();
                    if (!values.get(position).equals("Not Info") && !values.get(positionFieldsXaxis).equals("Not Info")) {
                        series.getData().addAll(new XYChart.Data(Double.parseDouble(values.get(positionFieldsXaxis)), Double.parseDouble(values.get(position))));

                    }

                }

            scatterChart.getData().add(series);
        }

    }



    @Override
    public void createDiagram(ArrayList<Record> recordList) {

        if(recordList !=null && recordList.size() != 0) {

            Record recordFirst = recordList.get(0);
            int numberOfFieldsFromQuery = recordFirst.getNumberOfFieldsfromQuery();
            int position = 0;
            while (position < numberOfFieldsFromQuery) {
                if (position != super.getPositionFieldsXaxis()) {
                    createChart(recordList, position);
                    //position++;
                }
                //createChart(recordList, position);
                position++;
            }
            addChartInWindow();

        }

    }
    @Override
    public void addChartInWindow() {
        super.getAppController().addTab("ScatterChart", scatterChart);
    }


    public void setLabelXaxis(String label){

        this.xAxis.setLabel(label);
    }
    public void setLabelYaxis(String label){

        this.yAxis.setLabel(label);
    }

    public void setTitleLineChart(String title){

        this.scatterChart.setTitle(title);
    }
}





