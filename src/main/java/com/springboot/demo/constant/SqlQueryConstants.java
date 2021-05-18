package com.springboot.demo.constant;

public class SqlQueryConstants {

	private SqlQueryConstants() {

	}

	public static final String GET_CUSTOMERS_QUERY = "SELECT id, first_name, last_name, email FROM customer";
	public static final String GET_CUSTOMER_QUERY = "SELECT id, first_name, last_name, email FROM customer WHERE id = ?";
	public static final String POST_CUSTOMER_QUERY = "INSERT INTO customer ( first_name, last_name, email) VALUES (?,?,?)";
	public static final String DELETE_CUSTOMER_QUERY = "DELETE FROM customer WHERE id = ?";
	public static final String UPDATE_CUSTOMER_QUERY = "UPDATE customer SET first_name = ? , last_name = ? , email = ? WHERE id = ?";
}
