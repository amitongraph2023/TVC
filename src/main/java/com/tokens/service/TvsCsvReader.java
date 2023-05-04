package com.tokens.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.tokens.models.Location;

@Service
public class TvsCsvReader {

	public List<Location> saveLocations() {

		// change the path
		String csvFilePath = "C:/Users/yaman/OneDrive/Desktop/merchant.csv";

		Map<String, String> mapping = new HashMap<String, String>();
		mapping.put("merchantId", "merchantId");
		mapping.put("merchantName", "merchantName");

		HeaderColumnNameTranslateMappingStrategy<Location> strategy = new HeaderColumnNameTranslateMappingStrategy<Location>();
		strategy.setType(Location.class);
		strategy.setColumnMapping(mapping);

		// Create csvtobean and csvreader object
		CSVReader csvReader = null;
		try {
			csvReader = new CSVReader(new FileReader(csvFilePath));
		} catch (FileNotFoundException e) {

			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		CsvToBean csvToBean = new CsvToBean();

		// call the parse method of CsvToBean
		// pass strategy, csvReader to parse method
		List<Location> list = csvToBean.parse(strategy, csvReader);
	
		return list;
	}
}
