package Online.Exception;

public class IdNotFoundException extends RuntimeException {

	String msg = "Id Not Found";

	public IdNotFoundException(String msg) {
		this.msg = msg;
	}

	public IdNotFoundException() {

	}

	@Override
	public String getMessage() {
		return msg;
	}

}
