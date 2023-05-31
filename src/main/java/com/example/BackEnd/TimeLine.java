package com.example.BackEnd;

import com.example.FrontEnd.AppController;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.*;



public class TimeLine extends Diagram{//implements Diagram {

     private  CategoryAxis xAxis;
     private NumberAxis yAxis;
    private LineChart<String,Number> lineChart;



    public TimeLine(){
        this.xAxis = new CategoryAxis();
        this.yAxis =  new NumberAxis();
        this.lineChart = new LineChart<String,Number>(this.xAxis,this.yAxis);

    }


    @Override
    public void createDiagram(ArrayList<Record> recordList) {
        if(recordList != null && recordList.size()!=0) {
            Record recordFirst = recordList.get(0);
            int numberOfFieldsFromQuery = recordFirst.getNumberOfFieldsfromQuery();
            int position = 0;
            while (position < numberOfFieldsFromQuery) {
                createChart(recordList, position);
                position++;
            }
            addChartInWindow();


        }

    }
    @Override
    public void addChartInWindow() {
        super.getAppController().addTab("LineChart", lineChart);
    }

    @Override
    public void createChart(ArrayList<Record> recordList, int position) {

        XYChart.Series<String, Number> series;
        setTitleLineChart("Statistics");
        setLabelXaxis("Year");
        String fieldsInYaxis = "";
        for(String name :super.getFieldsNameArray()){
            fieldsInYaxis+=name+",";
        }
        fieldsInYaxis=fieldsInYaxis.substring(0,fieldsInYaxis.length()-1);

        setLabelYaxis(fieldsInYaxis);
        for(Record record : recordList) {

            series = new XYChart.Series<>();
            //
            series.setName(record.getCountryName()+"_"+super.getNameField(position));
            for (Map.Entry<Integer, ArrayList<String>> entry : record.getMapYearValues().entrySet()) {
                Integer year = entry.getKey();
                ArrayList<String> values = entry.getValue();
                if(!values.get(position).equals("Not Info")){
                        series.getData().addAll(new XYChart.Data(year.toString() , Double.parseDouble(values.get(position)))); //year.toString()

                }

            }
            lineChart.getData().add(series);
        }

    }


    public void setLabelXaxis(String label){

        this.xAxis.setLabel(label);
    }
    public void setLabelYaxis(String label){

        this.yAxis.setLabel(label);
    }

    public void setTitleLineChart(String title){

        this.lineChart.setTitle(title);
    }







}
