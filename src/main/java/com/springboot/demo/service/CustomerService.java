package com.springboot.demo.service;

import java.util.List;
import com.springboot.demo.dto.CustomerDto;

public interface CustomerService {
	
	public abstract List<CustomerDto> getCustomers();

	public abstract CustomerDto getCustomer(int paramInt);

	public abstract int addCustomer(CustomerDto paramCustomerDto);

	public abstract void deleteCustomer(CustomerDto paramCustomerDto);

	public abstract void updateCustomer(int paramInt, CustomerDto paramCustomerDto);
	
}
