package com.springboot.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springboot.demo.dao.CustomerDao;
import com.springboot.demo.dto.CustomerDto;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;

	public List<CustomerDto> getCustomers() {
		return this.customerDao.getCustomerRecords();
	}

	public CustomerDto getCustomer(int id) {
		return this.customerDao.getCustomer(id);
	}

	public void addCustomer(CustomerDto cutomer) {
		this.customerDao.addCutomer(cutomer);
	}

	public void deleteCustomer(CustomerDto cutomer) {
		this.customerDao.deleteCustomer(cutomer);
	}

	public void updateCustomer(int id, CustomerDto cutomer) {
		this.customerDao.updateCustomer(id, cutomer);
	}

}
