package Online.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Online.dto.Products;
import Online.service.ProductsService;
import Online.util.ResponseStructure;

@RestController
public class ProductsController {

	@Autowired
	ProductsService productsService;

	@PostMapping("saveproducts")
	public ResponseEntity<ResponseStructure<Products>> saveProducts(@RequestParam int oId, int pId, int qty) {
		return productsService.saveProducts(oId, pId, qty);
	}

	@GetMapping("getproductsbyid")
	public ResponseEntity<ResponseStructure<Products>> getProductsbyid(@RequestParam int id) {
		return productsService.getProductsbyid(id);
	}

	@GetMapping("getproductsbyname")
	public ResponseEntity<ResponseStructure<Products>> getProductsbyname(@RequestParam int oId,String name) {
		return productsService.getProductsbyname(oId,name);
	}

	@GetMapping("getallproducts")
	public ResponseEntity<ResponseStructure<List<Products>>> getAllProducts() {
		return productsService.getAllproducts();
	}

	@DeleteMapping("deleteproductsbyid")
	public ResponseEntity<ResponseStructure<String>> deleteProductsbyid(@RequestParam int oId,int pId) {
		return productsService.deleteProductsbyid(oId,pId);
	}

	@PutMapping("updateproductsbyname")
	public ResponseEntity<ResponseStructure<Products>> updateProductsbyname(@RequestParam int oId,String name,int qty){
		return productsService.updateProductsbyname(oId,name,qty);
	}

	
}
