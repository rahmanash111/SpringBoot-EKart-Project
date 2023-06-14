package Online.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import Online.Exception.DuplicateDataException;
import Online.Exception.NoDataFoundException;
import Online.dao.CustomerDao;
import Online.dto.Customer;
import Online.dto.Orders;
import Online.util.ResponseStructure;

@Service
public class CustomerService {

	@Autowired
	CustomerDao customerDao;

	@Autowired
	OrdersService ordersService;

	public ResponseEntity<ResponseStructure<Customer>> saveCustomer(Customer customer) {

		if (customerDao.getCustomerbymail(customer.getEmail()) == null) {

			ResponseStructure<Customer> structure = new ResponseStructure<>();
			ResponseEntity<ResponseStructure<Customer>> entity = new ResponseEntity<>(structure, HttpStatus.CREATED);

			structure.setMessage("customer account created");
			structure.setData(customerDao.saveCustomer(customer));
			structure.setStatuscode(HttpStatus.CREATED.value());

			return entity;
		} else
			throw new DuplicateDataException("Email id already registered");
	}

	public ResponseEntity<ResponseStructure<Customer>> getCustomerbyid(int id) {

		Customer customer = customerDao.getCustomerbyid(id);

		if (customer != null) {

			ResponseStructure<Customer> structure = new ResponseStructure<>();

			ResponseEntity<ResponseStructure<Customer>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

			structure.setMessage("date found");
			structure.setStatuscode(HttpStatus.OK.value());
			structure.setData(customer);

			return entity;

		} else
			throw new NoDataFoundException("No data present with the given id");

	}

	public ResponseEntity<ResponseStructure<Customer>> getCustomerbymail(String mail) {

		Customer customer = customerDao.getCustomerbymail(mail);

		if (customer != null) {

			ResponseStructure<Customer> structure = new ResponseStructure<>();

			ResponseEntity<ResponseStructure<Customer>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

			structure.setMessage("Details found");
			structure.setStatuscode(HttpStatus.OK.value());
			structure.setData(customer);

			return entity;
		} else
			throw new NoDataFoundException("No Data available for given Email");
	}

	public ResponseEntity<ResponseStructure<List<Customer>>> getAllcustomer() {

		List<Customer> cList = customerDao.getAllcustomer();

		if (!cList.isEmpty()) {

			ResponseStructure<List<Customer>> structure = new ResponseStructure<>();
			ResponseEntity<ResponseStructure<List<Customer>>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

			structure.setMessage("Data List found");
			structure.setStatuscode(HttpStatus.OK.value());
			structure.setData(cList);

			return entity;
		} else
			throw new NoDataFoundException();
	}

	public ResponseEntity<ResponseStructure<String>> deleteCustomerbyid(int id) {

		Customer customer = customerDao.getCustomerbyid(id);

		if (customer != null) {

			List<Orders> oList = customer.getOrders();

			if (!oList.isEmpty() && oList.size() > 0) {
				for (int i = oList.size() - 1; i >= 0; i--) {
					ordersService.deleteOrdersbyid(oList.get(i).getOrderId());
				}
			}

			ResponseStructure<String> structure = new ResponseStructure<>();
			ResponseEntity<ResponseStructure<String>> entity = new ResponseEntity<>(structure, HttpStatus.NO_CONTENT);

			structure.setMessage("data deleted");
			structure.setStatuscode(HttpStatus.NO_CONTENT.value());
			structure.setData(customerDao.deleteCustomerbyid(id));

			return entity;
		} else
			throw new NoDataFoundException("Customer details not found with this id");
	}

	public ResponseEntity<ResponseStructure<Customer>> updateCustomerbyid(int id, Customer customer) {

		Customer c = customerDao.getCustomerbyid(id);
		if (c != null) {

			c.setName(customer.getName());
			c.setEmail(customer.getEmail());
			c.setMobile(customer.getMobile());
			
			ResponseStructure<Customer> structure = new ResponseStructure<>();

			ResponseEntity<ResponseStructure<Customer>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

			structure.setMessage("Customer data modified");
			structure.setStatuscode(HttpStatus.OK.value());
			structure.setData(customerDao.updateCustomer(c));

			return entity;

		} else
			throw new NoDataFoundException("Customer data not found with the given id");

	}

}
