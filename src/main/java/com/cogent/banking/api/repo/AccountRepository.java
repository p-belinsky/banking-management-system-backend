package com.cogent.banking.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cogent.banking.api.model.Account;

@Repository
public interface AccountRepository extends JpaRepository <Account, Integer>{
	

}
