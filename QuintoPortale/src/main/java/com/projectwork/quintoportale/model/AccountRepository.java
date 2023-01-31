package com.projectwork.quintoportale.model;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface AccountRepository extends CrudRepository<Account, Integer> {
	Optional<Account> findByEmail(String email);

	Optional<Account> findByUsername(String username);
}
