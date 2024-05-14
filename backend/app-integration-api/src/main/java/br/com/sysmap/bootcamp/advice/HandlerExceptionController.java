package br.com.sysmap.bootcamp.advice;

import br.com.sysmap.bootcamp.exceptions.AlbumAlreadyInCollectionException;
import br.com.sysmap.bootcamp.exceptions.AlbumNotFoundException;
import br.com.sysmap.bootcamp.exceptions.UnauthorizedUserException;
import br.com.sysmap.bootcamp.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * The type Handler exception controller.
 */
@ControllerAdvice
public class HandlerExceptionController {

  /**
   * Handle unauthorized user exception response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler(UnauthorizedUserException.class)
  public ResponseEntity<String> handleUnauthorizedUserException(RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
  }

  /**
   * Handle album not found exception response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler(AlbumNotFoundException.class)
  public ResponseEntity<String> handleAlbumNotFoundException(RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
  }

  /**
   * Handle user not found exception response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<String> handleUserNotFoundException(RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
  }

  /**
   * Handle album already in collection exception response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler(AlbumAlreadyInCollectionException.class)
  public ResponseEntity<String> handleAlbumAlreadyInCollectionException(
      RuntimeException exception) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getMessage());
  }

  /**
   * Method argument not valid exception response entity.
   *
   * @param ex the ex
   * @return the response entity
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(
            "Invalid Request Body\n" + ex.getBindingResult().getFieldError().getDefaultMessage());
  }

  /**
   * Handle runtime exception response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<String> handleRuntimeException(RuntimeException exception) {
    return ResponseEntity
        .status(HttpStatus.BAD_REQUEST)
        .body(exception.getMessage());
  }

  /**
   * Handle exception response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleException(Exception exception) {
    return ResponseEntity
        .status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(exception.getMessage());
  }

  /**
   * Handle throwable response entity.
   *
   * @param exception the exception
   * @return the response entity
   */
  @ExceptionHandler(Throwable.class)
  public ResponseEntity<String> handleThrowable(Throwable exception) {
    return ResponseEntity
        .status(HttpStatus.BAD_GATEWAY)
        .body(exception.getMessage());
  }
}
