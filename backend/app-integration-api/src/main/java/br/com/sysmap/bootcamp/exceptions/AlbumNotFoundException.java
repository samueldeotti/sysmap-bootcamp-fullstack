package br.com.sysmap.bootcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Album not found exception.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AlbumNotFoundException extends RuntimeException {

  /**
   * Instantiates a new Album not found exception.
   */
  public AlbumNotFoundException() {
    super("Album not found");
  }
}
