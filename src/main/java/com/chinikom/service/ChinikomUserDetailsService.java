package com.chinikom.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.chinikom.dao.jpa.ChinikomUserDetailsRepository;
import com.chinikom.domain.UserDetails;

/*
 * Service class to do CRUD for User and Address through JPS Repository
 */
@Service
public class ChinikomUserDetailsService {

	private static final Logger log = LoggerFactory
			.getLogger(ChinikomUserDetailsService.class);

	@Autowired
	private ChinikomUserDetailsRepository userRepository;

	@Autowired
	CounterService counterService;

	@Autowired
	GaugeService gaugeService;

	public ChinikomUserDetailsService() {
	}

	public UserDetails createUser(UserDetails user) {
		if (user.getFirstName() != null && user.getLastName() != null) {
			log.info("Customer Name :" + user.getFirstName() + " "
					+ user.getLastName());
			log.info("Customer MArriage Anniversary :"
					+ user.getMarriageAnniversery());
		} else {
			log.info("Customer Name is  null :");
		}

		log.info("User Full Info :" + user.toString());

		log.info("User Marriage Anniversary :" + user.getMarriageAnniversery());

		return userRepository.save(user);
	}

	public UserDetails getUser(long id) {
		return userRepository.findOne(id);
	}

	public void updateUser(UserDetails user) {
		userRepository.save(user);
	}

	public void deleteUser(Long id) {
		userRepository.delete(id);
	}

	public Page<UserDetails> getAllUsersByPage(Integer page, Integer size) {
		Page<UserDetails> pageOfUsers = userRepository.findAll(new PageRequest(
				page, size));
		// example of adding to the /metrics
		if (size > 50) {
			counterService.increment("com.rollingstone.getAll.largePayload");
		}
		return pageOfUsers;
	}

	public List<UserDetails> getAllUsers() {
		Iterable<UserDetails> pageOfUsers = userRepository.findAll();
		List<UserDetails> users = new ArrayList<UserDetails>();
		for (UserDetails user : pageOfUsers) {
			users.add(user);
		}
		log.info("In Real Service getAllAddresses size :" + users.size());

		return users;
	}
}
