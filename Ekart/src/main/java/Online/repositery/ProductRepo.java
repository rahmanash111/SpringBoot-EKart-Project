package Online.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import Online.dto.Product;

public interface ProductRepo extends JpaRepository<Product, Integer>{

	public Product findByProductName(String pname);
	
}
