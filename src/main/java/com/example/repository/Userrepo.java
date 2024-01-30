package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.User;

public interface Userrepo extends JpaRepository<User, String>
{

	User findByEmpemail (String email);
	User findByEmpid (String empid);
	Optional<User> findByName(String name);
	
}
