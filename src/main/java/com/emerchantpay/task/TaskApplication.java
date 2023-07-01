package com.emerchantpay.task;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.emerchantpay.task.services.interfaces.RakeService;
import com.opencsv.exceptions.CsvValidationException;

@SpringBootApplication
public class TaskApplication {
	
	@Autowired
	private RakeService rakeService;

	public static void main(String[] args) {
		SpringApplication.run(TaskApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void runRake() throws CsvValidationException, IOException, IllegalAccessException, InvocationTargetException {
		rakeService.run();
	}
}
