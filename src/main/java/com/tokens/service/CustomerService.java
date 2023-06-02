package com.tokens.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tokens.models.CustomerDto;
import com.tokens.models.User;
import com.tokens.repository.UserRepository;

@Service
public class CustomerService {

	Logger logger = LoggerFactory.getLogger(CustomerService.class);

	@Autowired
	EntityManager entityManager;

	@Autowired
	UserRepository userRepository;

	public List<CustomerDto> getTopCustomer(int userId) {
		List<CustomerDto> customerList = new ArrayList<>();
		String sql = "SELECT tc.customer_id as customer_id, SUM(tc.amount) as total_amount FROM transaction tc"
				+ " WHERE tc.user_id = :user_id GROUP BY tc.customer_id ORDER BY total_amount DESC";
		try {
			customerList = entityManager.createNativeQuery(sql, "CustomerDtoMapping")
					.setParameter("user_id", userId).setMaxResults(5).getResultList();

		} catch (Exception ex) {
			logger.error("Exception while getting Top Customers: " + ex.getMessage());
		}

		return customerList;
	}

}
