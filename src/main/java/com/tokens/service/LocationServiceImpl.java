package com.tokens.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokens.models.Location;
import com.tokens.repository.LocationRepository;

@Service
public class LocationServiceImpl implements LocationService{

	Logger logger = LoggerFactory.getLogger(LocationServiceImpl.class);
	
	@Autowired
	LocationRepository locationRepository;
	
	@Override
	public void addMerchant(Location location) throws Exception {
		try {
			locationRepository.save(location);
		} catch (Exception e) {
			throw new Exception("Exception occurred while saving location to DB");
		}
	}

	@Override
	public List<Location> getLocations() {
		List<Location> location = locationRepository.findAll();
		return location;
	}

	@Override
	public void removeMerchantById(int merchantId) {
		locationRepository.deleteById(merchantId);
		
	}


	
}
