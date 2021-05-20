package com.springboot.demo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import com.springboot.demo.constant.SqlQueryConstants;
import com.springboot.demo.dao.mapper.CustomerMapper;
import com.springboot.demo.dto.CustomerDto;

@Repository
public class CustomerDaoImpl implements CustomerDao {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<CustomerDto> getCustomerRecords() {
		return jdbcTemplate.query(SqlQueryConstants.GET_CUSTOMERS_QUERY, new CustomerMapper());
	}

	public CustomerDto getCustomer(int id) {
		return (CustomerDto) jdbcTemplate.queryForObject(SqlQueryConstants.GET_CUSTOMER_QUERY, new CustomerMapper(),
				id);
	}

	public int addCutomer(CustomerDto cutomer) {
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
		    @Override
		    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		        PreparedStatement statement = con.prepareStatement(SqlQueryConstants.POST_CUSTOMER_QUERY, Statement.RETURN_GENERATED_KEYS);
		        statement.setString(1, cutomer.getFirstName());
		        statement.setString(2, cutomer.getLastName());
		        statement.setString(3, cutomer.getEmail());
		        return statement;
		    }
		}, holder);
		Number key = holder.getKey();
		return key != null ? key.intValue() : 0;
	}

	public void deleteCustomer(CustomerDto cutomer) {
		jdbcTemplate.update(SqlQueryConstants.DELETE_CUSTOMER_QUERY, cutomer.getId());
	}

	public void updateCustomer(int id, CustomerDto cutomer) {
		jdbcTemplate.update(SqlQueryConstants.UPDATE_CUSTOMER_QUERY, cutomer.getFirstName(), cutomer.getLastName(),
				cutomer.getEmail(), id);
	}
	
	private int test() {
		return 5;

	}

}
