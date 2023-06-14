package Online.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import Online.dto.Products;

public interface ProductsRepo extends JpaRepository<Products, Integer> {

	public Products findProductsByName(String name);
}
