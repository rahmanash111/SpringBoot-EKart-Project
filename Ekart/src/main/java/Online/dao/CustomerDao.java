package Online.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Online.dto.Customer;
import Online.repositery.CustomerRepo;

@Repository
public class CustomerDao {

	@Autowired
	CustomerRepo repo;
	
	public Customer saveCustomer(Customer customer) {
		return repo.save(customer);
	}
	
	public Customer getCustomerbymail(String mail) {
		return repo.findByEmail(mail);
	}
	
	public Customer getCustomerbyid(int id) {
		Optional<Customer> o=repo.findById(id);
		if(o.isPresent())
			return o.get();
		else
			return null;
	}
	
	public List<Customer> getAllcustomer(){
		return repo.findAll();
	}
	
	public String deleteCustomerbyid(int id) {
		repo.deleteById(id);
		return "Customer details deleted successfully";
	}
	
	public Customer updateCustomer(Customer customer) {
		return repo.save(customer);
	}
}
