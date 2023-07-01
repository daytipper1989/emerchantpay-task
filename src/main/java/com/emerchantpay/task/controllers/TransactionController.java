package com.emerchantpay.task.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.emerchantpay.task.dtos.TransactionDto;
import com.emerchantpay.task.services.interfaces.TransactionService;

@RestController
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	@GetMapping("/transactions")
    public ResponseEntity<List<TransactionDto>> getAll() {

        return new ResponseEntity<>(transactionService.getAll(), HttpStatus.OK);
    }
	
	@PostMapping("/apply/transaction")
	public ResponseEntity<TransactionDto> apply(@RequestBody TransactionDto transactionDto) {
        return new ResponseEntity<>(transactionService.apply(transactionDto), HttpStatus.OK);
    }
}
