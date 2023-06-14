package Online.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import Online.dto.Customer;
import Online.dto.Orders;
import Online.repositery.OrdersRepo;
import Online.service.OrdersService;
import Online.util.ResponseStructure;

@RestController
public class OrdersController {

	@Autowired
	OrdersService ordersService;
	@Autowired
	OrdersRepo ordersRepo;
	
	@PostMapping("saveorders")
	public ResponseEntity<ResponseStructure<Orders>> saveOrders(@RequestParam int cId,@RequestBody Orders orders){
		return ordersService.saveOrders(cId, orders);
	}
	
	@GetMapping("getordersbyid")
	public ResponseEntity<ResponseStructure<Orders>> getOrdersbyid(@RequestParam int id){
		return ordersService.getOrdersbyid(id);
	}
	
	@GetMapping("getallorders")
	public ResponseEntity<ResponseStructure<List<Orders>>> getAllorders(){
		return ordersService.getAllorders();
	}
	
	@DeleteMapping("deleteordersbyid")
	public ResponseEntity<ResponseStructure<String>> deleteOrdersbyid(@RequestParam int id){
		return ordersService.deleteOrdersbyid(id);
	}
	
	
	//Selecting the customer data who made the maximum purchase on a specific day
	@GetMapping("query1")
	public ResponseEntity<ResponseStructure<Customer>> getCustomerid(@RequestParam String date) {
		return ordersService.getCustomerid(date);
	}
}
