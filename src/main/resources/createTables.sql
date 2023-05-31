CREATE TABLE `countries` (
                             `iso_code` INT PRIMARY KEY NOT NULL,
                             `ISO` VARCHAR(50),
                             `ISO3` VARCHAR(50),
                             `FIPS` VARCHAR(50),
                             `Display_Name` VARCHAR(50),
                             `Official_Name` VARCHAR(100),
                             `Capital` VARCHAR(50),
                             `Continent` VARCHAR(50),
                             `CurrencyCode` VARCHAR(50),
                             `CurrencyName` VARCHAR(50),
                             `Phone` VARCHAR(50),
                             `RegionCode` VARCHAR(50),
                             `RegionName` VARCHAR(50),
                             `SubRegionCode` VARCHAR(50),
                             `SubRegionName` VARCHAR(50),
                             `IntermediateRegionCode` VARCHAR(50),
                             `IntermediateRegionName` VARCHAR(50),
                             `Status` VARCHAR(50),
                             `DevelopedOrDeveloping` VARCHAR(50),
                             `SmallIslandDevelopingStates` VARCHAR(50),
                             `LandLockedDevelopingCountries` VARCHAR(50),
                             `LeastDevelopedCountries` VARCHAR(50),
                             `Area_SqKm` VARCHAR(50),
                             `Population` VARCHAR(50)
);
----
CREATE TABLE `fertility_rates` (
                                   `Iso_code` INT NOT NULL,
                                   `Year` INT NOT NULL,
                                   `fertility_rate_15_19` FLOAT,
                                   `fertility_rate_20_24` FLOAT,
                                   `fertility_rate_25_29` FLOAT,
                                   `fertility_rate_30_34` FLOAT,
                                   `fertility_rate_35_39` FLOAT,
                                   `fertility_rate_40_44` FLOAT,
                                   `fertility_rate_45_49` FLOAT,
                                   `total_fertility_rate` FLOAT,
                                   `gross_reproduction_rate` FLOAT,
                                   `sex_ratio_at_birth` FLOAT,
                                   PRIMARY KEY (`Iso_code`, `Year`)
);
----
CREATE TABLE `BirtDeathGrowth` (
                                   `Iso_code` INT NOT NULL,
                                   `Year` INT NOT NULL,
                                   `crude_birth_rate` FLOAT,
                                   `crude_death_rate` FLOAT,
                                   `net_migration` FLOAT,
                                   `rate_natural_increase` FLOAT,
                                   `growth_rate` FLOAT,
                                   PRIMARY KEY (`Iso_code`, `Year`)
);
----
CREATE TABLE `credit_data` (
                               `Iso_code` INT NOT NULL,
                               `Year` INT NOT NULL,
                               `credit_data_value` FLOAT,
                               PRIMARY KEY (`Iso_code`, `Year`)
);
----
CREATE TABLE `estimatedGNiFemale` (
                                      `Iso_code` INT NOT NULL,
                                      `Year` INT NOT NULL,
                                      `GNi_Female_Value` FLOAT,
                                      PRIMARY KEY (`Iso_code`, `Year`)
);
----
CREATE TABLE `estimatedGNiMale` (
                                    `Iso_code` INT NOT NULL,
                                    `Year` INT NOT NULL,
                                    `GNi_Male_Value` FLOAT,
                                    PRIMARY KEY (`Iso_code`, `Year`)
);
----
CREATE TABLE `GDPperCapita` (
                                `Iso_code` INT NOT NULL,
                                `Year` INT NOT NULL,
                                `Gdp_Per_Capita_Value` FLOAT,
                                PRIMARY KEY (`Iso_code`, `Year`)
);
----
CREATE TABLE `GDPTotal` (
                            `Iso_code` INT NOT NULL,
                            `Year` INT NOT NULL,
                            `Gdp_Total_Value` FLOAT,
                            PRIMARY KEY (`Iso_code`, `Year`)
);
----
CREATE TABLE `GNIPerCapita` (
                                `Iso_code` INT NOT NULL,
                                `Year` INT NOT NULL,
                                `Gni_Per_Capita_Value` FLOAT,
                                PRIMARY KEY (`Iso_code`, `Year`)
);
----
CREATE TABLE `GrossFixed` (
                              `Iso_code` INT NOT NULL,
                              `Year` INT NOT NULL,
                              `Gross_Fixed_Value` FLOAT,
                              PRIMARY KEY (`Iso_code`, `Year`)
);
----
CREATE TABLE `IncomeIndex` (
                               `Iso_code` INT NOT NULL,
                               `Year` INT NOT NULL,
                               `Income_Index_Value` FLOAT,
                               PRIMARY KEY (`Iso_code`, `Year`)
);
----
CREATE TABLE `LabourOf` (
                            `Iso_code` INT NOT NULL,
                            `Year` INT NOT NULL,
                            `Labour_Of_Value` FLOAT,
                            PRIMARY KEY (`Iso_code`, `Year`)
);
----
CREATE TABLE `life_expectancy` (
                                   `Iso_code` INT NOT NULL,
                                   `Year` INT NOT NULL,
                                   `infant_mortality` FLOAT,
                                   `infant_mortality_male` FLOAT,
                                   `infant_mortality_female` FLOAT,
                                   `life_expectancy` FLOAT,
                                   `life_expectancy_male` FLOAT,
                                   `life_expectancy_female` FLOAT,
                                   `mortality_rate_under5` FLOAT,
                                   `mortality_rate_under5_male` FLOAT,
                                   `mortality_rate_under5_female` FLOAT,
                                   `mortality_rate_1to4` FLOAT,
                                   `mortality_rate_1to4_male` FLOAT,
                                   `mortality_rate_1to4_female` FLOAT,
                                   PRIMARY KEY (`Iso_code`, `Year`)
);
----
CREATE TABLE `MidYearpopulation` (
                                     `Iso_code` INT NOT NULL,
                                     `Year` INT NOT NULL,
                                     `midYear_population` BIGINT,
                                     PRIMARY KEY (`Iso_code`, `Year`)
);
----
CREATE TABLE `MidYear5Yearagesex` (
                                      `Iso_code` INT NOT NULL,
                                      `Year` INT NOT NULL,
                                      `total_flag` VARCHAR(20),
                                      `starting_age` INT NOT NULL,
                                      `age_group_indicator` VARCHAR(20),
                                      `ending_age` INT NOT NULL,
                                      `midyearPopulation` BIGINT,
                                      `midyearPopulation_male` BIGINT,
                                      `midyearPopulation_female` BIGINT,
                                      PRIMARY KEY (`Iso_code`, `Year`, `starting_age`, `ending_age`)
);
----
CREATE TABLE `MidYearPopulationAgeSex` (
                                           `Iso_code` INT NOT NULL,
                                           `Year` INT NOT NULL,
                                           `sex` VARCHAR(10) NOT NULL,
                                           `age` INT NOT NULL,
                                           `population` BIGINT,
                                           PRIMARY KEY (`Iso_code`, `Year`, `sex`, `age`)
);

----
CREATE TABLE `BigPopbyAge` (
                                           `Iso_code` INT NOT NULL,
                                           `Year` INT NOT NULL,
                                           `sex` VARCHAR(10) NOT NULL,
                                           `maxAge` VARCHAR(10) NOT NULL,
                                           `age` INT NOT NULL,
                                           `populationBig` BIGINT,
                                           PRIMARY KEY (`Iso_code`, `Year`, `sex`, `age`)
);
----
CREATE TABLE `MidYearPopulationAgeSexMaleFemaleTotal` (
                                                          `Iso_code` INT NOT NULL,
                                                          `Year` INT NOT NULL,
                                                          `midYearPopulation` BIGINT,
                                                          `midYear_population_male` BIGINT,
                                                          `midYear_population_female` BIGINT,
                                                          PRIMARY KEY (`Iso_code`, `Year`)
);
----
ALTER TABLE `fertility_rates` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `BirtDeathGrowth` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `credit_data` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `estimatedGNiFemale` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `estimatedGNiMale` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `GDPperCapita` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `GDPTotal` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `GNIPerCapita` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `GrossFixed` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `IncomeIndex` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `LabourOf` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `life_expectancy` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `MidYearpopulation` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `MidYear5Yearagesex` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `MidYearPopulationAgeSex` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `BigPopbyAge` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
ALTER TABLE `MidYearPopulationAgeSexMaleFemaleTotal` ADD FOREIGN KEY (`Iso_code`) REFERENCES `countries` (`iso_code`);
----
