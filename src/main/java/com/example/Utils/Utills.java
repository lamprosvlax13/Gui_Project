package com.example.Utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

public class Utills {

    public static void printQueryResult(Optional<ResultSet> resultSet) throws SQLException {
        ResultSet rs = resultSet.get();
        if(!resultSet.isEmpty()){
            ResultSetMetaData metadata = resultSet.get().getMetaData();
            int columnCount = metadata.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                System.out.println(metadata.getColumnName(i) + ", ");
            }
            System.out.println();
            while (rs.next()) {
                String row = "";
                for (int i = 1; i <= columnCount; i++) {
                    row += rs.getString(i) + ", ";
                }
                System.out.println(row);

            }
        }
    }

    public static List<String[]> readFile(Path pathToFile){

        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {

            String line = br.readLine();
            while (line != null) {

            }

        }catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }


    public static Properties readPropertiesFile() throws IOException {
        FileReader reader=new FileReader("src/main/resources/sqlConnection.properties");
        Properties properties=new Properties();
        properties.load(reader);
        return properties;

    }


}
