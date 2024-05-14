package br.com.sysmap.bootcamp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The type Album already in collection exception.
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class AlbumAlreadyInCollectionException extends RuntimeException {

  /**
   * Instantiates a new Album already in collection exception.
   */
  public AlbumAlreadyInCollectionException() {
    super("You already have this album");
  }
}

