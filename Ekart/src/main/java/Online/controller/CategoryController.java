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

import Online.dao.CategoryDao;
import Online.dto.Category;
import Online.service.CategoryService;
import Online.util.ResponseStructure;

@RestController
public class CategoryController {

	@Autowired
	CategoryService categoryService;
	@Autowired
	CategoryDao categoryDao;
	
	@PostMapping("savecategory")
	public ResponseEntity<ResponseStructure<Category>> saveCategory(@RequestBody Category category){
		return categoryService.saveCategory(category);
	}
	
	@DeleteMapping("deletecategorybyid")
	public ResponseEntity<ResponseStructure<String>> deleteByid(@RequestParam int id) {
		return categoryService.deleteCategorybyid(id);
	}
	
	@PutMapping("updatecategorybyname")
	public ResponseEntity<ResponseStructure<Category>> updateCategory(@RequestParam String name,@RequestBody Category category){
		return categoryService.updateCategorybyname(name,category);
	}
	
	@GetMapping("getcategorybyid")
	public ResponseEntity<ResponseStructure<Category>> getCategory(@RequestParam int id){
		return categoryService.getCategorybyid(id);
	}
	
	@GetMapping("getallcategory")
	public ResponseEntity<ResponseStructure<List<Category>>> getAllcategory(){
		return categoryService.getAllcategory();
	}
	
	@GetMapping("getcategorybyname")
	public ResponseEntity<ResponseStructure<Category>> getCategorybyname(@RequestParam String name){
		return categoryService.getCategorybyname(name);
	}
}
