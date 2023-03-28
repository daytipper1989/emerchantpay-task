package com.emerchantpay.task.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.emerchantpay.task.dtos.MerchantDto;
import com.emerchantpay.task.services.interfaces.MerchantService;

@RestController
public class MerchantController {
	
	@Autowired
	private MerchantService merchantService;
	
	@GetMapping("/merchants")
    public ResponseEntity<List<MerchantDto>> getAll() {

        return new ResponseEntity<>(merchantService.getAll(), HttpStatus.OK);
    }
	
	@DeleteMapping("/delete/merchant/{id}")
    public ResponseEntity<Long> deleteMerchant(@PathVariable Long id) {

		merchantService.delete(id);
		
		return new ResponseEntity<>(id, HttpStatus.OK);
    }
	
	@PutMapping("/update/merchant")
	public ResponseEntity<MerchantDto> update(@RequestBody MerchantDto merchantDto) {

        return new ResponseEntity<>(merchantService.update(merchantDto), HttpStatus.OK);
    }
}
