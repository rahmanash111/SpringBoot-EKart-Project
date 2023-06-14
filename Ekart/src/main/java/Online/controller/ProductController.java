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

import Online.dto.Product;
import Online.repositery.ProductRepo;
import Online.service.ProductService;
import Online.util.ResponseStructure;

@RestController
public class ProductController {

	@Autowired
	ProductService productService;

	@Autowired
	ProductRepo repo;
	
	@PostMapping("saveproduct")
	public ResponseEntity<ResponseStructure<Product>> saveProduct(@RequestParam int id, @RequestBody Product product) {
		return productService.saveProduct(id, product);
	}

	@GetMapping("getproductbyid")
	public ResponseEntity<ResponseStructure<Product>> getProductbyid(@RequestParam int id,String name) {
		return productService.getProductbyid(id);
	}

	@DeleteMapping("deleteproductbyid")
	public ResponseEntity<ResponseStructure<String>> deleteProductbyid(@RequestParam int id) {
		return productService.deleteProductbyid(id);
	}

	@GetMapping("getallproduct")
	public ResponseEntity<ResponseStructure<List<Product>>> getAllproducts() {
		return productService.getAllProducts();
	}
	
	@GetMapping("getproductbyname")
	public ResponseEntity<ResponseStructure<Product>> getProductbyname(@RequestParam String name){
		return productService.getProductbyname(name);
	}
	
	@PutMapping("updateproductbyname")
	public ResponseEntity<ResponseStructure<Product>> updateProductbyname(@RequestParam String name,double price,int qty){
		return productService.updateProductByname(name, price, qty);
	}
	
}
