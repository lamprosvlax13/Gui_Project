LOAD DATA LOCAL INFILE 'src/main/resources/countriesTable.csv'
    INTO TABLE countries
    CHARACTER SET latin1
    FIELDS OPTIONALLY ENCLOSED BY '"' TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/AgeFertility_rates.csv'
    INTO TABLE fertility_rates
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/BirthDeathGrowth.csv'
    INTO TABLE BirtDeathGrowth
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/DomesticCredits.csv'
    INTO TABLE credit_data
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/EstimatedGniFemale.csv'
    INTO TABLE estimatedGNiFemale
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/EstimatedGniMale.csv'
    INTO TABLE estimatedGNiMale
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/GniPerCapita.csv'
    INTO TABLE GNIPerCapita
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/GdpPerCapita.csv'
    INTO TABLE GDPperCapita
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/GdpTotal.csv'
    INTO TABLE GDPTotal
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/IncomeIndex.csv'
    INTO TABLE IncomeIndex
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/LabourOf.csv'
    INTO TABLE LabourOf
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/GrossFixed.csv'
    INTO TABLE GrossFixed
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/com/example/FrontEnd/Midyear.csv'
    INTO TABLE MidYearpopulation
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/com/example/FrontEnd/midyearpopulation5yragesexWITHOUTONLYTOTAL.csv'
    INTO TABLE MidYear5Yearagesex
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/midyearpopulationagesex.csv'
    INTO TABLE MidYearPopulationAgeSex
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/com/example/FrontEnd/midyearpopulationMaleFemaletotal.csv'
    INTO TABLE MidYearPopulationAgeSexMaleFemaleTotal
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/LifeExpectansy.csv'
    INTO TABLE life_expectancy
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
LOAD DATA LOCAL INFILE 'src/main/resources/com/example/FrontEnd/Big.csv'
    INTO TABLE BigPopbyAge
    FIELDS TERMINATED BY ','
    LINES TERMINATED BY '\n'
    IGNORE 1 ROWS;
----
