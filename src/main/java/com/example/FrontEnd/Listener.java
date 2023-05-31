package com.example.FrontEnd;

import com.example.BackEnd.*;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.*;

public class Listener implements Initializable {



    //lifeExpectansy
    @FXML
    private CheckBox infant_mortality;
    @FXML
    private CheckBox infant_mortality_male;
    @FXML
    private CheckBox infant_mortality_female;
    @FXML
    private CheckBox life_expectancy;
    @FXML
    private CheckBox life_expectancy_male;
    @FXML
    private CheckBox life_expectancy_female;
    @FXML
    private CheckBox mortality_rate_under5;
    @FXML
    private CheckBox mortality_rate_under5_male;
    @FXML
    private CheckBox mortality_rate_under5_female;
    @FXML
    private CheckBox mortality_rate_1to4;
    @FXML
    private CheckBox mortality_rate_1to4_male;
    @FXML
    private CheckBox mortality_rate_1to4_female;







    //birthDeath
    @FXML
    private CheckBox crude_birth_rate;
    @FXML
    private CheckBox crude_death_rate;
    @FXML
    private CheckBox net_migration;
    @FXML
    private CheckBox rate_natural_increase;
    @FXML
    private CheckBox growth_rate;

    //Fertility
    @FXML
    private CheckBox total_fertility_rate;
    @FXML
    private CheckBox gross_reproduction_rate;
    @FXML
    private CheckBox sex_ratio_at_birth;
    @FXML
    private CheckBox fertility_rate_15_19;
    @FXML
    private CheckBox fertility_rate_20_24;
    @FXML
    private CheckBox fertility_rate_25_29;
    @FXML
    private CheckBox fertility_rate_30_34;
    @FXML
    private CheckBox fertility_rate_35_39;
    @FXML
    private CheckBox fertility_rate_40_44;
    @FXML
    private CheckBox fertility_rate_45_49;
    @FXML
    private CheckBox sumOfFertilitys;

    //MidYearPopulation
    @FXML
    private CheckBox MidYearPopulation;


    //MidYear5yearAgeSex
    @FXML
    private TextField starting_age;

    @FXML
    private TextField ending_age;
    @FXML
    private CheckBox MidYearPopulation5year;
    @FXML
    private CheckBox midyear_population_female5year;
    @FXML
    private CheckBox midyear_population_male5year;


    //MidYearPopMaleFemaleTotal
    @FXML
    private CheckBox MidYearPopulationMaleFelame;
    @FXML
    private CheckBox midyear_population_male;
    @FXML
    private CheckBox midyear_population_female;


    //MidPopulationAgeSex
    @FXML
    private CheckBox MidYearPopulationAgeSex;
    @FXML
    private CheckBox Male;
    @FXML
    private CheckBox Female;
    @FXML
    private CheckBox Total;
    @FXML
    private TextField age;
    @FXML
    private TextField ageRate;


    //IncomeByCountry
    @FXML
    private CheckBox IncomeIndexValue;
    @FXML
    private CheckBox LabourValue;
    @FXML
    private CheckBox GrossValue;
    @FXML
    private CheckBox GdptotalValue;
    @FXML
    private CheckBox GdpPerCapitaValue;
    @FXML
    private CheckBox GniPerCapitaValue;
    @FXML
    private CheckBox EstimetedGniMaleValue;
    @FXML
    private CheckBox EstimetedGniFemaleValue;
    @FXML
    private CheckBox DomesticValue;


    //SetYearCountriesGraph



    @FXML
    private CheckBox TypeGraphTrendLine;
    @FXML
    private CheckBox TypeGraphTimeLine;
    @FXML
    private CheckBox TypeGraphScatterPlot;
    @FXML
    private CheckBox TypeGraphBarchart;
    @FXML
    private TextField countriesByComma;
    @FXML
    private TextField rangeYear;
    @FXML
    private TextField by;
    @FXML
    private TextField fieldNameforX;

    //Big
    @FXML
    private CheckBox populationBig;
    @FXML
    private TextField rangeYearBig;
    @FXML
    private CheckBox TotalBig;
    @FXML
    private CheckBox MaleBig;
    @FXML
    private CheckBox FemaleBig;
    @FXML
    private TextField ageBigData;

    //
    //actual πεδία

    LinkedHashMap<String , ArrayList<String>> mapTablewithListFields =new LinkedHashMap<>();



    //

    @FXML
    private AppController appController;


    private LinkedHashMap<String,String> mapWithFields =new LinkedHashMap<>();

    public Listener() {
    }

    @FXML
    void okCliked(ActionEvent event) throws SQLException {
        boolean parameters = true;
        if(mapTablewithListFields.size()==0){
            ErrorAllerts.getInstance().messageDialog("No choosed Fields input",Alert.AlertType.ERROR);
        }else{
            FactoryDiagram factory;
            String typeDiagramm = "";
            if(TypeGraphTimeLine.isSelected()){
                //System.out.println("TypeGraphTimeLine");
                factory =new FactoryDiagram("TimeLine");
                typeDiagramm="TimeLine";
            }
            else if(TypeGraphBarchart.isSelected()){
                //System.out.println("TypeGraphBarchart");
                factory =new FactoryDiagram("BarCharts");
                typeDiagramm = "BarCharts";
            } else if(TypeGraphScatterPlot.isSelected()){
                // System.out.println("TypeGraphScatterPlot");
                factory =new FactoryDiagram("ScatterPlot");
                typeDiagramm ="ScatterPlot";

            }else if(TypeGraphTrendLine.isSelected()){
                factory = new FactoryDiagram("TrendLine");
                typeDiagramm = "TrendLine";
            }
            else{
                factory =new FactoryDiagram("null");
                typeDiagramm = "Null";
            }

            if(countriesByComma.getText().equals("")){

                ErrorAllerts.getInstance().messageDialog("No countries Selected ",Alert.AlertType.ERROR);
                parameters = false;
            }
            if(!countriesByComma.getText().contains(",")){
                ErrorAllerts.getInstance().messageDialog("Use commas ",Alert.AlertType.ERROR);


            }
            String[] countriesByCommaText= countriesByComma.getText().split(",");

            if(rangeYear.getText().equals("")){

                ErrorAllerts.getInstance().messageDialog("No YearRange select \n give year , year ",Alert.AlertType.ERROR);
                parameters = false;
            }
            if(!rangeYear.getText().contains(",")){

                ErrorAllerts.getInstance().messageDialog("Use commas ",Alert.AlertType.ERROR);
            }
            String[] yearRange = rangeYear.getText().split(",");
            if(yearRange.length != 2){

                ErrorAllerts.getInstance().messageDialog("No YearRange select \n give year , year ",Alert.AlertType.ERROR);
            }
            //check if is Numbers

            Integer bySql =0;

            if(!by.getText().equals("")){
                bySql = Integer.parseInt(by.getText());
            }else
            {
                bySql =1;

                ErrorAllerts.getInstance().messageDialog("No Choosed By field , ic case we use 1 by default",Alert.AlertType.ERROR);

            }
            String nameFieldXaxis = fieldNameforX.getText();   //refactor
            if(nameFieldXaxis.equals("") && !typeDiagramm.equals("TimeLine")){
                ErrorAllerts.getInstance().messageDialog("Not Selected Xaxis Field",Alert.AlertType.ERROR);
            }
            else{
                //





                if(parameters){
                    CreatorSqlQuery managerSqlQuery = new CreatorSqlQuery(countriesByCommaText,yearRange,bySql,nameFieldXaxis,mapTablewithListFields);
                    Engine engine = new Engine(factory,managerSqlQuery,appController,typeDiagramm);
                    engine.createSqlQuery();
                    int status = engine.callExecuteSqlQuery();
                    if(status == 1){
                        engine.manageResultSqlQuery();

                    }else{
                        ErrorAllerts.getInstance().messageDialog("Wrong initialize Data\n Check Again:\n 1 ) Create base\n2) Create Tables\n3) Load Data", Alert.AlertType.INFORMATION);
                    }


                }else{

                    ErrorAllerts.getInstance().messageDialog("follow before message an try to fix parameters !! ", Alert.AlertType.INFORMATION);
                }


            }


        }


        }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void setParentController(AppController appController) {
        this.appController = appController;
    }

    public void setLifeExpectansyFields(Event event) {

        String keyNameTableInDataBase = "life_expectancy";
        ArrayList<String> fieldsWhoChoosed = new ArrayList<>();
        //TableLife

        if(infant_mortality.isSelected()){

            fieldsWhoChoosed.add("infant_mortality");
        }
        if(infant_mortality_male.isSelected()){

            fieldsWhoChoosed.add("infant_mortality_male");
        }
        if(infant_mortality_female.isSelected()){

            fieldsWhoChoosed.add("infant_mortality_female");
        }
        if(life_expectancy.isSelected()){

            fieldsWhoChoosed.add("life_expectancy");
        }
        if(life_expectancy_male.isSelected()){

            fieldsWhoChoosed.add("life_expectancy_male");
        }
        if(life_expectancy_female.isSelected()){

            fieldsWhoChoosed.add("life_expectancy_female");
        }
        if(mortality_rate_under5.isSelected()){

            fieldsWhoChoosed.add("mortality_rate_under5");
        }
        if(mortality_rate_under5_male.isSelected()){

            fieldsWhoChoosed.add("mortality_rate_under5_male");
        }
        if(mortality_rate_under5_female.isSelected()){

            fieldsWhoChoosed.add("mortality_rate_under5_female");
        }
        if(mortality_rate_1to4.isSelected()){

            fieldsWhoChoosed.add("mortality_rate_1to4");
        }
        if(mortality_rate_1to4_male.isSelected()){

            fieldsWhoChoosed.add("mortality_rate_1to4_male");
        }
        if(mortality_rate_1to4_female.isSelected()){

            fieldsWhoChoosed.add("mortality_rate_1to4_female");
        }

        if(fieldsWhoChoosed.size()!=0){
            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);
            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }

        }
        //System.out.println("setLifeExpectansyFields");


    }

    public void setBirthDeathGrowth(Event event) {
        String keyNameTableInDataBase = "BirtDeathGrowth";
        ArrayList<String> fieldsWhoChoosed = new ArrayList<>();
        // System.out.println("pathse birthDeath");
        if(crude_birth_rate.isSelected()){

            fieldsWhoChoosed.add("crude_birth_rate");
        }
        if(crude_death_rate.isSelected()){

            fieldsWhoChoosed.add("crude_death_rate");
        }
        if(net_migration.isSelected()){

            fieldsWhoChoosed.add("net_migration");
        }
        if(rate_natural_increase.isSelected()){

            fieldsWhoChoosed.add("rate_natural_increase");
        }
        if(growth_rate.isSelected()){

            fieldsWhoChoosed.add("growth_rate");
        }

        if(fieldsWhoChoosed.size()!=0){
            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);
            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }


        }

    }

    public void setFertilityRates(Event event) {
        String keyNameTableInDataBase = "fertility_rates";
        ArrayList<String> fieldsWhoChoosed = new ArrayList<>();

        if(total_fertility_rate.isSelected()){
            fieldsWhoChoosed.add("total_fertility_rate");
        }
        if(gross_reproduction_rate.isSelected()){

            fieldsWhoChoosed.add("gross_reproduction_rate");
        }
        if(sex_ratio_at_birth.isSelected()){

            fieldsWhoChoosed.add("sex_ratio_at_birth");
        }
        if(fertility_rate_15_19.isSelected()){

            fieldsWhoChoosed.add("fertility_rate_15_19");
        }
        if(fertility_rate_20_24.isSelected()){

            fieldsWhoChoosed.add("fertility_rate_20_24");
        }
        if(fertility_rate_25_29.isSelected()){

            fieldsWhoChoosed.add("fertility_rate_25_29");
        }
        if(fertility_rate_30_34.isSelected()){

            fieldsWhoChoosed.add("fertility_rate_30_34");
        }
        if(fertility_rate_35_39.isSelected()){

            fieldsWhoChoosed.add("fertility_rate_35_39");
        }
        if(fertility_rate_40_44.isSelected()){

            fieldsWhoChoosed.add("fertility_rate_40_44");
        }
        if(fertility_rate_45_49.isSelected()){

            fieldsWhoChoosed.add("fertility_rate_45_49");
        }


        if(fieldsWhoChoosed.size()!=0) {

            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                if(sumOfFertilitys.isSelected() ){

                    String fieldQuery= "";
                    String anotherFields = "";
                    for(String field :fieldsWhoChoosed ){
                        if(field.contains("fertility_rate_")) {
                            fieldQuery += "fertility_rates." + field + "+";
                        }else{
                            anotherFields+=field+" ,";
                        }

                    }

                    fieldsWhoChoosed.clear();
                    if(fieldQuery.length()!=0) {
                        fieldQuery = fieldQuery.substring(0, fieldQuery.length() - 1);
                        fieldsWhoChoosed.add(fieldQuery);
                    }else{
                        ErrorAllerts.getInstance().messageDialog("You choosed sum and also wrong felds for sum",Alert.AlertType.ERROR);
                    }
                    if(anotherFields.length() !=0){
                        anotherFields = anotherFields.substring(0,anotherFields.length()-1);
                        fieldsWhoChoosed.add(anotherFields);
                    }


                }
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);

            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }



        }else if(sumOfFertilitys.isSelected() ) {
            ErrorAllerts.getInstance().messageDialog("You choosed sum without fields fertility_rate",Alert.AlertType.ERROR);
        }


    }

    public void setMidYearpopulation(Event event) {
        String keyNameTableInDataBase = "MidYearpopulation";
        ArrayList<String> fieldsWhoChoosed = new ArrayList<>();

        if(MidYearPopulation.isSelected()){
            System.out.println("MidYearPopulation");
            fieldsWhoChoosed.add("midYear_population");
        }
        if (fieldsWhoChoosed.size() !=0 ) {

            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);
            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }

        }

    }

    public void setMidYear5Yearagesex(Event event) {

        String keyNameTableInDataBase = "MidYear5Yearagesex";
        ArrayList<String> fieldsWhoChoosed = new ArrayList<>();

        if(MidYearPopulation5year.isSelected()){

            fieldsWhoChoosed.add("midyearPopulation");
        }
        if(midyear_population_male5year.isSelected()){

            fieldsWhoChoosed.add("midyearPopulation_male");
        }
        if(midyear_population_female5year.isSelected()){

            fieldsWhoChoosed.add("midyearPopulation_female");
        }
        if(fieldsWhoChoosed.size()!=0){

            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){

                String ageStart = starting_age.getText();
                String ageEnd = ending_age.getText();

                if(!ageStart.equals("")){
                    fieldsWhoChoosed.add("starting_age ,"+ageStart );
                }
                if(!ageEnd.equals("")){
                    fieldsWhoChoosed.add("ending_age ,"+ageEnd );
                }

                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);

            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }


        }


    }
    public void setmidYearPopulationMaleFemale(Event event) {

        String keyNameTableInDataBase = "MidYearPopulationAgeSexMaleFemaleTotal";
        ArrayList<String> fieldsWhoChoosed = new ArrayList<>();

        if(MidYearPopulationMaleFelame.isSelected()){

            fieldsWhoChoosed.add("midYearPopulation");
        }
        if(midyear_population_male.isSelected()){

            fieldsWhoChoosed.add("midYear_population_male");
        }
        if(midyear_population_female.isSelected()){

            fieldsWhoChoosed.add("midYear_population_female");
        }

        if(fieldsWhoChoosed.size()!=0){
            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);
            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }

        }

    }

    public void setMidPopAgeSex(Event event) {
        String keyNameTableInDataBase = "MidYearPopulationAgeSex";
        ArrayList<String> fieldsWhoChoosed = new ArrayList<>();

        if(Male.isSelected()){

            fieldsWhoChoosed.add("male");
        }
        if(Female.isSelected()){

            fieldsWhoChoosed.add("female");
        }
        if(Total.isSelected()){

            fieldsWhoChoosed.add("Total");
        }
        if(MidYearPopulationAgeSex.isSelected()){
            System.out.println("MidYearPopulationAgeSex");
            fieldsWhoChoosed.add("population");
        }

        if(fieldsWhoChoosed.size()!=0){
            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                String ageText = age.getText();
                String ageRateText =ageRate.getText();
                if(!ageText.equals("")){
                    fieldsWhoChoosed.add("age,"+ageText);
                }
                if(!ageRateText.equals("")){
                    fieldsWhoChoosed.add("ageRangeAgeSex,"+ageRateText);
                }
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);

            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }

        }



    }

    public void SetIncomeBycountry(Event event) {


        if(IncomeIndexValue.isSelected()){
            String keyNameTableInDataBase = "IncomeIndex";
            ArrayList<String> fieldsWhoChoosed = new ArrayList<>();
            fieldsWhoChoosed.add("Income_Index_Value");

            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);
            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }
        }
        if(LabourValue.isSelected()){

            String keyNameTableInDataBase = "LabourOf";
            ArrayList<String> fieldsWhoChoosed = new ArrayList<>();
            fieldsWhoChoosed.add("Labour_Of_Value");
            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);
            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }
        }
        if(GrossValue.isSelected()){

            String keyNameTableInDataBase = "GrossFixed";
            ArrayList<String> fieldsWhoChoosed = new ArrayList<>();
            fieldsWhoChoosed.add("Gross_Fixed_Value");
            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);
            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }
        }
        if(GdptotalValue.isSelected()){

            String keyNameTableInDataBase = "GDPTotal";
            ArrayList<String> fieldsWhoChoosed = new ArrayList<>();
            fieldsWhoChoosed.add("Gdp_Total_Value");
            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);
            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }

        }
        if(GdpPerCapitaValue.isSelected()){

            String keyNameTableInDataBase = "GDPperCapita";
            ArrayList<String> fieldsWhoChoosed = new ArrayList<>();
            fieldsWhoChoosed.add("Gdp_Per_Capita_Value");
            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);
            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }
        }
        if(GniPerCapitaValue.isSelected()){

            String keyNameTableInDataBase = "GNIPerCapita";
            ArrayList<String> fieldsWhoChoosed = new ArrayList<>();
            fieldsWhoChoosed.add("Gni_Per_Capita_Value");
            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);
            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }
        }
        if(EstimetedGniMaleValue.isSelected()){

            String keyNameTableInDataBase = "estimatedGNiMale";
            ArrayList<String> fieldsWhoChoosed = new ArrayList<>();
            fieldsWhoChoosed.add("GNi_Male_Value");
            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);
            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }
        }
        if(EstimetedGniFemaleValue.isSelected()){

            String keyNameTableInDataBase = "estimatedGNiFemale";
            ArrayList<String> fieldsWhoChoosed = new ArrayList<>();
            fieldsWhoChoosed.add("GNi_Female_Value");
            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);
            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }
        }
        if(DomesticValue.isSelected()){

            String keyNameTableInDataBase = "credit_data";
            ArrayList<String> fieldsWhoChoosed = new ArrayList<>();
            fieldsWhoChoosed.add("credit_data_value");
            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);
            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }
        }

    }
    public void setBigData(Event event) {
        String keyNameTableInDataBase = "BigPopbyAge";
        ArrayList<String> fieldsWhoChoosed = new ArrayList<>();

        if(MaleBig.isSelected()){

            fieldsWhoChoosed.add("male");
        }
        if(FemaleBig.isSelected()){

            fieldsWhoChoosed.add("female");
        }
        if(TotalBig.isSelected()){

            fieldsWhoChoosed.add("Total");
        }
        if(populationBig.isSelected()){
            fieldsWhoChoosed.add("populationBig");
        }

        if(fieldsWhoChoosed.size()!=0){
            if(mapTablewithListFields.get(keyNameTableInDataBase)==null){
                String ageText = ageBigData.getText();
                String ageRateText =rangeYearBig.getText();
                if(!ageText.equals("")){
                    fieldsWhoChoosed.add("age,"+ageText);
                }
                if(!ageRateText.equals("")){
                    fieldsWhoChoosed.add("ageRangeAgeSex,"+ageRateText);
                }
                mapTablewithListFields.put(keyNameTableInDataBase,fieldsWhoChoosed);

            }else{
                mapTablewithListFields.remove(keyNameTableInDataBase);
            }

        }


    }

    public void setGraph(Event event){
            //If changed Ok Pressed()
    }


}
