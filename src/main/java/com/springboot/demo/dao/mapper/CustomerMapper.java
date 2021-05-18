package com.springboot.demo.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.springboot.demo.dto.CustomerDto;

public class CustomerMapper implements RowMapper<CustomerDto> {

	@Override
	public CustomerDto mapRow(ResultSet rs, int rowNum) throws SQLException {
		CustomerDto customer = new CustomerDto();
		customer.setId(rs.getInt("id"));
		customer.setFirstName(rs.getString("first_name"));
		customer.setLastName(rs.getString("last_name"));
		customer.setEmail(rs.getString("email"));

		return customer;
	}

}
