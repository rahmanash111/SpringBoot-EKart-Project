package Online.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import Online.Exception.DuplicateDataException;
import Online.Exception.IdNotFoundException;
import Online.Exception.NoDataFoundException;
import Online.Exception.OutOfStockException;
import Online.util.ResponseStructure;

@RestControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> idNotfoundexception(IdNotFoundException exception) {

		ResponseStructure<String> structure = new ResponseStructure<>();
		ResponseEntity<ResponseStructure<String>> entity = new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);

		structure.setStatuscode(HttpStatus.NOT_FOUND.value());
		structure.setMessage("Id not found");
		structure.setData(exception.getMessage());

		return entity;
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(NoDataFoundException.class)
	public ResponseEntity<ResponseStructure<String>> noDatafoundexception(NoDataFoundException exception) {

		ResponseStructure<String> structure = new ResponseStructure<>();
		ResponseEntity<ResponseStructure<String>> entity = new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);

		structure.setStatuscode(HttpStatus.NOT_FOUND.value());
		structure.setMessage("Data not found");
		structure.setData(exception.getMessage());

		return entity;
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(DuplicateDataException.class)
	public ResponseEntity<ResponseStructure<String>> duplicateDataexception(DuplicateDataException exception) {

		ResponseStructure<String> structure = new ResponseStructure<>();
		ResponseEntity<ResponseStructure<String>> entity = new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);

		structure.setMessage("Duplicate Data");
		structure.setStatuscode(HttpStatus.NOT_FOUND.value());
		structure.setData(exception.getMessage());

		return entity;
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(OutOfStockException.class)
	public ResponseEntity<ResponseStructure<String>> outOfstockexception(OutOfStockException exception) {

		ResponseStructure<String> structure = new ResponseStructure<>();
		ResponseEntity<ResponseStructure<String>> entity = new ResponseEntity<>(structure, HttpStatus.NOT_FOUND);

		structure.setMessage("No Stock");
		structure.setStatuscode(HttpStatus.NOT_FOUND.value());
		structure.setData(exception.getMessage());

		return entity;

	}

}
