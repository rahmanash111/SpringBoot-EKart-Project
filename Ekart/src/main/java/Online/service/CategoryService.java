package Online.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import Online.Exception.DuplicateDataException;
import Online.Exception.IdNotFoundException;
import Online.Exception.NoDataFoundException;
import Online.dao.CategoryDao;
import Online.dto.Category;
import Online.dto.Product;
import Online.util.ResponseStructure;

@Service
public class CategoryService {

	@Autowired
	CategoryDao categoryDao;

	public ResponseEntity<ResponseStructure<Category>> saveCategory(Category category) {

		boolean b = true;

		String cName = category.getCategoryName();

		List<Category> list = categoryDao.getAllcategory();

		if (!list.isEmpty() && list.size() > 0) {

			for (int i = 0; i < list.size(); i++) {
				if (list.get(i).getCategoryName().equals(cName)) {
					b = false;
				}
			}

			if (b) {

				ResponseStructure<Category> structure = new ResponseStructure<>();
				ResponseEntity<ResponseStructure<Category>> entity = new ResponseEntity<>(structure,
						HttpStatus.CREATED);

				structure.setMessage("New category created");
				structure.setStatuscode(HttpStatus.CREATED.value());
				structure.setData(categoryDao.saveCategory(category));

				return entity;
			} else
				throw new DuplicateDataException("Category already present in the database");

		} else {

			ResponseStructure<Category> structure = new ResponseStructure<>();
			ResponseEntity<ResponseStructure<Category>> entity = new ResponseEntity<>(structure, HttpStatus.CREATED);

			structure.setMessage("New category created");
			structure.setStatuscode(HttpStatus.CREATED.value());
			structure.setData(categoryDao.saveCategory(category));

			return entity;
		}

	}

	public ResponseEntity<ResponseStructure<String>> deleteCategorybyid(int id) {

		Category c = categoryDao.getCategorybyid(id);

		if (c != null) {

			ResponseStructure<String> structure = new ResponseStructure<>();
			ResponseEntity<ResponseStructure<String>> entity = new ResponseEntity<>(structure, HttpStatus.NO_CONTENT);

			structure.setMessage("Category deleted successfully");
			structure.setStatuscode(HttpStatus.NO_CONTENT.value());
			structure.setData(categoryDao.deleteByid(id));

			return entity;

		} else
			throw new IdNotFoundException();

	}

	public ResponseEntity<ResponseStructure<Category>> updateCategorybyname(String name, Category category) {

		Category c = categoryDao.getCategorybyname(name);

		if (c != null) {

			if(categoryDao.getCategorybyname(category.getCategoryName())==null) {
			
				category.setCategoryId(c.getCategoryId());
				category.setProducts(c.getProducts());

				ResponseStructure<Category> structure = new ResponseStructure<>();
				ResponseEntity<ResponseStructure<Category>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

				structure.setData(categoryDao.updateCategory(category));
				structure.setMessage("Category has been updated");
				structure.setStatuscode(HttpStatus.OK.value());
				return entity;

			}else
				throw new DuplicateDataException("Category is already present with the same name");
					} else
			throw new IdNotFoundException("No Category present in the given name");

	}

	public ResponseEntity<ResponseStructure<Category>> getCategorybyid(int id) {

		Category c = categoryDao.getCategorybyid(id);

		if (c != null) {
			ResponseStructure<Category> structure = new ResponseStructure<>();
			ResponseEntity<ResponseStructure<Category>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

			structure.setStatuscode(HttpStatus.OK.value());
			structure.setMessage("data found");
			structure.setData(c);
			return entity;
		} else
			throw new IdNotFoundException("No Category present in the given id");
	}

	public ResponseEntity<ResponseStructure<List<Category>>> getAllcategory() {

		List<Category> cList = categoryDao.getAllcategory();

		if (cList.isEmpty()) {// the method returns the empty list object so it will not be null instead it's
								// empty.
			throw new NoDataFoundException("No Category Present");

		} else {

			ResponseStructure<List<Category>> structure = new ResponseStructure<>();

			ResponseEntity<ResponseStructure<List<Category>>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

			structure.setStatuscode(HttpStatus.OK.value());
			structure.setMessage("Data Listed");
			structure.setData(cList);

			return entity;
		}
	}

	public ResponseEntity<ResponseStructure<Category>> getCategorybyname(String name) {

		Category c = categoryDao.getCategorybyname(name);

		if (c != null) {
			ResponseStructure<Category> structure = new ResponseStructure<>();

			ResponseEntity<ResponseStructure<Category>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

			structure.setStatuscode(HttpStatus.OK.value());
			structure.setMessage("Data Listed");
			structure.setData(c);
			
			return entity;
		}else
			throw new NoDataFoundException("No category present with the given name");
	}

}
