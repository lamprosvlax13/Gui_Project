
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class MidYear5 {

	public static void main(String[] args) throws IOException {
		String csvFilePath = "C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\midyear_population_5yr_age_sex.csv";
		String outputTotalFlagFilePath = "C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\output_total_flag.csv";
		String outputNonTotalFlagFilePath = "C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\output_non_total_flag.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
				FileWriter fwTotalFlag = new FileWriter(outputTotalFlagFilePath);
				FileWriter fwNonTotalFlag = new FileWriter(outputNonTotalFlagFilePath)) {

			// ������������ ��� ����� ������ ��� �������� ���� ������� ��� ������
			String header = br.readLine();
			String headerNewFormat = "countryCode,countryName,year,MidYearPopulation,MidYearPopulationMale,MidYearPopulationFemale";
			fwTotalFlag.write(headerNewFormat + "\n");
			fwNonTotalFlag.write(header + "\n");

			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");

				String totalFlag = values[3];
				String starting_age = values[4];

				// ������� �� �� total flag �������� ��� ������ ��� ��������� ������, oti
				// anaferete se synolika ta grafoume se diaforetiko arxeio se sxesh me tis 5eth
				if (totalFlag.equals("*")) {

					String newLineFormat = "";
					newLineFormat =values[0]+","+ values[1] + "," + values[2] + "," + values[7] + "," + values[8] + "," + values[9];

					fwTotalFlag.write(newLineFormat + "\n");
				} else {
					fwNonTotalFlag.write(line + "\n");
				}
			}
			fwTotalFlag.close();
			fwNonTotalFlag.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		File csvFile = new File("C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\MapCountriesIsoCode.csv");
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
			// countryMap.put(isoCode, displayName);
			countryMap.put(officialName, isoCode);
			countryMap.put(displayName, isoCode);
			countryMap.put(fips, isoCode);
			countryMap.put(officialName+" "+displayName, isoCode);
		}

		File inputFile = new File(
				"C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\output_non_total_flag.csv");
		FileInputStream fis = new FileInputStream(inputFile);
		String pathToOutputFile = "C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\midyearpopulation5yragesexWITHOUTONLYTOTAL.csv";
		// Write the header row
		FileWriter fw = new FileWriter(pathToOutputFile);
		fw.write(
				"Iso_code,Year,total_flag,starting_age,age_group_indicator,ending_age,midyear_population,midyear_population_male,midyear_population_female\n");

		FileReader fr2 = new FileReader(inputFile);
		BufferedReader br2 = new BufferedReader(fr2);
		br2.readLine(); // skip first line
		String line2 = "";
		while ((line2 = br2.readLine()) != null) {

			String[] parts = line2.split(",");
			String fips = parts[0];
			String countryName = parts[1];
			String isoCode = null;

			/////////////////////////////////////////

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
					String FieldsValue = "";
					for (int i = 2; i < parts.length - 1; i++) {

						FieldsValue = FieldsValue + parts[i] + ",";
					}
					FieldsValue = FieldsValue + parts[parts.length - 1];
					fw.write(isoCode + "," + FieldsValue + "\n");
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
				if (isoCode != null) {
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToOutputFile, true))) {
						String FieldsValue = "";
						for (int i = 2; i < parts.length - 1; i++) {

							FieldsValue = FieldsValue + parts[i] + ",";
						}
						FieldsValue = FieldsValue + parts[parts.length - 1];
						fw.write(isoCode + "," + FieldsValue + "\n");
					}
				}
				/*else {
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(pathToOutputFile, true))) {
						String FieldsValue = "";
						for (int i = 2; i < parts.length - 1; i++) {

							FieldsValue = FieldsValue + parts[i] + ",";
						}
						FieldsValue = FieldsValue + parts[parts.length - 1];
						fw.write(countryName + "," + FieldsValue + "\n");
					}
					
				}*/
				// afairw dhladh tis metrhseis gia xwres pou den uparxoun sto countries. �����
				// �� ����� �� ��� primary , 9a mporousa na tis pros9etw me eidiko contry_id,kai
				// na tis 3exwrizw me to fips
			}

		}

		// Close the input and output streams
		fis.close();
		fw.close();
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

}
