package Online.Exception;

public class OutOfStockException extends RuntimeException {

	String msg = "Out Of Stock!!!";

	public OutOfStockException() {
	}

	public OutOfStockException(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return this.msg;
	}

}
