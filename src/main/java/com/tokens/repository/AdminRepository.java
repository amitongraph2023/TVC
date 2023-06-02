package com.tokens.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokens.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer>{

	@Query("Select u from Admin u where u.adminName = :adminName")
	Admin findByAdminName(@Param("adminName") String adminName);
	
	@Query("Select u from Admin u where u.user.userId = :userId")
	Admin findByUserId(@Param("userId") int userId);
	
	@Query("SELECT u FROM Admin u WHERE u.systemId = :systemId AND u.user.userId != :userId")
    List<Admin> findAdminUsersBySystemIdExceptUser(@Param("systemId") String systemId, @Param("userId") int userId);

}
