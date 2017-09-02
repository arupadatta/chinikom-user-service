package com.chinikom;

import java.util.NoSuchElementException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserRestControllerAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CounterService counterService;

	@Before("execution(public * com.chinikom.api.rest.*Controller.*(..))")
	public void logBeforeRestCall(JoinPoint pjp) throws Throwable {
		logger.info(":::::AOP Before for User REST call:::::" + pjp);
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.createUser*(..))")
	public void afterCallingCreateUser(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Create User REST call:::::" + pjp);
		counterService
				.increment("com.chinikom.api.rest.UserController.createUser");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.getAllUserByPage*(..))")
	public void afterCallingGetAllUserByPage(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Users getAllUsersByPage REST call:::::"
				+ pjp);

		counterService
				.increment("com.chinikom.api.rest.UserController.getAllUsersByPage");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.getAllUser*(..))")
	public void afterCallingGetAllUser(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning User getAllUser REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.UserController.getAllUsers");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.User*(..))")
	public void afterCallingGetUser(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Users getUser REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.UserController.getUser");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.updateUser*(..))")
	public void afterCallingUpdateCustomer(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Users updateUser REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.UserController.updateUser");
	}

	@AfterReturning("execution(public * com.chinikom.api.rest.*Controller.deleteUser*(..))")
	public void afterCallingDeleteUser(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Users deleteUser REST call:::::"
				+ pjp);
		counterService
				.increment("com.chinikom.api.rest.UserController.deleteUser");
	}

	@AfterThrowing(pointcut = "execution(public * com.chinikom.api.rest.*Controller.*(..))", throwing = "e")
	public void afterCustomerThrowsException(NoSuchElementException e) {
		counterService.increment("counter.errors.User.controller");
	}
}
