package com.emerchantpay.task.services.interfaces;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import com.opencsv.exceptions.CsvValidationException;

public interface RakeService {
	public void run() throws IOException, CsvValidationException, IllegalAccessException, InvocationTargetException;
}
