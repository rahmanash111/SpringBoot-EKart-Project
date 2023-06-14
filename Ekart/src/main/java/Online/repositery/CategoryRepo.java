package Online.repositery;

import org.springframework.data.jpa.repository.JpaRepository;

import Online.dto.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	
	public Category findByCategoryName(String categoryName);
	
}
