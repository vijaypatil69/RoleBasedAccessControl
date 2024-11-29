package com.vrvsecurity.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vrvsecurity.enumfileds.Gender;
import com.vrvsecurity.enumfileds.Role;
import com.vrvsecurity.model.User;

import jakarta.transaction.Transactional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	User findByEmailId(String emailId);

	@Query(nativeQuery = true, value = "select email_id from user_detail where email_id = :emailId and password = :password")
	String checkUserPresetOrNot(String emailId, String password);

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO user_detail (first_name, last_name, email_id, mobile_number, password,gender,role) "
			+ "VALUES (:firstName, :lastName, :emailId, :mobileNumber, :password,:gender,:role)", nativeQuery = true)
	void insertUser(@Param("firstName") String firstName, @Param("lastName") String lastName,
			@Param("emailId") String emailId, @Param("mobileNumber") String mobileNumber,
			@Param("password") String password,@Param("gender") Integer gender, @Param("role") Integer role );

}
