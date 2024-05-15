package com.example.task.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

	private Long customerId;
	private String firstName;
	private String surName;
	private String email;
	private String address;
	private String zipCode;
	private String region;
	private Long orderId;
	private Double orderAmount;
	private LocalDate orderDate;
	private String name;

}
