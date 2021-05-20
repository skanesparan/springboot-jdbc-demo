package com.springboot.demo.controller;

import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.springboot.demo.dto.CustomerDto;
import com.springboot.demo.service.CustomerService;

@RestController
@RequestMapping("demo/v1/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@GetMapping
	public ResponseEntity<List<CustomerDto>> getCustomers() {
		List<CustomerDto> customers = this.customerService.getCustomers();
		return new ResponseEntity(customers, HttpStatus.OK);
	}

	@GetMapping({ "/{id}" })
	public ResponseEntity<Object> getCustomerById(@PathVariable int id) {
		try {
			CustomerDto cutomer = customerService.getCustomer(id);
			return new ResponseEntity(cutomer, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}

	}

	@PostMapping
	public ResponseEntity<Object> addCustomer(@RequestBody CustomerDto customer) {
		if ((customer.getFirstName() == null) || (customer.getLastName() == null) || (customer.getEmail() == null))
			return ResponseEntity.badRequest().body("Please try with valid customer object");
		try {
			int key = customerService.addCustomer(customer);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{first_name}")
					.buildAndExpand(key).toUri();
			return ResponseEntity.created(location).body(location);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error Occured when create the customer");
		}

	}

	@DeleteMapping({ "/{id}" })
	public ResponseEntity<Object> deleteCustomerById(@PathVariable int id) {
		for (CustomerDto cutomer : this.customerService.getCustomers()) {
			if (cutomer.getId() == id) {
				this.customerService.deleteCustomer(cutomer);
				return ResponseEntity.ok().body("Deleted customer for id = " + id);
			}
		}
		return ResponseEntity.badRequest().body("There is no available customer for id = " + id);
	}

	@PutMapping({ "/{id}" })
	public ResponseEntity<Object> putCustomerById(@PathVariable int id, @RequestBody CustomerDto cutomerBody) {
		List<CustomerDto> customers = this.customerService.getCustomers();
		for (CustomerDto cutomer : customers) {
			if (cutomer.getId() == id) {
				this.customerService.updateCustomer(id, cutomerBody);
				return ResponseEntity.ok().body("Updated customer for id = " + id);
			}
		}
		return ResponseEntity.badRequest().body("There are no available customer for id = " + id);
	}

	@PatchMapping({ "/{id}" })
	public ResponseEntity<Object> patchCustomerById(@PathVariable int id, @RequestBody CustomerDto cutomerBody) {
		for (CustomerDto cutomer : this.customerService.getCustomers()) {
			if (cutomer.getId() == id) {
				if (cutomerBody.getId() != 0) {
					cutomer.setId(id);
				}
				if (cutomerBody.getFirstName() != null) {
					cutomer.setFirstName(cutomerBody.getFirstName());
				}
				if (cutomerBody.getEmail() != null) {
					cutomer.setEmail(cutomerBody.getEmail());
				}
				if (cutomerBody.getLastName() != null) {
					cutomer.setLastName(cutomerBody.getLastName());
				}
				this.customerService.updateCustomer(id, cutomer);
				return ResponseEntity.ok().body("Updated customer for id = " + id);
			}
		}
		return ResponseEntity.badRequest().body("There are no available customer for id = " + id);
	}

}
