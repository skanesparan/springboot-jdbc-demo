package com.springboot.demo.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.springboot.demo.constant.SqlQueryConstants;
import com.springboot.demo.dao.mapper.CustomerMapper;
import com.springboot.demo.dto.CustomerDto;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<CustomerDto> getCustomerRecords() {
		return this.jdbcTemplate.query(SqlQueryConstants.GET_CUSTOMERS_QUERY, new CustomerMapper());
	}

	public CustomerDto getCustomer(int id) {
		return (CustomerDto) this.jdbcTemplate.queryForObject(SqlQueryConstants.GET_CUSTOMER_QUERY,
				new CustomerMapper(), new Object[] { id });
	}

	public void addCutomer(CustomerDto cutomer) {
		this.jdbcTemplate.update(SqlQueryConstants.POST_CUSTOMER_QUERY, cutomer.getFirstName(), cutomer.getLastName(),
				cutomer.getEmail());
	}

	public void deleteCustomer(CustomerDto cutomer) {
		this.jdbcTemplate.update(SqlQueryConstants.DELETE_CUSTOMER_QUERY, cutomer.getId());
	}

	public void updateCustomer(int id, CustomerDto cutomer) {
		this.jdbcTemplate.update(SqlQueryConstants.UPDATE_CUSTOMER_QUERY, cutomer.getFirstName(), cutomer.getLastName(),
				cutomer.getEmail(), id);
	}

}
