package com.example.task.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.task.models.Customer;
import com.example.task.models.CustomerDto;
import com.example.task.models.Order;
import com.example.task.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerService {

	private final CustomerRepository customerRepository;

	public List<CustomerDto> getAllCustomers() throws Exception{

		List<Customer> customersList = customerRepository.findAll();
		if (customersList.isEmpty()) {
			throw new Exception("No data found");
		}
		return prepareListDto(customersList);
	}
	
	public List<CustomerDto> getCustomersByStatus(String status) throws Exception{

		List<Customer> customersList = customerRepository.findByStatus(status);
		if (customersList.isEmpty()) {
			throw new Exception("No data found");
		}
		return prepareListDto(customersList);
	}
	
	public Customer getCustomerById(Long customerId) throws Exception {
		Optional<Customer> customer = customerRepository.findById(customerId);
		if (customer.isEmpty()) {
			throw new Exception("No customer found with id - " + customerId);
		}
		
		return customer.get();
	}

	private List<CustomerDto> prepareListDto(List<Customer> customers) {
		List<CustomerDto> allCustomers = new ArrayList<>();
		customers.forEach(customer -> {
			List<Order> orders = customer.getOrders();
			orders.forEach(order -> {
				CustomerDto c = CustomerDto.builder()
						.customerId(customer.getCid())
						.firstName(customer.getFirstname())
						.surName(customer.getSurname())
						.email(customer.getEmail())
						.region(customer.getRegion())
						.address(customer.getAddress())
						.zipCode(customer.getZipcode())
						.orderAmount(order.getAmount())
						.orderDate(order.getDate())
						.orderId(order.getOrderid())
						.build();
				
				allCustomers.add(c);
			});
			
		});
		return allCustomers;
	}
	
}
