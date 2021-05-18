package com.springboot.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.http.HttpStatus;
import org.skyscreamer.jsonassert.JSONAssert;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.mockito.Mockito;
import java.util.Arrays;
import com.springboot.demo.dto.CustomerDto;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.springboot.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({ SpringExtension.class })
@WebMvcTest({ CustomerController.class })
class CustomerControllerTest
{
    @Autowired
    MockMvc mockMvc;
    @MockBean
    CustomerService customerService;
    
    @Test
    void getAllCustomerTest() throws Exception {
        final List<CustomerDto> listOfCustomers = Arrays.asList(new CustomerDto(1, "Ravi", "Mavi", "mavi@gmail.com"), new CustomerDto(2, "Siva", "God", "god@gmail.com"), new CustomerDto(3, "Then", "When", "when@gmail.com"));
        Mockito.when((Object)this.customerService.getCustomers()).thenReturn((Object)listOfCustomers);
        final String URI = "/demo/v1/customers";
        final RequestBuilder request = (RequestBuilder)MockMvcRequestBuilders.get(URI, new Object[0]).accept(new MediaType[] { MediaType.APPLICATION_JSON });
        final MvcResult result = this.mockMvc.perform(request).andReturn();
        final String expectedResult = this.mapToJson(listOfCustomers);
        final String actualResult = result.getResponse().getContentAsString();
        Assertions.assertEquals((Object)expectedResult, (Object)actualResult);
        ((CustomerService)Mockito.verify((Object)this.customerService, Mockito.times(1))).getCustomers();
    }
    
    @Test
    void getCustomerByIdTest() throws Exception {
        final CustomerDto customer = new CustomerDto(3, "David", "Hons", "david@gmail.com");
        Mockito.when((Object)this.customerService.getCustomer(Mockito.anyInt())).thenReturn((Object)customer);
        final String URI = "/demo/v1/customers/3";
        final RequestBuilder request = (RequestBuilder)MockMvcRequestBuilders.get(URI, new Object[0]).accept(new MediaType[] { MediaType.APPLICATION_JSON });
        final MvcResult result = this.mockMvc.perform(request).andReturn();
        final String expectedResult = this.mapToJson(customer);
        final String actualResult = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(expectedResult, actualResult, false);
    }
    
    @Test
    void addCustomerTest() throws Exception {
        final CustomerDto customer = new CustomerDto(5, "Raj", "Jhon", "jhon@gmail.com");
        final String input = this.mapToJson(customer);
        final String URI = "/demo/v1/customers";
        final RequestBuilder request = (RequestBuilder)MockMvcRequestBuilders.post(URI, new Object[0]).accept(new MediaType[] { MediaType.APPLICATION_JSON }).content(input).contentType(MediaType.APPLICATION_JSON);
        final MvcResult result = this.mockMvc.perform(request).andReturn();
        final MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    }
    
    @Test
    void deleteCustomerTest() throws Exception {
        final List<CustomerDto> listOfCustomers = Arrays.asList(new CustomerDto(1, "Ravi", "Mavi", "mavi@gmail.com"), new CustomerDto(2, "Siva", "God", "god@gmail.com"), new CustomerDto(3, "Then", "When", "when@gmail.com"));
        Mockito.when((Object)this.customerService.getCustomers()).thenReturn((Object)listOfCustomers);
        final String URI = "/demo/v1/customers/3";
        final RequestBuilder request = (RequestBuilder)MockMvcRequestBuilders.delete(URI, new Object[0]).accept(new MediaType[] { MediaType.APPLICATION_JSON });
        final MvcResult result = this.mockMvc.perform(request).andReturn();
        result.getResponse().getContentAsString();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
    
    @Test
    void updateCustomerTest() throws Exception {
        final List<CustomerDto> listOfCustomers = Arrays.asList(new CustomerDto(1, "Ravi", "Mavi", "mavi@gmail.com"), new CustomerDto(2, "Siva", "God", "god@gmail.com"), new CustomerDto(3, "Then", "When", "when@gmail.com"));
        final CustomerDto customer = new CustomerDto(2, "Siva", "Sivan", "god@gmail.com");
        Mockito.when((Object)this.customerService.getCustomers()).thenReturn((Object)listOfCustomers);
        final String URI = "/demo/v1/customers/2";
        final RequestBuilder request = (RequestBuilder)MockMvcRequestBuilders.put(URI, new Object[0]).accept(new MediaType[] { MediaType.APPLICATION_JSON }).content(this.mapToJson(customer)).contentType(MediaType.APPLICATION_JSON);
        final MvcResult result = this.mockMvc.perform(request).andReturn();
        result.getResponse().getContentAsString();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
    
    @Test
    void patchCustomerTest() throws Exception {
        final List<CustomerDto> listOfCustomers = Arrays.asList(new CustomerDto(1, "Ravi", "Mavi", "mavi@gmail.com"), new CustomerDto(2, "Siva", "God", "god@gmail.com"), new CustomerDto(3, "Then", "When", "when@gmail.com"));
        final CustomerDto customer = new CustomerDto(2, "Siva", "Sivan", "god@gmail.com");
        Mockito.when((Object)this.customerService.getCustomers()).thenReturn((Object)listOfCustomers);
        final String URI = "/demo/v1/customers/2";
        final RequestBuilder request = (RequestBuilder)MockMvcRequestBuilders.patch(URI, new Object[0]).accept(new MediaType[] { MediaType.APPLICATION_JSON }).content(this.mapToJson(customer)).contentType(MediaType.APPLICATION_JSON);
        final MvcResult result = this.mockMvc.perform(request).andReturn();
        result.getResponse().getContentAsString();
        Assertions.assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
    }
    
    String mapToJson(final Object object) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }
    
    CustomerDto mapToObject(final String json) throws JsonProcessingException {
        final ObjectMapper objectMapper = new ObjectMapper();
        return (CustomerDto)objectMapper.readValue(json, (Class)CustomerDto.class);
    }
}