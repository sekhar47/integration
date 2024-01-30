package com.example.serviceauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import com.example.entity.User;
import com.example.repository.Userrepo;






@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	 @Autowired
	 private Userrepo userRepository;

	@Override
	public UserDetails loadUserByUsername(String empid) throws UsernameNotFoundException {
		User user = userRepository.findByEmpid(empid);
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		
		return new CustomUserDetail(user);

	}

}