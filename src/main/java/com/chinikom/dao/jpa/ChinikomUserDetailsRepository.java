package com.chinikom.dao.jpa;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.chinikom.domain.UserDetails;

public interface ChinikomUserDetailsRepository extends
		PagingAndSortingRepository<UserDetails, Long> {
	UserDetails findUserByDateOfBirth(Date dateOfBirth);

	@Override
	Page<UserDetails> findAll(Pageable pageable);

}
