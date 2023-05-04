package com.tokens.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tokens.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query("Select u from User u where u.userName = :username")
	User findByUserName(@Param("username") String username);
	
	@Query("Select u from User u where u.email = :email")
	Optional<User> findByUserEmail(@Param("email") String email);

}
