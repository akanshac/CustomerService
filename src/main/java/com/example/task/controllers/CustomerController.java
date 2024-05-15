package com.example.task.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.task.models.Customer;
import com.example.task.models.CustomerDto;
import com.example.task.service.CustomerService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/application")
@RequiredArgsConstructor
public class CustomerController {
	
	private final CustomerService customerService;

	// Task 2 - Endpoint 1
	@GetMapping("/customers")
	public ResponseEntity<List<CustomerDto>> getAllCustomers(@RequestParam(required = false) String status) throws Exception {

		List<CustomerDto> customersList = null ;
		if(status != null)
			customersList = customerService.getCustomersByStatus(status);
		else
			customersList = customerService.getAllCustomers();
		
		return new ResponseEntity<>(customersList, HttpStatus.OK);

	}
	
	// Task 2 - Endpoint 2
	@GetMapping("/customer")
	public ResponseEntity<Customer> getCustomerById(@RequestParam(required = true) Long customerId) throws Exception {

		Customer customersList = customerService.getCustomerById(customerId);
		return new ResponseEntity<>(customersList, HttpStatus.OK);

	}
}
