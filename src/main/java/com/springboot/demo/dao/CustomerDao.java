package com.springboot.demo.dao;

import java.util.List;
import com.springboot.demo.dto.CustomerDto;

public interface CustomerDao {

	public abstract List<CustomerDto> getCustomerRecords();

	public abstract CustomerDto getCustomer(int paramInt);

	public abstract void addCutomer(CustomerDto paramCustomerDto);

	public abstract void deleteCustomer(CustomerDto paramCustomerDto);

	public abstract void updateCustomer(int paramInt, CustomerDto paramCustomerDto);

}
