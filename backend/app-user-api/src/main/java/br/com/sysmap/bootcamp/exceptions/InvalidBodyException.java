package br.com.sysmap.bootcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Invalid body exception.
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidBodyException extends RuntimeException {

  /**
   * Instantiates a new Invalid body exception.
   *
   * @param message the message
   */
  public InvalidBodyException(String message) {
    super(message);
  }
}

