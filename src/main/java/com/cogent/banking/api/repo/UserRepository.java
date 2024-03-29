package com.cogent.banking.api.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cogent.banking.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String username);

}
