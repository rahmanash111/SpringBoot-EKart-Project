package Online.Exception;

public class NoDataFoundException extends RuntimeException{

	String msg="No data Found";
	
	public NoDataFoundException() {
		
	}
	
	public NoDataFoundException(String msg) {
		this.msg=msg;
	}
	
	public String getMessage() {
		return this.msg;
	}
}
