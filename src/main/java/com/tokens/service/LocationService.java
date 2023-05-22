package com.tokens.service;

import java.util.List;


import com.tokens.models.Location;

public interface LocationService {

	void addMerchant(Location location)  throws Exception;
	
	List<Location> getLocations();
	
	public void removeMerchantById(int merchantId);
}
