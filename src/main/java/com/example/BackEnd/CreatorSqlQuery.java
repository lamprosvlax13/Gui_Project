package com.example.BackEnd;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
public class CreatorSqlQuery {


    private final String[] countriesText;
    private final String[] yearRange;
    private final int by;

    private final String nameFieldXaxis;
    private final LinkedHashMap<String, ArrayList<String>> mapTableWithFields;
    private String countries;
    private  String finalQuiry= "";
    private String pointerInArrayTable="";


    //************************************ Global Variables for Class
    String avgFieldXaxis= "";
    String FieldsWhoTakedFromQuery = "";
    String innerJoin = "";
    String andAgeRange = "";
    int numberOfValue = 0;
    String firstTable = "";
    String arrayTablePointer="";

    String sqlQueiry5YearAgeSexTable = "";
    String sqlQueryAgeYearTable = "";
    ArrayList<Integer> listWithNumberPositionValueMin5year = new ArrayList<>();
    ArrayList<Integer> listWithNumberPositionValueYearAge = new ArrayList<>();
    ArrayList<Integer> resultPotionValue = new ArrayList<>();
    ArrayList<Integer> listWithNumberPositionValueBig =  new ArrayList<>();
    String sqlQueryBigPopulationTable = "";
    ArrayList<String> finalquiryList = new ArrayList<>();


    public CreatorSqlQuery(String[] countriesByCommaText, String[] yearRange, Integer bySql, String nameFieldXaxis, LinkedHashMap<String, ArrayList<String>> mapWithFields) {
        super();
        this.countriesText = countriesByCommaText;
        countries = getCountries();
        this.yearRange = yearRange;
        this.by = bySql;
        this.nameFieldXaxis = nameFieldXaxis;
        this.mapTableWithFields = mapWithFields;


    }

    int position =0;
    public void createSqlQueryByTable(){
        //int position = 0;
        for (Map.Entry<String, ArrayList<String>> entry : mapTableWithFields.entrySet()) {
            skipTables(entry);
            if (entry.getKey().equals("fertility_rates")) {
                manageFertilityRateTableInQuery(entry);
            } else if (entry.getKey().equals("MidYear5Yearagesex")) {
                manageMid5YearAgeSexTableInQuery(countries, entry);
            } else if (entry.getKey().equals("MidYearPopulationAgeSex")) {
                manageMidYearPopulayionAgeSexTableInQuery(countries, entry);

            } else if (entry.getKey().equals("BigPopbyAge")) {
                manageBigPopByAgeTableInQuery(countries, entry);
            } else {
                managerAnotherTablesInQuery(entry);

            }
            
            arrayTablePointer += entry.getKey() + ",";
            position++;


        }
        if (FieldsWhoTakedFromQuery.length() != 0) {
            FieldsWhoTakedFromQuery = FieldsWhoTakedFromQuery.substring(0, FieldsWhoTakedFromQuery.length() - 2); // , afairw
        }


    }

    private String getCountries() {
        String countries = "( ";

        for(String country : countriesText){
            countries+="'"+country+"',";
        }
        countries = countries.substring(0,countries.length()-1);//afairw ,
        countries +=" )";
        return countries;
    }

    private void skipTables(Map.Entry<String, ArrayList<String>> entry) {

        if (position == 0 && !entry.getKey().equals("MidYear5Yearagesex") && !entry.getKey().equals("MidYearPopulationAgeSex")) {
            FieldsWhoTakedFromQuery = "CEIL(" + entry.getKey() + ".Year / " + by + ") * " + by + " AS year_group,\n";
            innerJoin += "LEFT JOIN " + entry.getKey() + " ON " + "countries.iso_code = " + entry.getKey() + ".Iso_code\n";
            firstTable = entry.getKey();
        }
        if (entry.getKey().equals("MidYear5Yearagesex") || entry.getKey().equals("MidYearPopulationAgeSex")) {
            position--;
        }


    }

    private void managerAnotherTablesInQuery(Map.Entry<String, ArrayList<String>> entry) {
        if (!firstTable.equals(entry.getKey())) {
            String and = " AND " + entry.getKey() + ".Year = " + firstTable + ".Year";
            innerJoin += "LEFT JOIN " + entry.getKey() + " ON " + "countries.iso_code = " + entry.getKey() + ".Iso_code" + and + "\n";
        }
        
        for (String field : entry.getValue()) {
            FieldsWhoTakedFromQuery += "AVG(" + entry.getKey() + "." + field + ")" + " AS " + "avg_" + numberOfValue + " ," + "\n";
            resultPotionValue.add(numberOfValue);

            if(field.equals(nameFieldXaxis)){
                avgFieldXaxis =  "avg_" + numberOfValue;
            }

            numberOfValue++;
            pointerInArrayTable += field + ",";


        }
    }

    private void manageMid5YearAgeSexTableInQuery(String countries, Map.Entry<String, ArrayList<String>> entry) {

        String FieldsWhoTakedFromQueryMin5year = "";
        String andAgeRangeMin5year = "";



        boolean haveSum = false;
        String startingAge = "";
        String endingAge = "";
        for (String field : entry.getValue()) {
            if (field.contains("starting_age")) {
                startingAge = field.split(",")[1];
                if (!startingAge.equals("")) {
                    haveSum = true;
                }

            }
            if (field.contains("ending_age ,")) {
                endingAge = field.split(",")[1];
                if (!endingAge.equals("")) {
                    haveSum = true;
                }

            }

        }


        if (haveSum) {
            for (String field : entry.getValue().subList(0, entry.getValue().size() - 2)) {

                //  FieldsWhoTakedFromQuery += "SUM(" + entry.getKey() + "." + field + ")" + " AS " + "avg_" + numberOfValue + " ," + "\n";  //SUM
                FieldsWhoTakedFromQueryMin5year += "SUM(" + entry.getKey() + "." + field + ")/"+by + " AS " + "avg_" + numberOfValue + " ," + "\n";  //SUM
                andAgeRangeMin5year = " AND  " + entry.getKey() + "." + "starting_age " + ">= " + startingAge + " AND  " + entry.getKey() + "." + "ending_age" + " <= " + endingAge + "\n";
                pointerInArrayTable += field + ",";
                if(field.equals(nameFieldXaxis)){
                    avgFieldXaxis =  "avg_" + numberOfValue;
                }
                numberOfValue++;
                listWithNumberPositionValueMin5year.add(numberOfValue - 1);
            }
        } else {

            for (String field : entry.getValue().subList(0, entry.getValue().size())) {
                FieldsWhoTakedFromQueryMin5year += "AVG(" + entry.getKey() + "." + field + ")" + " AS " + "avg_" + numberOfValue + " ," + "\n";  //SUM
                pointerInArrayTable += field + ",";
                if(field.equals(nameFieldXaxis)){
                    avgFieldXaxis =  "avg_" + numberOfValue;
                }
                numberOfValue++;
                listWithNumberPositionValueMin5year.add(numberOfValue - 1);
            }

        }

        sqlQueiry5YearAgeSexTable = "SELECT countries.Display_Name, \n" +
                "CEIL(MidYear5Yearagesex.Year / " + by + ") * " + by + " AS year_group," +
                FieldsWhoTakedFromQueryMin5year.substring(0, FieldsWhoTakedFromQueryMin5year.length() - 2) + " \n" +
                "FROM countries\n" +
                "LEFT JOIN MidYear5Yearagesex \n" +
                "ON countries.iso_code = MidYear5Yearagesex.Iso_code\n" +
                "WHERE countries.Display_Name IN " + countries + "\n" +
                "AND MidYear5Yearagesex.Year BETWEEN " + yearRange[0] + " AND " + yearRange[1] + "\n" +
                andAgeRangeMin5year +
                "GROUP BY countries.Display_Name, year_group";



    }

    private void manageMidYearPopulayionAgeSexTableInQuery(String countries, Map.Entry<String, ArrayList<String>> entry) {

        boolean haveSum = false;

        String age = "";
        String ageStart = "";
        String ageEnd = "";
        String FieldsWhoTakedFromQueryAgeYear = "";
        String andSex = "";
        String andAge = "";
        for (String field : entry.getValue()) {
            if (field.equals("male")) {
                andSex += " AND MidYearPopulationAgeSex.sex = 'male' \n";

            }
            if (field.equals("female")) {
                andSex += " AND MidYearPopulationAgeSex.sex = 'female' \n";


            }
            if (field.equals("Total")) {
                andSex += " AND  MidYearPopulationAgeSex.sex IN ('male', 'female')\n";

            }
            if (field.contains("age,")) {
                age = field.split(",")[1];
                andAge += " AND MidYearPopulationAgeSex.age = " + age + "\n";

            }

            if (field.contains("ageRangeAgeSex")) {
                ageStart = field.split(",")[1];
                ageEnd = field.split(",")[2];
                andAgeRange += "AND  " + entry.getKey() + ".age " + " >= " + ageStart + "\n";
                andAgeRange += "AND  " + entry.getKey() + ".age " + " <= " + ageEnd + "\n";
                haveSum = true;
            }

        }
        if (haveSum) {
            // FieldsWhoTakedFromQuery+="SUM("+entry.getKey()+"."+"population"+")"+" AS "+"avg_"+numberOfValue+" ,"+"\n";
            FieldsWhoTakedFromQueryAgeYear += "SUM(" + entry.getKey() + "." + "population" + ")/"+by + " AS " + "avg_" + numberOfValue + " ," + "\n"; //SUM
            listWithNumberPositionValueYearAge.add(numberOfValue);
            String field = "population";
            if(field.equals(nameFieldXaxis)){
                avgFieldXaxis =  "avg_" + numberOfValue;
            }
            numberOfValue++;
            pointerInArrayTable += "population" + ",";

        } else {
            //FieldsWhoTakedFromQuery+="AVG("+entry.getKey()+"."+"population"+")"+" AS "+"avg_"+numberOfValue+" ,"+"\n";
            FieldsWhoTakedFromQueryAgeYear +="AVG(" + entry.getKey() + "." + "population" + ")" + " AS " + "avg_" + numberOfValue + " ," + "\n";
            pointerInArrayTable += "population" + ",";
            listWithNumberPositionValueYearAge.add(numberOfValue);
            String field = "population";
            if(field.equals(nameFieldXaxis)){
                avgFieldXaxis =  "avg_" + numberOfValue;
            }

            numberOfValue++;
        }

        sqlQueryAgeYearTable = "SELECT countries.Display_Name, \n" +
                "CEIL(MidYearPopulationAgeSex.Year / " + by + ") * " + by + " AS year_group," +
                FieldsWhoTakedFromQueryAgeYear.substring(0, FieldsWhoTakedFromQueryAgeYear.length() - 2) + " \n" +
                "FROM countries\n" +
                "LEFT JOIN MidYearPopulationAgeSex \n" +
                "ON countries.iso_code = MidYearPopulationAgeSex.Iso_code\n" +
                "WHERE countries.Display_Name IN " + countries + "\n" +
                "AND MidYearPopulationAgeSex.Year BETWEEN " + yearRange[0] + " AND " + yearRange[1] + "\n" +
                andAgeRange + andSex + andAge +
                "GROUP BY countries.Display_Name, year_group";


        //System.out.println(sqlqueiryAgeYear);
    }




    private void manageBigPopByAgeTableInQuery(String countries, Map.Entry<String, ArrayList<String>> entry) {

        String andAgeRangeBig ="";
        String andSexBig ="";
        String andAgeBig =  "";
        String FieldsWhoTakedFromQueryBig = "" ;


        boolean haveSum = false;
        String age = "";
        String ageStart = "";
        String ageEnd = "";
        for (String field : entry.getValue()) {
            if (field.equals("male")) {
                andSexBig += " AND BigPopbyAge.sex = 'male' \n";
            }
            if (field.equals("female")) {
                andSexBig += " AND BigPopbyAge.sex = 'female' \n";

            }
            if (field.equals("Total")) {
                andSexBig += " AND  BigPopbyAge.sex IN ('male', 'female')\n";
            }
            if (field.contains("age,")) {
                age = field.split(",")[1];
                andAgeBig += " AND BigPopbyAge.age = " + age + "\n";
            }

            if (field.contains("ageRangeAgeSex")) {
                ageStart = field.split(",")[1];
                ageEnd = field.split(",")[2];
                andAgeRangeBig += "AND  " + entry.getKey() + ".age " + " >= " + ageStart + "\n";
                andAgeRangeBig += "AND  " + entry.getKey() + ".age " + " <= " + ageEnd + "\n";
                haveSum = true;
            }

        }
        if (haveSum) {
            // FieldsWhoTakedFromQuery+="SUM("+entry.getKey()+"."+"population"+")"+" AS "+"avg_"+numberOfValue+" ,"+"\n";
            FieldsWhoTakedFromQueryBig += "SUM(" + entry.getKey() + "." + "populationBig" + ")/"+by + " AS " + "avg_" + numberOfValue + " ," + "\n";
            listWithNumberPositionValueBig.add(numberOfValue);
            String field = "populationBig";
            if(field.equals(nameFieldXaxis)){
                avgFieldXaxis =  "avg_" + numberOfValue;
            }
            numberOfValue++;
            pointerInArrayTable += "populationBig" + ",";

        } else {
            //FieldsWhoTakedFromQuery+="AVG("+entry.getKey()+"."+"population"+")"+" AS "+"avg_"+numberOfValue+" ,"+"\n";
            FieldsWhoTakedFromQueryBig += "AVG(" + entry.getKey() + "." + "populationBig" + ")" + " AS " + "avg_" + numberOfValue + " ," + "\n";
            pointerInArrayTable += "populationBig" + ",";
            listWithNumberPositionValueBig.add(numberOfValue);
            String field = "populationBig";
            if(field.equals(nameFieldXaxis)){
                avgFieldXaxis =  "avg_" + numberOfValue;
            }

            numberOfValue++;
        }

        sqlQueryBigPopulationTable = "SELECT countries.Display_Name, \n" +
                "CEIL(BigPopbyAge.Year / " + by + ") * " + by + " AS year_group, " +
                FieldsWhoTakedFromQueryBig.substring(0, FieldsWhoTakedFromQueryBig.length() - 2) + " \n" +
                "FROM countries\n" +
                "LEFT JOIN BigPopbyAge \n" +
                "ON countries.iso_code = BigPopbyAge.Iso_code\n" +
                "WHERE countries.Display_Name IN " + countries + "\n" +
                "AND BigPopbyAge.Year BETWEEN " + yearRange[0] + " AND " + yearRange[1] + "\n" +
                andAgeRangeBig + andSexBig + andAgeBig +
                "GROUP BY countries.Display_Name, year_group";


        //System.out.println(sqlqueiryAgeYear);
    }

    private void manageFertilityRateTableInQuery(Map.Entry<String, ArrayList<String>> entry) {
        
        if (!firstTable.equals(entry.getKey())) {
            String and = " AND " + entry.getKey() + ".Year = " + firstTable + ".Year";
            innerJoin += "LEFT JOIN " + entry.getKey() + " ON " + "countries.iso_code = " + entry.getKey() + ".Iso_code" + and + "\n";
        }

        for (String field : entry.getValue()) {
            if (field.contains("fertility_rates.fertility_rate_")) {

                FieldsWhoTakedFromQuery += "SUM(" + field + ")" + " AS " + "avg_" + numberOfValue + " ," + "\n";
                pointerInArrayTable += field + ",";
                resultPotionValue.add(numberOfValue);
                numberOfValue++;
            } else {
                String[] anotherFields = field.split(",");
                for (String another : anotherFields) {
                    FieldsWhoTakedFromQuery += "AVG(" + entry.getKey() + "." + another + ")" + " AS " + "avg_" + numberOfValue + " ," + "\n";
                    pointerInArrayTable += another + ",";
                    resultPotionValue.add(numberOfValue);

                    if (field.equals(nameFieldXaxis)) {
                        avgFieldXaxis = "avg_" + numberOfValue;
                    }
                    numberOfValue++;
                }

            }

        }
    }

    public void createTotalSqlQuery(){

        String select;
        String from;
        String where;
        String groupBy;
        String orderBy;

        select =  "SELECT countries.Display_Name, \n";
        from = "FROM countries\n";
        where = "WHERE countries.Display_Name " + "IN " + countries + " AND " + firstTable + ".Year" + " BETWEEN " + yearRange[0] + " AND " + yearRange[1] + "\n";
        groupBy = "GROUP BY countries.Display_Name, year_group\n";
        String resultSqlQuery = select + FieldsWhoTakedFromQuery + from + innerJoin + where + groupBy;//+orderBy;
        LinkedHashMap<String, Integer> map =initializeMapQueryFields(resultSqlQuery);

        if (finalquiryList.size() == 1) {
            String sqlQuiry = finalquiryList.get(0);
            finalQuiry = sqlQuiry; 
        }else{
            String fields = createfieldsInQuery(resultSqlQuery, map);

            //gia order By
            String[] tokens = fields.split(",");
            String order = "";

            if(avgFieldXaxis.equals("") /*&& nameFieldXaxis.equals("year")*/){
                order="year_group";
            }else{
                avgFieldXaxis= "avg_0";
            }
            for(String field : tokens){
                if(field.indexOf(avgFieldXaxis)>0){
                    order = field;
                }

            }
            /*
            if(order.equals("") && nameFieldXaxis.equals("year")){
                order="year_group";
            }
            else{

            }*/

            fields = fields.substring(0,fields.length()-1);

            String tId = "t";
            String finalQ = "SELECT t1.Display_Name,t1.year_group," +fields+"\n" ;
            String fromFinal = " FROM (" + finalquiryList.get(0) + ") "+tId+(1)+ "\n" ;
            finalQ += fromFinal;
            //for(String queries : finalquiryList){

            for (int i = 1; i < finalquiryList.size() ; i++) {

                finalQ += " LEFT JOIN (" + "\n"
                        + finalquiryList.get( i) + ") "+tId+(i+1) + "\n"
                        + "ON "+tId+(i+1)+".Display_Name = "+tId+(i)+".Display_Name AND "+tId+(i+1)+".year_group = "+tId+(i)+".year_group";


            }
            finalQuiry = finalQ;
            orderBy = "\nORDER BY "+order; //, t1.Display_Name "
            finalQuiry+=orderBy;

        }


    }

    private String createfieldsInQuery(String resultSqlQuery, LinkedHashMap<String, Integer> map) {
        String t1field = "";
        for (Integer numberOfField : resultPotionValue) {
            int num = map.get(resultSqlQuery);
            t1field += "t"+num+".avg_" + numberOfField + " ,";

        }

        String t2fields = "";

        for (Integer numberOfField : listWithNumberPositionValueMin5year) {
            int num = map.get(sqlQueiry5YearAgeSexTable);
            t2fields += "t"+num+".avg_" + numberOfField + " ,";
        }

        String t3fields = "";


        for (Integer numberOfField : listWithNumberPositionValueYearAge) {
            int num = map.get(sqlQueryAgeYearTable);
            t3fields += "t"+num+".avg_" + numberOfField + " ,";
        }

        String t4fields = "";

        for (Integer numfield : listWithNumberPositionValueBig){
            int num = map.get(sqlQueryBigPopulationTable);
            t4fields += "t"+num+".avg_"+ numfield + ",";
        }

        String  fields= t1field+t2fields+t3fields+t4fields;
        return fields;
    }

    private LinkedHashMap<String, Integer> initializeMapQueryFields(String resultSqlQuery) {
        LinkedHashMap<String ,Integer> map = new LinkedHashMap<>();
        if (listWithNumberPositionValueYearAge.size() != 0) {
            finalquiryList.add(sqlQueryAgeYearTable);
            map.put(sqlQueryAgeYearTable,finalquiryList.size());
        }
        if (listWithNumberPositionValueMin5year.size() != 0) {
            finalquiryList.add(sqlQueiry5YearAgeSexTable);
            map.put(sqlQueiry5YearAgeSexTable,finalquiryList.size());
        }
        if (listWithNumberPositionValueBig.size() != 0) {
            finalquiryList.add(sqlQueryBigPopulationTable);
            map.put(sqlQueryBigPopulationTable,finalquiryList.size());
        }
        if (resultPotionValue.size() != 0) {

            finalquiryList.add(resultSqlQuery);
            map.put(resultSqlQuery,finalquiryList.size());
        }
        return map;
    }


    public String getFinalQuiry() {

        return finalQuiry;
    }

    public String[] getCountriesText() {

        return countriesText;
    }
    public String[] getPointerInArrayTable() {
        String [] pointerInTable = pointerInArrayTable.split(",");
        return pointerInTable;
    }
    public String getNameFieldXaxis() {
        return nameFieldXaxis;
    }


}
