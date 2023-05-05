package com.tokens.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.tokens.models.Location;

@Service
public class TvsCsvReader {

	public List<Location> saveLocations() {

		// change the path
		String csvFilePath = "C:/Users/yaman/OneDrive/Desktop/merchant.csv";

//		Map<String, String> mapping = new HashMap<String, String>();
//		mapping.put("merchantId", "merchantId");
//		mapping.put("merchantName", "merchantName");
//
//		HeaderColumnNameTranslateMappingStrategy<Location> strategy = new HeaderColumnNameTranslateMappingStrategy<Location>();
//		strategy.setType(Location.class);
//		strategy.setColumnMapping(mapping);
//
//		CSVReader csvReader = null;
//		try {
//			csvReader = new CSVReader(new FileReader(csvFilePath));
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//		CsvToBean csvToBean = new CsvToBean();
//
//		List<Location> list = csvToBean.parse(strategy, csvReader);
		List<Location> list = new ArrayList<>();
		 try (CSVReader csvReader = new CSVReader(new FileReader(csvFilePath))) {
	            String[] headers = csvReader.readNext(); // read the header row
	            String[] row;
	            while ((row = csvReader.readNext()) != null) {
	                Location location = new Location();
	                location.setMerchantId(Integer.parseInt(row[0]));
	                location.setMerchantName(row[1]);
	                list.add(location);
	            }
	        } catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		return list;
	}
}
