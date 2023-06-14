
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
import Online.dao.ProductDao;
import Online.dto.Category;
import Online.dto.Product;
import Online.util.ResponseStructure;

@Service
public class ProductService {

	@Autowired
	ProductDao productDao;
	@Autowired
	CategoryDao categoryDao;

	public ResponseEntity<ResponseStructure<Product>> saveProduct(int id, Product product) {

		Category category = categoryDao.getCategorybyid(id);

		if (category != null) {

			Product p = productDao.getProductbyname(product.getProductName());
			if (p == null) {
				List<Product> plist = category.getProducts();
			//	System.out.println(plist);
//				if(plist==null) {
//					plist=new ArrayList<Product>();
//				}
				product.setCategory(category);
		//		System.out.println(category);
				plist.add(product);
			//	System.out.println(plist);
				// product.setCategory(category);
				category.setProducts(plist);

				ResponseStructure<Product> structure = new ResponseStructure<>();
				ResponseEntity<ResponseStructure<Product>> entity = new ResponseEntity<>(structure, HttpStatus.CREATED);

				structure.setMessage("Product saved");
				structure.setStatuscode(HttpStatus.CREATED.value());
				structure.setData(categoryDao.saveCategory(category).getProducts().get(plist.size() - 1));

				return entity;

			} else
				throw new DuplicateDataException("Product with the same name already present in this category");

		} else {
			throw new IdNotFoundException("No category present in the given id");
		}

	}

	public ResponseEntity<ResponseStructure<Product>> getProductbyid(int id) {

		Product p = productDao.getProductbyid(id);

		if (p != null) {
			ResponseStructure<Product> structure = new ResponseStructure<>();
			ResponseEntity<ResponseStructure<Product>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

			structure.setMessage("Product Details");
			structure.setStatuscode(HttpStatus.OK.value());
			structure.setData(p);

			return entity;
		} else {
			throw new IdNotFoundException("Product not found with the given Id");
		}
	}

	public ResponseEntity<ResponseStructure<String>> deleteProductbyid(int id) {

		Product p = productDao.getProductbyid(id);

		if (p != null) {

			ResponseStructure<String> structure = new ResponseStructure<>();

			ResponseEntity<ResponseStructure<String>> entity = new ResponseEntity<>(structure, HttpStatus.NO_CONTENT);

			structure.setStatuscode(HttpStatus.NO_CONTENT.value());
			structure.setMessage("Product deleted successfully");
			structure.setData(productDao.deleteProductbyid(id));

			return entity;
		} else {
			throw new IdNotFoundException("No Product found with the given id");
		}
	}

	public ResponseEntity<ResponseStructure<List<Product>>> getAllProducts() {

		List<Product> pList = productDao.getAllproduct();

		if (!pList.isEmpty()) {

			ResponseStructure<List<Product>> structure = new ResponseStructure<>();

			ResponseEntity<ResponseStructure<List<Product>>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

			structure.setStatuscode(HttpStatus.OK.value());
			structure.setMessage("All Products details");
			structure.setData(pList);

			return entity;
		} else
			throw new NoDataFoundException("There is no category present");
	}

	public ResponseEntity<ResponseStructure<Product>> getProductbyname(String name) {

		Product p = productDao.getProductbyname(name);

		if (p != null) {
			ResponseStructure<Product> structure = new ResponseStructure<>();
			ResponseEntity<ResponseStructure<Product>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

			structure.setStatuscode(HttpStatus.OK.value());
			structure.setMessage("Product Details");
			structure.setData(p);

			return entity;
		} else {
			throw new NoDataFoundException("No Product found with this given name");
		}
	}

	public ResponseEntity<ResponseStructure<Product>> updateProductByname(String name, double price, int qty) {

		Product p = productDao.getProductbyname(name);

		if (p != null) {

			p.setPrice(price);
			p.setQuantity(qty);

			ResponseStructure<Product> structure = new ResponseStructure<>();
			ResponseEntity<ResponseStructure<Product>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

			structure.setStatuscode(HttpStatus.OK.value());
			structure.setMessage("Product Modified Succesfully");
			structure.setData(productDao.updateProductbyname(p));

			return entity;

		} else
			throw new NoDataFoundException("No product available with this name");

	}

}
