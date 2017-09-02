package com.chinikom.api.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.chinikom.domain.UserDetails;
import com.chinikom.exception.HTTP400Exception;
import com.chinikom.service.ChinikomUserDetailsService;
import com.chinikom.service.ServiceEvent;

/*
 * Demonstrates how to set up RESTful API endpoints using Spring MVC
 */
@RestController
@RequestMapping(value = "/chinikom-user-service/v1/users")
public class UserServiceController extends AbstractRestController {

	@Autowired
	private ChinikomUserDetailsService userService;

	@Autowired
	CounterService counterService;

	@RequestMapping(value = "", method = RequestMethod.POST, consumes = {
			"application/json", "application/xml" }, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.CREATED)
	public void createUser(@RequestBody UserDetails user,
			HttpServletRequest request, HttpServletResponse response) {
		UserDetails createdUser = this.userService.createUser(user);
		if (createdUser != null) {
			counterService.increment("com.chinikom.userdetail.created.success");
			eventPublisher.publishEvent(new ServiceEvent(this, createdUser,
					"UserCreated"));
		} else {
			counterService.increment("com.chinikom.userdetail.created.failure");
		}
		response.setHeader("Location", request.getRequestURL().append("/")
				.append(createdUser.getId()).toString());
	}

	@RequestMapping(value = "", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	Page<UserDetails> getAllUsersByPage(
			@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
			@RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			HttpServletRequest request, HttpServletResponse response) {
		return this.userService.getAllUsersByPage(page, size);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	List<UserDetails> getAllUsers(
			@RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
			@RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
			HttpServletRequest request, HttpServletResponse response) {
		return this.userService.getAllUsers();
	}

	@RequestMapping("/simple/{id}")
	public UserDetails getSimpleUser(@PathVariable("id") Long id) {
		UserDetails user = this.userService.getUser(id);
		checkResourceFound(user);
		return user;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.OK)
	public @ResponseBody
	UserDetails getUser(@PathVariable("id") Long id,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		UserDetails user = this.userService.getUser(id);
		checkResourceFound(user);
		return user;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = {
			"application/json", "application/xml" }, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateUser(@PathVariable("id") Long id,
			@RequestBody UserDetails user, HttpServletRequest request,
			HttpServletResponse response) {
		checkResourceFound(this.userService.getUser(id));
		if (id != user.getId())
			throw new HTTP400Exception("ID doesn't match!");
		counterService.increment("com.chinikom.userdetails.updated.success");

		this.userService.updateUser(user);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = {
			"application/json", "application/xml" })
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable("id") Long id,
			HttpServletRequest request, HttpServletResponse response) {
		checkResourceFound(this.userService.getUser(id));
		counterService.increment("com.chinikom.userdetails.deleted.success");
		this.userService.deleteUser(id);
	}
}
