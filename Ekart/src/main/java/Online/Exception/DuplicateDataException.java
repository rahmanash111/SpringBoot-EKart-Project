package Online.Exception;

public class DuplicateDataException extends RuntimeException {

	String msg = "Duplicate Data";

	public DuplicateDataException() {

	}

	public DuplicateDataException(String msg) {
		this.msg = msg;
	}

	public String getMessage() {
		return this.msg;
	}
}
