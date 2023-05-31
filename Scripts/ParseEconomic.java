
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ParseEconomic {
	public static void main(String[] args) throws IOException {
		// Open the input file
		File inputFile = new File(
				"C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\Income by Country.xlsx");
		FileInputStream fis = new FileInputStream(inputFile);
		// Create a workbook object from the input file
		Workbook workbook = WorkbookFactory.create(fis);
		// Get the first sheet of the workbook
		Sheet sheet = workbook.getSheetAt(8);
		// Get the number of rows and columns in the sheet
		int numRows = sheet.getLastRowNum() - 17; // -human ... region
		int numColumns = sheet.getRow(8).getLastCellNum(); // 3 kai panw -1 kai 8 kanonika
		// Create new file and FileWriter
		File csvFile = new File("C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\MapCountriesIsoCode.csv");
		FileReader fr = new FileReader(csvFile);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		// Create a HashMap to store country names and their corresponding ISO codes
		HashMap<String, String> countryMap = new HashMap<String, String>();
		while ((line = br.readLine()) != null) {
			String[] parts = line.split(",");
			String isoCode = parts[0];
			String displayName = parts[2];
			String officialName = parts[3];
			countryMap.put(isoCode, displayName);
			countryMap.put(officialName, isoCode);
			countryMap.put(displayName, isoCode);
			countryMap.put(displayName + " " + officialName, isoCode); //

		}
		// Write the header row
		FileWriter fw = new FileWriter(
				"C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\DomestivCredits.csv");
		fw.write("Iso_code,Year,value\n");
		// Write the data rows
		int c = 0;
		for (int i = 1; i <= numRows; i++) {
			for (int j = 1; j < numColumns; j++) {
				Cell cellCountry = sheet.getRow(i).getCell(0);
				Cell cellYear = sheet.getRow(0).getCell(j);
				Cell cellValue = sheet.getRow(i).getCell(j);
				String countryName = cellCountry.getStringCellValue();
				int year = (int) cellYear.getNumericCellValue();
				Double value = null;
				boolean needRemove = false;
				switch (cellValue.getCellType()) {
				case NUMERIC:
					value = Double.valueOf(cellValue.getNumericCellValue());
					break;
				default:
					needRemove = true;
					break;
				}

				if (!needRemove) {
					String isoCode = null;
					boolean foundEcactly = false;
					for (String key : countryMap.keySet()) {

						if (key.equals(countryName)) {
							isoCode = countryMap.get(key);
							foundEcactly = true;
						}

					}

					if (foundEcactly == false) {

						boolean result = false;
						for (String key : countryMap.keySet()) {

							result = compareStrings(key, countryName);
							if (result) {
								isoCode = countryMap.get(key);
								break;
							}

						}

					}
					if (isoCode != null) {
						String outputLine = isoCode + "," + year + "," + value + "\n";
						fw.write(outputLine);
					} else {

						if (countryName.equals("Hong Kong; China (SAR)")) {
							//System.out.println("ela");
							isoCode = "344";
						} else {

							double maxSimilarity = Double.MIN_VALUE;
							for (String key : countryMap.keySet()) {
								double similarity = jaccardSimilarity(key, countryName);
								if (similarity > maxSimilarity) {
									maxSimilarity = similarity;
									isoCode = countryMap.get(key);
								}
							}

						}
						String outputLine = isoCode + "," + year + "," + value + "\n";
						fw.write(outputLine);
						c++;
					}

				}
			}
		}
		// Close the FileWriter and BufferedReader
		fw.close();
		br.close();
		//System.out.println(c);
	}

	// Function to calculate Jaccard similarity between two strings
	public static double jaccardSimilarity(String str1, String str2) {
		String[] arr1 = str1.split(" ");
		String[] arr2 = str2.split(" ");
		// System.out.println(arr1);
		// System.out.println(arr2);

		HashSet<String> set1 = new HashSet<String>(Arrays.asList(arr1));
		HashSet<String> set2 = new HashSet<String>(Arrays.asList(arr2));
		int intersection = 0;
		for (String word : set1) {
			if (set2.contains(word)) {
				intersection++;
			}
		}
		int union = set1.size() + set2.size() - intersection;
		return (double) intersection / union;
	}

	public static boolean compareStrings(String str1, String str2) {
		// ��������� �� ��� strings ����� ��� ����� �����������
		String[] list1 = str1.split("\\s+");
		String[] list2 = str2.split("\\s+");

		// ��������� ���� ���������� ()
		for (int i = 0; i < list1.length; i++) {
			list1[i] = list1[i].replaceAll("\\(|\\)", "");
		}
		for (int i = 0; i < list2.length; i++) {
			list2[i] = list2[i].replaceAll("\\(|\\)", "");
		}

		// ��������� ��� ��� �� ���������� strings ��� ������ string �������� ����
		// ������� string
		for (String item : list1) {
			if (!Arrays.asList(list2).contains(item)) {
				return false;
			}
			if (list1.length != list2.length) {
				return false;
			}
		}

		return true;
	}
}
