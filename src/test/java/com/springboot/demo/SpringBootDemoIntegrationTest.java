package com.springboot.demo;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.springboot.demo.dto.CustomerDto;
import com.springboot.demo.util.TestUtil;

@SpringBootTest(classes = SpringBootDemoApplication.class , webEnvironment = WebEnvironment.RANDOM_PORT)
class SpringBootDemoIntegrationTest extends AbstractTestNGSpringContextTests {
	
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;
	
	private HttpHeaders headers = new HttpHeaders();
	
	private int customerId;
	
	@Test(priority = 0)
	public void testGetAllCustomers() {
		final HttpEntity<CustomerDto> entity = new HttpEntity<>(null, headers);

		final ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/demo/v1/customers"),
				HttpMethod.GET, entity, String.class);

		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}
	
	@Test(priority = 1)
	public void testAddcustomer() {		
		final HttpEntity<CustomerDto> entity = new HttpEntity<>(getTestCustomerDto(), headers);

		final ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/demo/v1/customers/"),
				HttpMethod.POST, entity, String.class);
		
		String[] uriValues = response.getBody().split("/");
		customerId = Integer.parseInt(uriValues[uriValues.length-1].replaceAll("[^\\d.]", ""));
		String actual = response.getHeaders().get(HttpHeaders.LOCATION).get(0);

		assertTrue(actual.contains("/demo/v1/customers/"+customerId));
		
	}
	
	@Test(priority = 2, dependsOnMethods = "testAddcustomer")
	public void testGetCustomer() throws JSONException, JsonProcessingException{
		final HttpEntity<CustomerDto> entity = new HttpEntity<>(null, headers);

		final ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/demo/v1/customers/")+customerId,
				HttpMethod.GET, entity, String.class);

		JSONAssert.assertEquals(TestUtil.mapToJson(getTestCustomerDto()), response.getBody(), false);
	}
	
	@Test(priority = 3, dependsOnMethods = {"testAddcustomer", "testGetCustomer"})
	public void testUpdateCustomer() throws JSONException, JsonProcessingException{
		
		CustomerDto updateCustomer = getTestCustomerDto();
		updateCustomer.setFirstName("UpdateMalan");
		
		HttpEntity<CustomerDto> entity = new HttpEntity<>(updateCustomer, headers);		

		final ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/demo/v1/customers/")+customerId,
				HttpMethod.PUT, entity, String.class);
		
		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
	}
	
	@Test(priority = 4, dependsOnMethods = {"testAddcustomer", "testGetCustomer", "testUpdateCustomer"})
	public void testDeletecustomer() {
		final HttpEntity<CustomerDto> entity = new HttpEntity<>(null, headers);

		final ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/demo/v1/customers/")+customerId,
				HttpMethod.DELETE, entity, String.class);

		assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
		
	}
	
	private CustomerDto getTestCustomerDto() {
		CustomerDto customer = new CustomerDto();
		customer.setId(customerId);
		customer.setFirstName("Malan");
		customer.setLastName("Mark");
		customer.setEmail("mark@gmail.com");
		return customer;
	}
	
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}
}
