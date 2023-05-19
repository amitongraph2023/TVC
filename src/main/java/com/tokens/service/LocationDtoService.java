package com.tokens.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokens.models.LocationDto;
import com.tokens.models.User;
import com.tokens.repository.UserRepository;

@Service
public class LocationDtoService {

	Logger logger = LoggerFactory.getLogger(LocationDtoService.class);

	@Autowired
	EntityManager entityManager;

	@Autowired
	UserRepository userRepository;

	public List<LocationDto> getAmountPerLocation(int userId) {
		User user = userRepository.findById(userId).get();
		List<LocationDto> locationList = new ArrayList<>();
		String sql = "SELECT tc.merchant_id as merchant_id, SUM(tc.amount) as total_amount FROM transaction tc"
				+ " WHERE tc.system_id = :systemId GROUP BY tc.merchant_id ORDER BY total_amount DESC";
		try {
			locationList = entityManager.createNativeQuery(sql, "LocationDtoMapping")
					.setParameter("systemId", user.getSystemId()).getResultList();

		} catch (Exception ex) {
			logger.error("Exception while getting Top Customers: " + ex.getMessage());
		}

		return locationList;
	}
	
}
