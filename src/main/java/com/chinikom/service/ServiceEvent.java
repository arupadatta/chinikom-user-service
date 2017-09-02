package com.chinikom.service;

import org.springframework.context.ApplicationEvent;

import com.chinikom.domain.UserDetails;

/**
 * This is an optional class used in publishing application events. This can be
 * used to inject events into the Spring Boot audit management endpoint.
 */
public class ServiceEvent extends ApplicationEvent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	UserDetails eventUser;
	String eventType;

	public ServiceEvent(Object source) {
		super(source);
	}

	@Override
	public String toString() {
		return "My UserDetailService Event";
	}

	public UserDetails getEventUser() {
		return eventUser;
	}

	public void setEventUser(UserDetails eventUser) {
		this.eventUser = eventUser;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public ServiceEvent(Object source, UserDetails eventUser, String eventType) {
		super(source);
		this.eventUser = eventUser;
		this.eventType = eventType;
	}

}
