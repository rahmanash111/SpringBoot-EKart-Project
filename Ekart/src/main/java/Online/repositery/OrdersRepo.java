package Online.repositery;

import java.sql.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Online.dto.Customer;
import Online.dto.Orders;

public interface OrdersRepo extends JpaRepository<Orders, Integer> {

	@Query(nativeQuery = true, value = "select Customer_customer_id from springboot_ekart.orders where date=? group by customer_customer_id having sum(order_value) =(select max(val) from (select sum(order_value) val from springboot_ekart.orders where date=? group by customer_customer_id) ntable)")
	public int findCustomerId(Date date1, Date date2);

}
