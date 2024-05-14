package br.com.sysmap.bootcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Unauthorized user exception.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedUserException extends RuntimeException {

  /**
   * Instantiates a new Unauthorized user exception.
   */
  public UnauthorizedUserException() {
    super("Unauthorized User");
  }
}
