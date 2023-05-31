package com.example.BackEnd;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Record {

    private String countryName;
    private LinkedHashMap<Integer,ArrayList<String>> mapYearValues;
    private int numberOfFieldsfromQuery = 0;

    public Record(String countryName){
        this.countryName =countryName;
        this.mapYearValues =  new LinkedHashMap<>();
    }

    public void addYearWithList(int year , ArrayList<String> valuesInYear){

        mapYearValues.put(year,valuesInYear);
    }
    public void setNumberOfFieldsfromQuery(int number){
        this.numberOfFieldsfromQuery =  number;
    }
    public int getNumberOfFieldsfromQuery(){
        return numberOfFieldsfromQuery;
    }

    public String getCountryName() {

        return countryName;
    }

    public LinkedHashMap<Integer, ArrayList<String>> getMapYearValues()
    {
        return mapYearValues;
    }


}
