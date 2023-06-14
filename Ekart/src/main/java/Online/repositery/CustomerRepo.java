package Online.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import Online.dto.Customer;

public interface CustomerRepo extends JpaRepository<Customer, Integer>{

	public Customer findByEmail(String mail);
	
}
