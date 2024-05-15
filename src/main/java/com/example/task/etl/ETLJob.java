package com.example.task.etl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.task.models.CustomerDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j

//Task 3
public class ETLJob {

	@Value("${customerAPIURL}")
	private String customerAPIURL;
	@Value("${postmanEchoURL}")
	private String postmanEchoURL;
	
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ObjectMapper objectMapper;

	// (second, minute, hour, day of month, month, day(s) of week) - ETL Job to be run in every 6 hours 
	@Scheduled(cron = "0 0 */6 * * *")
	public void runJob() {
		List<CustomerDto> customers = new ArrayList<>();
		customers = getCustomers();
		
		for(CustomerDto c : customers) {
			 c.setName(c.getFirstName() + " " + c.getSurName());
		}
		
		for(CustomerDto c : customers) {
			postCallPostmanAPI(c);
		}
	}

	public List<CustomerDto> getCustomers() {
		UriComponents getAllAPI = UriComponentsBuilder.fromHttpUrl(customerAPIURL).path("application/customers?status=active").buildAndExpand();
		ResponseEntity<String> response = restTemplate.exchange(getAllAPI.toUriString(), HttpMethod.GET, null, String.class);
		List<CustomerDto> customers = new ArrayList<>();
		try {
			customers = objectMapper.readValue(response.getBody(), new TypeReference<List<CustomerDto>>() {
			});
		}
		catch (Exception e) {
			log.error(e.getLocalizedMessage());
		}
		return customers;
	}
	
	public void postCallPostmanAPI(CustomerDto customer) {
		UriComponents postmanAPI = UriComponentsBuilder.fromHttpUrl(postmanEchoURL).buildAndExpand();
		HttpEntity<?> httpEntity = new HttpEntity<>(customer.toString());
		ResponseEntity<String> response = restTemplate.exchange(postmanAPI.toUriString(), HttpMethod.POST, httpEntity, String.class);
		log.info("Postman API Response - {}", response.getStatusCode());
	}
}