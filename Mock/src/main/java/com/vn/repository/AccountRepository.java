package com.vn.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vn.entity.Account;
import com.vn.entity.Role;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{
	@Query("SELECT a FROM Account a WHERE a.username = ?1")
	Account findByUsername(String userName);
	
	@Query("Select a from Account a where a.fullName like %:fullName%")
	Page<Account> findByNameLike(String fullName, Pageable pageable);
	
	@Query("Select a from Account a where a.role = ?1")
	Page<Account> findByRole(Role role, Pageable pageable);
}

