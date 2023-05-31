
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class MidYearAgeSex {

	public static void main(String[] args) throws IOException {
		String csvFilePath = "C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\midyear_population_age_sex.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
			// �������� ��� ����� ������ ��� �������� ���� ������� ��� ������
			br.readLine();

			// ������������ ��� Map ��� �� ������������� �� ��������
			Map<String, List<PopulationData>> dataMap = new HashMap<>();

			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");

				// ��������� �� �������� ��� �� ������
				String country_code = values[0];
				String country_name = values[1];
				int year = Integer.parseInt(values[2]);
				String sex = values[3];
				int max_age = Integer.parseInt(values[4]);

				// ������������ ��� ����� �� �� �������� ��� �������
				List<Integer> ageDataList = new ArrayList<>();
				for (int i = 5; i <= 105; i++) {
					ageDataList.add(Integer.parseInt(values[i]));
				}

				// ����������� �� �������� ��� Map
				String key = country_code + country_name + year + sex;
				List<PopulationData> dataList = dataMap.getOrDefault(key, new ArrayList<>());
				for (int i = 0; i < ageDataList.size(); i++) {
					int age = i;
					int population = ageDataList.get(i);

					PopulationData data = new PopulationData(country_code, country_name, year, sex, age, population);
					dataList.add(data);
				}
				dataMap.put(key, dataList);
			}
			/////////

			File csvFile = new File("C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\MapCountriesIsoCode.csv");
			FileReader fr = new FileReader(csvFile);
			BufferedReader br2 = new BufferedReader(fr);
			String line2 = "";
			// Create a HashMap to store country names and their corresponding ISO codes
			HashMap<String, String> countryMap = new HashMap<String, String>();
			while ((line2 = br2.readLine()) != null) {
				String[] parts = line2.split(",");
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

	

			// ����������� �� �������� ��� ������ ��� �� ��������������
			String outputFilePath = "C:\\Users\\vlaho\\OneDrive\\Desktop\\prox\\Scripts\\src\\midyearpopulationagesex.csv";
			try (FileWriter writer = new FileWriter(outputFilePath)) {
				writer.write("country_code,year,sex,age,population\n");
				for (List<PopulationData> dataList : dataMap.values()) {
					
				
					
					/*
					String isoCode = null;
					double minDistance = Double.MAX_VALUE;
					for (String key : countryMap.keySet()) {
						double distance = levenshteinDistance(key, dataList.get(0).getCountryName());
						if (distance < minDistance) {
							minDistance = distance;
							System.out.println(key);
							isoCode = countryMap.get(key);
						}
					}
					
					*/
					
					String isoCode = null;
					String fips = dataList.get(0).getCountryCode();
					String countryName =  dataList.get(0).getCountryName();
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
						try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath, true))) {
							for (PopulationData data : dataList) {

								writer.write(String.format("%s,%d,%s,%d,%d\n", isoCode,
										// data.getCountryName(),
										data.getYear(), data.getSex(), data.getAge(), data.getPopulation()));
							}
							
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
							try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFilePath, true))) {
								for (PopulationData data : dataList) {

									writer.write(String.format("%s,%d,%s,%d,%d\n", isoCode,
											// data.getCountryName(),
											data.getYear(), data.getSex(), data.getAge(), data.getPopulation()));
								}
							}
						}
						// afairw dhladh tis metrhseis gia xwres pou den uparxoun sto countries. �����
						// �� ����� �� ��� primary , 9a mporousa na tis pros9etw me eidiko contry_id,kai
						// na tis 3exwrizw me to fips
					}

					
					
				
				
					
					
					
					///////
					
					
					
					
				}
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}



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

///
class PopulationData {
	private String countryCode;
	private String countryName;
	private int year;
	private String sex;
	private int age;
	private int population;

	public PopulationData(String countryCode, String countryName, int year, String sex, int age, int population) {
		this.countryCode = countryCode;
		this.countryName = countryName;
		this.year = year;
		this.sex = sex;
		this.age = age;
		this.population = population;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public String getCountryName() {
		return countryName;
	}

	public int getYear() {
		return year;
	}

	public String getSex() {
		return sex;
	}

	public int getAge() {
		return age;
	}

	public int getPopulation() {
		return population;
	}
}
