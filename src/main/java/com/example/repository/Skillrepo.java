package com.example.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.entity.Skills;


public interface Skillrepo extends JpaRepository<Skills, Integer>{
	
	Optional<Skills> findBySkillname(String skillname);
	
	   List<Skills> findByDomain(String domain);

	    List<Skills> findByDomainAndSubdomain(String domain, String subdomain);

	    @Query("SELECT DISTINCT s.domain FROM Skills s")
	    List<String> findDistinctDomains();

	    @Query("SELECT DISTINCT s.subdomain FROM Skills s WHERE s.domain = :domain")
	    List<String> findSubdomainsByDomain(String domain);
	    
	    @Query("SELECT s.skillname FROM Skills s WHERE s.subdomain = ?1")
	    List<String> findSkillNamesBySubdomain(String subdomain);
	    
	    
	    Optional<Skills> findBySkillnameAndDomainAndSubdomain(String skillname, String domain, String subdomain);
	    List<Skills> findByDomainAndSubdomainAndSkillname(String domain, String subdomain, String skillname);

	    
	 // Custom query method to find skills by domain and subdomain (case-insensitive)
	    List<Skills> findByDomainAndSubdomainIgnoreCase(String domain, String subdomain);
	    
	    boolean existsByDomainIgnoreCase(String domain);

		List<Skills> findBySubdomain(String selectedSubdomain);


	    

	    
	    
	    



}
