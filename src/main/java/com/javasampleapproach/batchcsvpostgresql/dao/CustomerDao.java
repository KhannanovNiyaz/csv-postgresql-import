package com.javasampleapproach.batchcsvpostgresql.dao;

import java.util.List;

import com.javasampleapproach.batchcsvpostgresql.model.Customer;

public interface CustomerDao {
	public void insert(List<? extends Customer> customers);
	List<Customer> loadAllCustomers();
}
