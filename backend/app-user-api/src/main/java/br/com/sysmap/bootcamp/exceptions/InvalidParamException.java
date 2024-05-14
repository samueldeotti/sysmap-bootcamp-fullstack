package br.com.sysmap.bootcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Invalid param exception.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class InvalidParamException extends RuntimeException {

  /**
   * Instantiates a new Invalid param exception.
   */
  public InvalidParamException() {
    super("Invalid parameters");
  }
}
