package com.tokens.service;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.tokens.models.Location;

@Service
public class TvsCsvReader {

	public List<Location> saveLocations() {

		// change the path
		String csvFilePath = "merchant.csv";

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
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
		return list;
	}
}
