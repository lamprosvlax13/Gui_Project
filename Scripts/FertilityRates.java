
/*
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FertillityRates {
	public static void main(String[] args) throws IOException {

		String pathToOutputFile = "C:\\Users\\Hacknet\\eclipse-workspace\\Scripts\\src\\archive\\AgeFertility_rates.csv";

		File csvFile = new File("C:\\Users\\Hacknet\\eclipse-workspace\\Scripts\\src\\archive\\newCountries.csv");
		FileReader fr = new FileReader(csvFile);
		BufferedReader br = new BufferedReader(fr);
		String line = "";
		// Create a HashMap to store country names and their corresponding ISO codes
		HashMap<String, String> countryMap = new HashMap<String, String>();
		while ((line = br.readLine()) != null) {
			String[] parts = line.split(",");
			String isoCode = parts[0];
			String fips = parts[1];
			String displayName = parts[2];
			String officialName = parts[3];
			countryMap.put(isoCode, displayName);
			countryMap.put(officialName, isoCode);
			countryMap.put(displayName, isoCode);
			
		}

		File inputFile = new File(
				"C:\\Users\\Hacknet\\eclipse-workspace\\Scripts\\src\\archive\\age_specific_fertility_rates.csv");
		FileInputStream fis = new FileInputStream(inputFile);

		// Write the header row
		FileWriter fw = new FileWriter(pathToOutputFile);
		fw.write("Iso_code,Year,fertility_rate_15_19,fertility_rate_20_24,fertility_rate_25_29,fertility_rate_30_34,fertility_rate_35_39,fertility_rate_40_44,fertility_rate_45_49,total_fertility_rate,gross_reproduction_rate,sex_ratio_at_birth\n");

		FileReader fr2 = new FileReader(inputFile);
		BufferedReader br2 = new BufferedReader(fr2);
		br2.readLine(); // skip first line
		String line2 = "";
		while ((line2 = br2.readLine()) != null) {

			String[] parts = line2.split(",");
			String countryName = parts[1];
			String year = parts[2];
			;
			/////

			String isoCode = null;
			double minDistance = Double.MAX_VALUE;
			for (String key : countryMap.keySet()) {
				double distance = levenshteinDistance(key, countryName);
				if (distance < minDistance) {
					minDistance = distance;
					System.out.println(key);
					isoCode = countryMap.get(key);
				}
			}
			String FieldsValue="";
			for(int i=3; i< parts.length-1; i++) {
				
				FieldsValue=FieldsValue + parts[i]+",";
			}
			FieldsValue =FieldsValue + parts[parts.length-1];
			fw.write(isoCode + "," + year + ","+ FieldsValue+"\n");

			////
		}

		// Close the input and output streams
		fis.close();
		fw.close();
	}

	// Calculate the Levenshtein distance between two strings
	private static int levenshteinDistance(String s, String t) {
		int m = s.length();
		int n = t.length();
		int[][] dp = new int[m + 1][n + 1];
		for (int i = 0; i <= m; i++) {
			for (int j = 0; j <= n; j++) {
				if (i == 0) {
					dp[i][j] = j;
				} else if (j == 0) {
					dp[i][j] = i;
				} else {
					int substitutionCost = s.charAt(i - 1) == t.charAt(j - 1) ? 0 : 1;
					dp[i][j] = Math.min(dp[i - 1][j] + 1,
							Math.min(dp[i][j - 1] + 1, dp[i - 1][j - 1] + substitutionCost));
				}
			}
		}
		return dp[m][n];
	}

}*/

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class FertilityRates {
	public static void main(String[] args) throws IOException {
		String pathToOutputFile = "C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\AgeFertility_rates.csv";

		// Create a HashMap to store country names and their corresponding ISO codes
		HashMap<String, String> countryMap = new HashMap<String, String>();
		try (BufferedReader br = new BufferedReader(
				new FileReader("C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\MapCountriesIsoCode.csv"))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				String isoCode = parts[0];
				String fips = parts[1];
				String displayName = parts[2];
				String officialName = parts[3];
				// countryMap.put(isoCode, displayName);
				countryMap.put(officialName, isoCode);
				countryMap.put(displayName, isoCode);
				countryMap.put(fips, isoCode);
				countryMap.put(officialName+" "+displayName, isoCode);

			}
		}

		// Write the header row
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToOutputFile))) {
			bw.write(
					"Iso_code,Year,fertility_rate_15_19,fertility_rate_20_24,fertility_rate_25_29,fertility_rate_30_34,fertility_rate_35_39,fertility_rate_40_44,fertility_rate_45_49,total_fertility_rate,gross_reproduction_rate,sex_ratio_at_birth\n");
		}

		// Read and process the input file
		try (BufferedReader br = new BufferedReader(new FileReader(
				"C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\age_specific_fertility_rates.csv"))) {
			// Skip the first line (header row)
			br.readLine();
			String line;
			while ((line = br.readLine()) != null) {
				String[] parts = line.split(",");
				String fips = parts[0];
				String countryName = parts[1];
				String year = parts[2];
				String isoCode = null;
				

				boolean foundExactly = false;
				// Fips idio
				for (String key : countryMap.keySet()) {

					if (fips.equals(key)) {
						isoCode = countryMap.get(key);
						foundExactly = true;
						break;
					}
				}

				// an to ena periexei ola ta string tou allou
				if (foundExactly == false) {

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
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToOutputFile, true))) {
						bw.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", isoCode, year, parts[3],
								parts[4], parts[5], parts[6], parts[7], parts[8], parts[9], parts[10], parts[11],
								parts[12]));
						//continue;
					}
				} else {
					if (countryName.equals("Hong Kong; China (SAR)")) {
						System.out.println("ela");
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
					if(isoCode!=null) {
						try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToOutputFile, true))) {
							bw.write(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s,%s\n", isoCode, year, parts[3],
									parts[4], parts[5], parts[6], parts[7], parts[8], parts[9], parts[10], parts[11],
									parts[12]));
						}
					}
					//afairw dhladh tis metrhseis gia xwres pou den uparxoun sto countries. ����� �� ����� �� ��� primary , 9a mporousa na tis pros9etw me eidiko contry_id,kai na tis 3exwrizw me to fips 
				}

			}

		}
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

	    // ��������� ��� ��� �� ���������� strings ��� ������ string �������� ���� ������� string
	    for (String item : list1) {
	        if (!Arrays.asList(list2).contains(item)) {
	            return false;
	        }
	        if(list1.length!=list2.length) {
	        	return false;
	        }
	    }
	   
	    return true;
	}
	// Function to calculate Jaccard similarity between two strings
		public static double jaccardSimilarity(String str1, String str2) {
			String[] arr1 = str1.split(" ");
			String[] arr2 = str2.split(" ");
			//System.out.println(arr1);
			//System.out.println(arr2);
			
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
    }

