package com.chinikom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsEventListener implements
		ApplicationListener<ServiceEvent> {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onApplicationEvent(ServiceEvent event) {
		// ServiceEvent customeEvent = (ServiceEvent) event;
		logger.info("User Detail " + event.getEventType() + " with details : "
				+ event.getEventUser());
	}
}
