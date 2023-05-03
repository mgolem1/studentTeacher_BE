package users.app.rest.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import users.exceptions.AppError;
import users.exceptions.AppException;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

	@ExceptionHandler({AccessDeniedException.class})
	public ResponseEntity<Object> handleException() {

		String response = null;
		try {
			response = ResponseMessage.packageAndJsoniseError(AppError.UNAUTHORIZED);
		} catch (AppException e) {

			e.printStackTrace();
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler({AppException.class})
	public ResponseEntity<Object> handleUsersException(
			AppException ex) {

		String response = null;
		try {
			if (ex.getErrorData() != null) {
				response = ResponseMessage.packageAndJsoniseError(ex.getError(), ex.getErrorData().toString());
			} else {
				response = ResponseMessage.packageAndJsoniseError(ex.getError());
			}
		} catch (AppException e) {

			e.printStackTrace();
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ExceptionHandler({Exception.class})
	public ResponseEntity<Object> handleException(
			Exception ex) {

		String response = null;
		ex.printStackTrace();
		try {
			response = ResponseMessage.packageAndJsoniseError(AppError.UNRECOGNIZED_EXCEPTION, ex.toString());
		} catch (AppException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({ClientAbortException.class})
	public void handleException(ClientAbortException ex) {
		log.info("Client aborted request!");
	}
}
