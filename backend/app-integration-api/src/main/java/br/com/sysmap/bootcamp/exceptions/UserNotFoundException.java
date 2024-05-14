package br.com.sysmap.bootcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type User not found exception.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

  /**
   * Instantiates a new User not found exception.
   */
  public UserNotFoundException() {
    super("User Not Found");
  }
}
