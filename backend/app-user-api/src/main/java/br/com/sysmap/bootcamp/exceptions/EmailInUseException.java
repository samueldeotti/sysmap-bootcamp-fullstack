package br.com.sysmap.bootcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Email in use exception.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class EmailInUseException extends RuntimeException {

  /**
   * Instantiates a new Email in use exception.
   */
  public EmailInUseException() {
    super("This email is already in use");
  }

}
