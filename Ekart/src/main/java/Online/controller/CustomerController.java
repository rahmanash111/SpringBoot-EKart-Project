package Online.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Online.dto.Customer;
import Online.service.CustomerService;
import Online.util.ResponseStructure;

@RestController
public class CustomerController {

	@Autowired
	CustomerService service;
	
	@PostMapping("savecustomer")
	public ResponseEntity<ResponseStructure<Customer>> saveCustomer(@RequestBody Customer c){
		return service.saveCustomer(c);
	}
	
	@GetMapping("getcustomerbyid")
	public ResponseEntity<ResponseStructure<Customer>> getCustomerbyid(@RequestParam int id){
		return service.getCustomerbyid(id);
	}
	
	@GetMapping("getcustomerbymail")
	public ResponseEntity<ResponseStructure<Customer>> getCustomerbymail(@RequestParam String mail){
		return service.getCustomerbymail(mail);
	}
	
	@GetMapping("getallcustomer")
	public ResponseEntity<ResponseStructure<List<Customer>>> getallcustomer(){
		return service.getAllcustomer();
	}
	
	@DeleteMapping("deletecustomerbyid")
	public ResponseEntity<ResponseStructure<String>> deleteCustomerbyid(@RequestParam int id){
		return service.deleteCustomerbyid(id);
	}
	
	@PutMapping("updatecustomerbyid")
	public ResponseEntity<ResponseStructure<Customer>> updateCustomerbyid(@RequestParam int id,@RequestBody Customer customer){
		return service.updateCustomerbyid(id, customer);
		
	}
}
