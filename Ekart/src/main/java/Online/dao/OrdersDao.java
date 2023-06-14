package Online.dao;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import Online.dto.Customer;
import Online.dto.Orders;
import Online.repositery.OrdersRepo;

@Repository
public class OrdersDao {

	@Autowired
	OrdersRepo repo;

	public Orders saveOrders(Orders orders) {
		return repo.save(orders);
	}

	public Orders getOrdersbyid(int id) {

		Optional<Orders> o = repo.findById(id);

		if (o.isPresent())
			return o.get();
		else
			return null;
	}
	
	public List<Orders> getAllorders(){
		return repo.findAll();
	}
	
	public String deleteOrdersbyid(int id) {
		repo.deleteById(id);
		return "Orders has been deleted";
	}
	
	public Orders updateOrders(Orders orders) {
		return repo.save(orders);
	}
	
	public int getCustomerid(Date date1,Date date2) {
		return repo.findCustomerId(date1, date2);
	}
}
