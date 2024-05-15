package com.example.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	public List<Customer> findByStatus(String status);

}
