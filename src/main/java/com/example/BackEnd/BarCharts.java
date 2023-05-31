package com.example.BackEnd;

import com.example.FrontEnd.AppController;
import javafx.scene.chart.*;

import java.util.*;

public class BarCharts extends Diagram{//implements Diagram{


    private CategoryAxis xAxis;
    private NumberAxis yAxis;
    private BarChart<String,Number> barChart;



    public BarCharts() {

        this.xAxis = new CategoryAxis();
        this.yAxis =  new NumberAxis();
        this.barChart = new BarChart<>(this.xAxis,this.yAxis);

    }



    private void createChartByField(ArrayList<Record> recordList, int i) {

        setTitleLineChart("Statistics");
        String[] fieldsNameArray = super.getFieldsNameArray();
        int positionFieldsXaxis = super.getPositionFieldsXaxis();
        setLabelXaxis(fieldsNameArray[positionFieldsXaxis]);
        int pos=0;
        String fieldsInYaxis = "";
        for(String name :fieldsNameArray){
            if(pos!=positionFieldsXaxis){
                fieldsInYaxis+=name+",";
            }
            pos++;
        }
        fieldsInYaxis=fieldsInYaxis.substring(0,fieldsInYaxis.length()-1);
        setLabelYaxis(fieldsInYaxis);

        //XYChart.Series series=null; //= new XYChart.Series<>();

        XYChart.Series series = new XYChart.Series<>();
        series.setName(getNameField(i));
        for (Number num : super.getListNumberXaxis()) {
            boolean flag = false;
            boolean nullInfo =  false;
            for (Record record : recordList) {
                for (Map.Entry<Integer, ArrayList<String>> entry : record.getMapYearValues().entrySet()) {
                    ArrayList<String> values = entry.getValue();

                    if(!values.get(i).equals("Not Info") && !values.get(super.getPositionFieldsXaxis()).equals("Not Info")){
                        if (num.equals(Double.parseDouble(values.get(super.getPositionFieldsXaxis())))) {
                            series.getData().addAll(new XYChart.Data(num.toString() + " " + record.getCountryName(), Double.parseDouble(values.get(i))));
                            flag = true;
                            //break;
                        }
                    }else{
                        continue;
                    }

                }

                if (flag) {
                   // break;
                }else{
                    continue;
                }


            }

           // if (!flag) {
            //    series.getData().addAll(new XYChart.Data(num.toString(), 0));
           // }
            //barChart.getData().add(series);
        }

        barChart.getData().add(series);


    }


    @Override
    public void createChart(ArrayList<Record> recordList, int position)
    {
         if (super.getFieldNameInXaxis().equals("year")) {
             createChartByYear(recordList, position);
         }else{
            createChartByField(recordList, position);
         }
    }


    private void createChartByYear(ArrayList<Record> recordList, int i){
        XYChart.Series<String, Number> series;

        setTitleLineChart("Statistics");
        String[] fieldsNameArray = super.getFieldsNameArray();
       // int positionFieldsXaxis = super.getPositionFieldsXaxis();
        setLabelXaxis("Year");
       // int position=0;
        String fieldsInYaxis = "";
        for(String name :fieldsNameArray){
            //if(position!=positionFieldsXaxis){
                fieldsInYaxis+=name+",";
           // }
           // position++;
        }
        fieldsInYaxis=fieldsInYaxis.substring(0,fieldsInYaxis.length()-1);
        setLabelYaxis(fieldsInYaxis);


        for(Record record : recordList) {

            series = new XYChart.Series<>();
            //
            series.setName(record.getCountryName()+"_"+super.getNameField(i));
            for (Map.Entry<Integer, ArrayList<String>> entry : record.getMapYearValues().entrySet()) {
                Integer year = entry.getKey();
                ArrayList<String> values = entry.getValue();
                //if(values.get(position) != null){
                if(!values.get(i).equals("Not Info")){
                    //if(values.get(0)!= null){
                    series.getData().addAll(new XYChart.Data(year.toString() , Double.parseDouble(values.get(i)))); //year.toString()
                    //}
                    //series.getData().addAll(new XYChart.Data(values.get(0) , Double.parseDouble(values.get(position)))); //year.toString()
                }

            }
            barChart.getData().add(series);
        }
    }







    //


  @Override
  public void createDiagram(ArrayList<Record> recordList ) {
    if(recordList !=null && recordList.size() !=0) {

            Record firstRecord = recordList.get(0);
            int numberFields = firstRecord.getNumberOfFieldsfromQuery();
            int i = 0;
            while (i < numberFields) {
                if (i != super.getPositionFieldsXaxis() && !super.getFieldNameInXaxis().equals("year") ) {
                    createChart(recordList, i);
                }
                if(super.getFieldNameInXaxis().equals("year")){
                    createChart(recordList, i);
                }
                i++;
            }
            addChartInWindow();
    }

  }
    @Override
    public void addChartInWindow() {
        super.getAppController().addTab("BarChart", barChart);
    }





    public void setLabelXaxis(String label){

        this.xAxis.setLabel(label);
    }
    public void setLabelYaxis(String label){

        this.yAxis.setLabel(label);
    }

    public void setTitleLineChart(String title){

        this.barChart.setTitle(title);
    }


}