package Online.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Online.dto.Products;
import Online.repositery.ProductsRepo;

@Repository
public class ProductsDao {

	@Autowired
	ProductsRepo repo;

	public Products save(Products products) {
		return repo.save(products);
	}

	public Products getProductsbyid(int id) {
		Optional<Products> o = repo.findById(id);

		if (o.isPresent())
			return o.get();
		else
			return null;
	}

	public Products getProductsbyname(String name) {
		return repo.findProductsByName(name);
	}

	public String deleteProductsbyid(int id) {
		repo.deleteById(id);
		return "Products deleted successfully";
	}
	
	public List<Products> getAllproducts(){
		return repo.findAll();
	}

	public Products updateProducts(Products products) {
		return repo.save(products);
	}
	
}
