package com.springboot.demo.controller;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.http.HttpStatus;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testng.annotations.Test;
import org.mockito.Mockito;
import static org.testng.Assert.assertEquals;
import java.util.Arrays;
import com.springboot.demo.dto.CustomerDto;
import com.springboot.demo.service.CustomerService;
import com.springboot.demo.util.TestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;


@WebMvcTest({ CustomerController.class })
@TestExecutionListeners(MockitoTestExecutionListener.class)
class CustomerControllerTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	CustomerService customerService;

	@Test
	void getAllCustomerTest() throws Exception {
		final List<CustomerDto> listOfCustomers = getCustomerList();
		Mockito.when(customerService.getCustomers()).thenReturn(listOfCustomers);
		final String URI = "/demo/v1/customers";
		final RequestBuilder request = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(request).andReturn();
		final String expectedResult = TestUtil.mapToJson(listOfCustomers);
		final String actualResult = result.getResponse().getContentAsString();
		assertEquals(expectedResult, actualResult);
	}

	@Test
	void getCustomerByIdTest() throws Exception {
		final CustomerDto customer = getCustomer();
		Mockito.when(customerService.getCustomer(Mockito.anyInt())).thenReturn(customer);
		final String URI = "/demo/v1/customers/2";
		final RequestBuilder request = MockMvcRequestBuilders.get(URI).accept(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(request).andReturn();
		final String expectedResult = TestUtil.mapToJson(customer);
		final String actualResult = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(expectedResult, actualResult, false);
	}

	@Test
	void addCustomerTest() throws Exception {
		final CustomerDto customer = getCustomer();
		final String input = TestUtil.mapToJson(customer);
		final String URI = "/demo/v1/customers";
		final RequestBuilder request = MockMvcRequestBuilders.post(URI).accept(MediaType.APPLICATION_JSON)
				.content(input).contentType(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(request).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
	}

	@Test
	void deleteCustomerTest() throws Exception {
		final List<CustomerDto> listOfCustomers = getCustomerList();
		Mockito.when(customerService.getCustomers()).thenReturn(listOfCustomers);
		final String URI = "/demo/v1/customers/2";
		final RequestBuilder request = MockMvcRequestBuilders.delete(URI).accept(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(request).andReturn();
		result.getResponse().getContentAsString();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}

	@Test
	void updateCustomerTest() throws Exception {
		final List<CustomerDto> listOfCustomers = getCustomerList();
		final CustomerDto customer = getCustomer();
		Mockito.when(customerService.getCustomers()).thenReturn(listOfCustomers);
		final String URI = "/demo/v1/customers/2";
		final RequestBuilder request = MockMvcRequestBuilders.put(URI).accept(MediaType.APPLICATION_JSON)
				.content(TestUtil.mapToJson(customer)).contentType(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(request).andReturn();
		result.getResponse().getContentAsString();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}

	@Test
	void patchCustomerTest() throws Exception {
		final List<CustomerDto> listOfCustomers = getCustomerList();
		final CustomerDto customer = getCustomer();
		Mockito.when(customerService.getCustomers()).thenReturn(listOfCustomers);
		final String URI = "/demo/v1/customers/2";
		final RequestBuilder request = MockMvcRequestBuilders.patch(URI).accept(MediaType.APPLICATION_JSON)
				.content(TestUtil.mapToJson(customer)).contentType(MediaType.APPLICATION_JSON);
		final MvcResult result = mockMvc.perform(request).andReturn();
		result.getResponse().getContentAsString();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}
	
	private List<CustomerDto> getCustomerList() {
		final List<CustomerDto> listOfCustomers = Arrays.asList(new CustomerDto(1, "Henry", "Vivki", "henry@gmail.com"),
				new CustomerDto(2, "Hod", "Jones", "hod@gmail.com"),
				new CustomerDto(3, "Peter", "James", "peter@gmail.com"));
		return listOfCustomers;
	}
	
	private CustomerDto getCustomer() {
		return new CustomerDto(2, "Mark", "David", "god@gmail.com");
	}
}
