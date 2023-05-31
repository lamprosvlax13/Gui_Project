

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class PivotCSV {
    public static void main(String[] args) throws IOException {
        String pathToFile ="C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\countries.csv";
        String pathToOutputFile = "C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\MapCountriesIsoCode.csv";

        BufferedReader reader = new BufferedReader(new FileReader(pathToFile));
        String[] headers = reader.readLine().split(",");
        Map<String, Map<String, String>> data = new HashMap<>();
        String line;
        ArrayList<String> linesALL = new ArrayList<String>();
        while ((line = reader.readLine()) != null) {
            String[] row = line.split(",");
          
            String isoCode = row[2];
            String Fips =row[3];
            String displayName = row[4];
            String officialName = row[5];
            Map<String, String> rowData = new HashMap<>();
            rowData.put(headers[2], isoCode);
            rowData.put(headers[3], Fips);
            rowData.put(headers[4], displayName);
            rowData.put(headers[5], officialName);
            data.put(isoCode, rowData);
            ///////////newCountryTable
            linesALL.add(line);
            
        }
        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(pathToOutputFile));
        writer.write(headers[2] +","+ headers[3] + "," + headers[4] + "," + headers[5] + "\n");
        for (Map.Entry<String, Map<String, String>> entry : data.entrySet()) {
        	String s=null;
        	if(entry.getValue().get(headers[5]).equals("")) {
        		s="Not found";
        	}else {
        		s=entry.getValue().get(headers[5]);
        	}
            writer.write(entry.getValue().get(headers[2]) + "," +entry.getValue().get(headers[3]) + ","+
                    entry.getValue().get(headers[4]) + "," +
                    s + "\n");
          
        }
        writer.close();
        
        String pathToOutputFile2 = "C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\countriesTable.csv";
        BufferedWriter writerCountries = new BufferedWriter(new FileWriter(pathToOutputFile2));
        String header ="iso_code";
        header = header +","+"ISO,ISO3,FIPS,Display_Name,Official_Name,Capital,Continent,CurrencyCode,CurrencyName,Phone,Region Code,Region Name,Sub-region Code,Sub-region Name,Intermediate Region Code,Intermediate Region Name,Status,Developed or Developing,Small Island Developing States (SIDS),Land Locked Developing Countries (LLDC),Least Developed Countries (LDC),Area_SqKm,Population\n";
        writerCountries.write(header);
        for (String record : linesALL) {
        	
        	String[] tokensInLine =record.split(",");
        	for (int i = 0; i < tokensInLine.length; i++) {
        		   if(tokensInLine[i].equals("")) {
        		      tokensInLine[i] = "Not found";
        		   }
        		}
        	
        	 
        	String iso =tokensInLine[0];
        	String iso3 =tokensInLine[1];
        	String all ="";
        	for(int i=3; i<tokensInLine.length; i++) {
        		all =all+","+tokensInLine[i];
        	}
        	writerCountries.write(tokensInLine[2]+","+iso+","+iso3+all+"\n");
        }
        writerCountries.close();
    }
}
