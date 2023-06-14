package Online.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Online.dto.Product;
import Online.repositery.ProductRepo;

@Repository
public class ProductDao {

	@Autowired
	ProductRepo repo;

	public Product saveProduct(Product product) {
		return repo.save(product);
	}

	public Product getProductbyid(int id) {
		Optional<Product> o = repo.findById(id);

		if (o.isPresent()) {
			return o.get();
		} else {
			return null;
		}
	}

	public String deleteProductbyid(int id) {
		repo.deleteById(id);
		return "Product deleted Successfully";
	}
	
	public List<Product> getAllproduct(){
		return repo.findAll();
	}
	
	public Product getProductbyname(String name) {
		return repo.findByProductName(name);
	}

	public Product updateProductbyname(Product product) {
		return repo.save(product);
	}
}
