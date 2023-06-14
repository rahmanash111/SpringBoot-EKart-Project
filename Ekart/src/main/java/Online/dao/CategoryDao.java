package Online.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Online.dto.Category;
import Online.repositery.CategoryRepo;

@Repository
public class CategoryDao {

	@Autowired
	CategoryRepo repo;
	
	public Category saveCategory(Category category) {
		return repo.save(category);
	}
	
	public Category getCategorybyid(int id) {
		 Optional<Category> o=repo.findById(id);
		// System.out.println(o);
		 if(o.isPresent()) {
			 return o.get();
		 }else
			 return null;
		
	//	repo.findById(id);
	//	return new Category();
		
	}
	
	public List<Category> getAllcategory(){
		return repo.findAll();
	}
	
	public String deleteByid(int id) {
		repo.deleteById(id);
		return "Deleted";
	}
	
	public Category getCategorybyname(String name) {
	return repo.findByCategoryName(name);
	}
	
	public Category updateCategory(Category category) {
		return repo.save(category);
	}
}
