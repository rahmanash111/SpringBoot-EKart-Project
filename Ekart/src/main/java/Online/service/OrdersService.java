package Online.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import Online.Exception.IdNotFoundException;
import Online.Exception.NoDataFoundException;
import Online.dao.CustomerDao;
import Online.dao.OrdersDao;
import Online.dto.Customer;
import Online.dto.Orders;
import Online.dto.Products;
import Online.util.ResponseStructure;

@Service
public class OrdersService {

	@Autowired
	OrdersDao ordersDao;

	@Autowired
	CustomerDao customerDao;

	@Autowired
	ProductsService productsService;

	public ResponseEntity<ResponseStructure<Orders>> saveOrders(int cid, Orders orders) {

		Customer customer = customerDao.getCustomerbyid(cid);
		if (customer != null) {

			orders.setCustomer(customer);
			// orders.setDate(new Date(System.currentTimeMillis()));
			orders.setDate(new Date(System.currentTimeMillis()));

			Orders o1 = ordersDao.saveOrders(orders);

			List<Orders> oList = customer.getOrders();

			oList.add(o1);

			customer.setOrders(oList);

			customerDao.saveCustomer(customer);

			ResponseStructure<Orders> structure = new ResponseStructure<>();

			ResponseEntity<ResponseStructure<Orders>> entity = new ResponseEntity<>(structure, HttpStatus.CREATED);

			structure.setMessage("Orders created successfully");
			structure.setStatuscode(HttpStatus.CREATED.value());
			structure.setData(o1);

			return entity;

		} else
			throw new NoDataFoundException("Customer details with this id is not present");
	}

	public ResponseEntity<ResponseStructure<Orders>> getOrdersbyid(int id) {

		Orders orders = ordersDao.getOrdersbyid(id);

		if (orders != null) {

			ResponseStructure<Orders> structure = new ResponseStructure<>();

			ResponseEntity<ResponseStructure<Orders>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

			structure.setMessage("Orders details found with the id");
			structure.setStatuscode(HttpStatus.OK.value());
			structure.setData(orders);

			return entity;

		} else
			throw new IdNotFoundException("Order not found with this id");
	}

	public ResponseEntity<ResponseStructure<List<Orders>>> getAllorders() {

		List<Orders> orders = ordersDao.getAllorders();

		if (!orders.isEmpty()) {

			ResponseStructure<List<Orders>> structure = new ResponseStructure<>();

			ResponseEntity<ResponseStructure<List<Orders>>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

			structure.setMessage("Orders details");
			structure.setStatuscode(HttpStatus.OK.value());
			structure.setData(orders);

			return entity;

		} else
			throw new NoDataFoundException("No order not found");
	}

	public ResponseEntity<ResponseStructure<String>> deleteOrdersbyid(int id) {

		Orders orders = ordersDao.getOrdersbyid(id);

		if (orders != null) {

			List<Products> pList = orders.getPList();

//			for (Products p : pList) {
//				productsService.deleteProductsbyid(orders.getOrderId(),p.getId());
//			}

//			ListIterator<Products> lIt=pList.listIterator();
//			
//			while(lIt.hasNext()) {
//			 productsService.deleteProductsbyid(orders.getOrderId(), lIt.next().getId());
//			}

			for (int i = pList.size() - 1; i >= 0; i--) {
				productsService.deleteProductsbyid(orders.getOrderId(), pList.get(i).getId());
			}

			System.out.println("every");

			ResponseStructure<String> structure = new ResponseStructure<>();

			ResponseEntity<ResponseStructure<String>> entity = new ResponseEntity<>(structure, HttpStatus.NO_CONTENT);

			structure.setMessage("Orders deleted successfully");
			structure.setStatuscode(HttpStatus.NO_CONTENT.value());
			structure.setData(ordersDao.deleteOrdersbyid(id));

			return entity;

		} else
			throw new IdNotFoundException("Orders with this id is not present");
	}

	public ResponseEntity<ResponseStructure<Customer>> getCustomerid(String date) {

		Customer customer = customerDao
				.getCustomerbyid(ordersDao.getCustomerid(Date.valueOf(date), Date.valueOf(date)));
		
		ResponseStructure<Customer> structure = new ResponseStructure<>();

		ResponseEntity<ResponseStructure<Customer>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

		structure.setMessage("Customer details");
		structure.setStatuscode(HttpStatus.OK.value());
		structure.setData(customer);

		return entity;
	}

}
