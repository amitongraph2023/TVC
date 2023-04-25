package com.tokens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokens.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Modifying
	@Query("Update User u set u.masterKey = :masterKey where u.userId = :userId")
	Optional<User> addUserMasterKey(@Param("userId") Integer userId,@Param("masterKey") String masterKey);

	User findByUserName(String username);

}
