package com.emerchantpay.task.services.implementations;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.emerchantpay.task.dtos.MerchantDto;
import com.emerchantpay.task.models.Merchant;
import com.emerchantpay.task.models.factories.MerchantFactory;
import com.emerchantpay.task.repositories.MerchantRepository;
import com.emerchantpay.task.services.interfaces.RakeService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

@Service
public class RakeServiceImpl implements RakeService {

	@Value("${rake.file.location}")
	private String rakeFileLocation;

	@Autowired
	private MerchantRepository merchantRepository;
	
	@Autowired
	private MerchantFactory merchantFactory;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void run() throws IOException, CsvValidationException, IllegalAccessException, InvocationTargetException {
		
		CSVParser parser = new CSVParserBuilder()
			    .withSeparator(',')
			    .withIgnoreQuotations(true)
			    .build();
	    try (Reader reader = Files.newBufferedReader(Paths.get(rakeFileLocation))) {
	        try (CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).withCSVParser(parser).build();) {
	            String[] line;
	            while ((line = csvReader.readNext()) != null) {
	                MerchantDto merchantDto = MerchantDto.builder()
	                		.name(line[0])
	                		.description(line[1])
	                		.email(line[2])
	                		.status(line[3])
	                		.totalTransactionSum(Double.valueOf(line[4]))
	                		.admin(Boolean.valueOf(line[5]))
	                		.build();
	                Optional<Merchant> merchantOp = merchantRepository.findByEmail(merchantDto.getEmail());
	                Merchant merchant = null;
	                
	                if(merchantOp.isPresent()) {
	                	merchant = merchantOp.get();
	                }
	                
	                if(merchant == null) {
	                	merchant = merchantFactory.getModel(merchantDto);
	                }
	                else {
	                	Long id = merchant.getId();
	                	BeanUtils.copyProperties(merchantDto, merchant);
	                	merchant.setId(id);
	                }
	                merchant.setPassword(passwordEncoder.encode(line[6]));
	                merchantRepository.save(merchant);
	            }
	        }
	    }
	}
}
