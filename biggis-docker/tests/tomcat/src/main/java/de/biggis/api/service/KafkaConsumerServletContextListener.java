package de.biggis.api.service;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class KafkaConsumerServletContextListener implements ServletContextListener {

	private SimpleKafkaConsumer myThread = null;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if ((myThread == null) || (!myThread.isAlive())) {
			try {
				myThread = new SimpleKafkaConsumer("test");
			} catch (IOException e) {
				e.printStackTrace();
			}
			myThread.start();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			myThread.shutdown();
		} catch (Exception ex) {
		}
	}

}
