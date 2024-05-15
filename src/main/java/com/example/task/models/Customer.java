package com.example.task.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "customer")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

//Task 1
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cid;
	private String firstname;
	private String surname;
	private String email;
	private String address;
	private String zipcode;
	private String region;
	private String status;
	@JoinColumn(name = "customerid", referencedColumnName = "cid")
	@OneToMany(fetch = FetchType.EAGER)
	private List<Order>	orders;
}
