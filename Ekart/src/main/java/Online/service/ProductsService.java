package Online.service;

import java.util.List;
import java.util.ListIterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import Online.Exception.DuplicateDataException;
import Online.Exception.IdNotFoundException;
import Online.Exception.NoDataFoundException;
import Online.Exception.OutOfStockException;
import Online.dao.CategoryDao;
import Online.dao.CustomerDao;
import Online.dao.OrdersDao;
import Online.dao.ProductDao;
import Online.dao.ProductsDao;
import Online.dto.Orders;
import Online.dto.Product;
import Online.dto.Products;
import Online.util.ResponseStructure;

@Service
public class ProductsService {

	@Autowired
	ProductsDao productsDao;

	@Autowired
	OrdersDao ordersDao;

	@Autowired
	ProductDao productDao;

	@Autowired
	CustomerDao customerDao;

	@Autowired
	CategoryDao categoryDao;

//	@Autowired
//	ProductsService productsService;

	public ResponseEntity<ResponseStructure<Products>> saveProducts(int oId, int pId, int qty) {

		Orders orders = ordersDao.getOrdersbyid(oId);

		if (orders != null) {

			Product product = productDao.getProductbyid(pId);

			if (product != null) {

				List<Products> pList = orders.getPList();

				boolean b = true;

				for (int i = pList.size() - 1; i >= 0; i--) {
					if (pList.get(i).getName().equalsIgnoreCase(product.getProductName()))
						b = false;
				}

				if (b) {

					if (product.getQuantity() >= qty) {

						Products products = new Products();
						products.setName(product.getProductName());
						products.setPrice(product.getPrice());
						products.setQuantity(qty);

						// List<Products> pList = orders.getPList();
						// pList.add(productsDao.save(products));

						pList.add(products);

						product.setQuantity(product.getQuantity() - qty);

						productDao.updateProductbyname(product);

						orders.setPList(pList);
						orders.setOrderValue(orders.getOrderValue() + products.getPrice() * qty);
						// orders.setDate(new Date());
						// orders.setCustomer(customer);

						ordersDao.saveOrders(orders);

						ResponseStructure<Products> structure = new ResponseStructure<>();

						ResponseEntity<ResponseStructure<Products>> entity = new ResponseEntity<>(structure,
								HttpStatus.CREATED);

						structure.setMessage("Products created successfully");
						structure.setStatuscode(HttpStatus.CREATED.value());
						structure.setData(products);

						return entity;
					} else
						throw new OutOfStockException("The products stock is lesser than the ordered quantity");
				} else
					throw new DuplicateDataException("Given Products already present in the Order");

			} else
				throw new NoDataFoundException("No Product available with this id");
		} else
			throw new NoDataFoundException("No Orders found with this id");
	}

	public ResponseEntity<ResponseStructure<List<Products>>> getAllproducts() {

		List<Products> pList = productsDao.getAllproducts();

		if (!pList.isEmpty()) {

			ResponseStructure<List<Products>> structure = new ResponseStructure<>();

			ResponseEntity<ResponseStructure<List<Products>>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

			structure.setMessage("All Products Details");
			structure.setStatuscode(HttpStatus.OK.value());
			structure.setData(pList);

			return entity;
		} else
			throw new NoDataFoundException("No Products available");

	}

	public ResponseEntity<ResponseStructure<Products>> getProductsbyid(int id) {

		Products products = productsDao.getProductsbyid(id);

		if (products != null) {

			ResponseStructure<Products> structure = new ResponseStructure<>();

			ResponseEntity<ResponseStructure<Products>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

			structure.setMessage("Products has been found with the Id");
			structure.setStatuscode(HttpStatus.OK.value());
			structure.setData(products);

			return entity;

		} else
			throw new NoDataFoundException("No products available with this id");
	}

	public ResponseEntity<ResponseStructure<Products>> getProductsbyname(int oId, String name) {

		Orders orders = ordersDao.getOrdersbyid(oId);

		if (orders != null) {

			Products products = productsDao.getProductsbyname(name);

			if (products != null) {

				ResponseStructure<Products> structure = new ResponseStructure<>();

				ResponseEntity<ResponseStructure<Products>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

				structure.setMessage("Products has been found with the name");
				structure.setStatuscode(HttpStatus.OK.value());
				structure.setData(products);

				return entity;

			} else
				throw new NoDataFoundException("No products available with this name");
		} else
			throw new NoDataFoundException("Orders not found with this id");

	}

	public ResponseEntity<ResponseStructure<String>> deleteProductsbyid(int oId, int pId) {
		Orders orders = ordersDao.getOrdersbyid(oId);
		if (orders != null) {
			Products products = productsDao.getProductsbyid(pId);
			if (products != null) {
				List<Products> pList = orders.getPList();
				ListIterator<Products> lIt = pList.listIterator();
				while (lIt.hasNext()) {
					if (lIt.next().equals(products))
						lIt.remove();
				}
				orders.setOrderValue(orders.getOrderValue() - products.getPrice() * products.getQuantity());
				orders.setPList(pList);
				ordersDao.updateOrders(orders);
				Product product = productDao.getProductbyname(products.getName());
				product.setQuantity(product.getQuantity() + products.getQuantity());
				productDao.saveProduct(product);

				ResponseStructure<String> structure = new ResponseStructure<>();
				ResponseEntity<ResponseStructure<String>> entity = new ResponseEntity<>(structure,
						HttpStatus.NO_CONTENT);
				structure.setMessage("Products deleted successfully");
				structure.setStatuscode(HttpStatus.NO_CONTENT.value());
				structure.setData(productsDao.deleteProductsbyid(pId));
				return entity;
			} else
				throw new IdNotFoundException("No Product available with this id");
			
		} else
			throw new IdNotFoundException("No order present with this id");
	}

	public ResponseEntity<ResponseStructure<Products>> updateProductsbyname(int oId, String name, int qty) {

		Orders orders = ordersDao.getOrdersbyid(oId);

		if (orders != null) {

			List<Products> pList = orders.getPList();

			Products products = null;

			for (int i = pList.size() - 1; i >= 0; i--) {

				if (pList.get(i).getName().equals(name)) {
					products = pList.get(i);
				}
			}
			if (products != null) {

				Product product = productDao.getProductbyname(name);

				int psqty = products.getQuantity(), pqty = product.getQuantity();

				double asr = psqty * products.getPrice(), asa = qty * product.getPrice();

				if (psqty + pqty >= qty) {

					// productsDao.deleteProductsbyid(products.getId());

					// new ProductsService().deleteProductsbyid(oId, products.getId());

					// int minus=qty+;

					product.setQuantity(pqty + psqty - qty);
					productDao.saveProduct(product);

					products.setQuantity(qty);

					orders.setOrderValue((orders.getOrderValue() - asr + asa));
					
					ordersDao.saveOrders(orders);
					
					ResponseStructure<Products> structure = new ResponseStructure<>();

					ResponseEntity<ResponseStructure<Products>> entity = new ResponseEntity<>(structure, HttpStatus.OK);

					structure.setMessage("Products Modified successfully");
					structure.setStatuscode(HttpStatus.OK.value());
					structure.setData(productsDao.updateProducts(products));

					// new ProductsService().saveProducts(oId, product.getProductId(), qty);

					return entity;

				} else
					throw new OutOfStockException("The products stock is lesser than the ordered quantity");
			} else
				throw new NoDataFoundException("No Products available with this name");
		} else
			throw new NoDataFoundException("Orders not found with this id");
	}

}
