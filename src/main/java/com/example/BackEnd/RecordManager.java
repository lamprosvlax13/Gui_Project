package com.example.BackEnd;

import javafx.scene.control.Alert;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RecordManager {


    private ArrayList<Record> listRecord;
    private Optional<ResultSet> result;
    private String [] countries;
    private String [] fields;
    private String typeDiagram;
    private String fieldNameInXaxis;

    public RecordManager(String typeDiagram)
    {
        this.typeDiagram = typeDiagram;
        this.listRecord = new ArrayList<>();
        //default
    }


//global
    ArrayList<Number> listNumberXaxis =  new ArrayList<>();
    //
    public int createRecord() throws SQLException {
        positionFieldsXaxis=getPositionFieldsXaxis();

        if (this.result.isPresent()) {
            ResultSet resultSet = this.result.get();
            for(String country : countries){
                resultSet.beforeFirst();
                Record record = new Record(country);

                while(resultSet.next()){
                    String countryName = resultSet.getString("Display_Name");
                    if(countryName.equals(country)){
                        String year = resultSet.getString("year_group");
                        int numberField = 0;
                        ArrayList<String> values =  new ArrayList<>();
                        for(String field : fields){
                            String namefieldInResultSqlQuery ="avg_"+numberField;
                            String valueOfField = resultSet.getString(namefieldInResultSqlQuery);

                            if(valueOfField != null){  //pros9hkh
                                values.add(valueOfField);
                                //
                                if(numberField == positionFieldsXaxis){
                                    listNumberXaxis.add(Double.parseDouble(valueOfField));   //9elei sort ####
                                }
                                //



                                numberField++;

                            }else{
                               values.add("Not Info");
                                numberField++;
                            }
                            //values.add(valueOfField);
                           // numberField++;

                        }
                        record.setNumberOfFieldsfromQuery(numberField);
                        record.addYearWithList(Integer.parseInt(year), values);

                    }
                }
                if(record.getNumberOfFieldsfromQuery() != 0){
                    listRecord.add(record);
                }
                //listRecord.add(record);
            }
            NumberComparator comparator = new NumberComparator();
            Collections.sort(listNumberXaxis, comparator);
            //System.out.println(listNumberXaxis);
            checkIfNeedSortByYear();
            return 1;
        }
        return -1;
    }


    public class NumberComparator implements Comparator<Number> {
        @Override
        public int compare(Number num1, Number num2) {
            double value1 = num1.doubleValue();
            double value2 = num2.doubleValue();

            if (value1 < value2) {
                return -1;
            } else if (value1 > value2) {
                return 1;
            } else {
                return 0;
            }
        }
    }


    public void sortMapBySmallestYear(){
        ArrayList<Integer> minKeyPerRecordList =  new ArrayList<>();
        int minKey = 9999999;
        Record recordWithMinKey =  null;
        for(Record record : listRecord){
            LinkedHashMap<Integer,ArrayList<String>> mapRecord = record.getMapYearValues();
            if(mapRecord.size()!=0) {
                int firstKey = mapRecord.keySet().iterator().next();
                if (firstKey <= minKey) {
                    minKey = firstKey;
                    recordWithMinKey = record;
                }
            }
        }

        ArrayList<Record> listRecordWithFirstItemSmallestKey = new ArrayList<>();
        listRecordWithFirstItemSmallestKey.add(recordWithMinKey);
        for(Record record : listRecord){

            if(!record.getCountryName().equals(recordWithMinKey.getCountryName())){
                listRecordWithFirstItemSmallestKey.add(record);
            }

        }
        listRecord = listRecordWithFirstItemSmallestKey;


    }

////GLOBAL

    int positionFieldsXaxis ;
    ////


    public  int getPositionFieldsXaxis() {

        positionFieldsXaxis = 0;
        int i=0;
        boolean matchXfieldWithFieldsFormQuery = false;
        for(String field:fields){
            if(field.equals(fieldNameInXaxis)) {
                positionFieldsXaxis = i;
                matchXfieldWithFieldsFormQuery = true;
                break;
            }
            i++;
        }
        if(!matchXfieldWithFieldsFormQuery && !typeDiagram.equals("TimeLine") ){
            ErrorAllerts.getInstance().messageDialog(" name X field not match with choosed fields In Tables \nIN case we use first choosed field for xAxis", Alert.AlertType.ERROR );
        }
      return  positionFieldsXaxis;
    }

    public void setResultSqlQuery(Optional<ResultSet> result){
        this.result = result;

    }

    public ArrayList<Record> getListRecord(){
        return this.listRecord;
     }

    public void setCountriesArray(String[] countries) {
        this.countries = countries;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    public void setFieldNameInXaxis(String fieldNameInXaxis){

        this.fieldNameInXaxis = fieldNameInXaxis;
    }

    public void checkIfNeedSortByYear(){

            if(typeDiagram.equals("TimeLine")||fieldNameInXaxis.equals("year")){

                sortMapBySmallestYear();
            }

    }


    public ArrayList<Number> getListNumberXaxis() {
        return listNumberXaxis;
    }



}
