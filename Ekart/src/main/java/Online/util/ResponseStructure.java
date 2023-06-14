package Online.util;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseStructure<T>{
	
	private int statuscode;
	private String message;
	private T data;
	
}
