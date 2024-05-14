package br.com.sysmap.bootcamp.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.sysmap.bootcamp.advice.HandlerExceptionController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootTest
@AutoConfigureMockMvc
public class HandleGenericExceptionTest {

  private final HandlerExceptionController controller = new HandlerExceptionController();

  @Test
  @DisplayName("Should handle UnauthorizedUserException")
  public void shouldHandleUnauthorizedUserException() {
    UnauthorizedUserException exception = new UnauthorizedUserException();
    ResponseEntity<String> response = controller.handleUnauthorizedUserException(exception);
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertEquals("Unauthorized User", response.getBody());
  }

  @Test
  @DisplayName("Should handle AlbumNotFoundException")
  public void shouldHandleAlbumNotFoundException() {
    AlbumNotFoundException exception = new AlbumNotFoundException();
    ResponseEntity<String> response = controller.handleAlbumNotFoundException(exception);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("Album not found", response.getBody());
  }

  @Test
  @DisplayName("Should handle UserNotFoundException")
  public void shouldHandleUserNotFoundException() {
    UserNotFoundException exception = new UserNotFoundException();
    ResponseEntity<String> response = controller.handleUserNotFoundException(exception);
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    assertEquals("User Not Found", response.getBody());
  }

  @Test
  @DisplayName("Should handle AlbumAlreadyInCollectionException")
  public void shouldHandleAlbumAlreadyInCollectionException() {
    AlbumAlreadyInCollectionException exception = new AlbumAlreadyInCollectionException();
    ResponseEntity<String> response = controller.handleAlbumAlreadyInCollectionException(exception);
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    assertEquals("You already have this album", response.getBody());
  }

  @Test
  @DisplayName("Should handle RuntimeException")
  public void shouldHandleRuntimeException() {
    RuntimeException exception = new RuntimeException("Runtime error");
    ResponseEntity<String> response = controller.handleRuntimeException(exception);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    assertEquals("Runtime error", response.getBody());
  }

  @Test
  @DisplayName("Should handle Exception")
  public void shouldHandleException() {
    Exception exception = new Exception("Internal server error");
    ResponseEntity<String> response = controller.handleException(exception);
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals("Internal server error", response.getBody());
  }

  @Test
  @DisplayName("Should handle Throwable")
  public void shouldHandleThrowable() {
    Throwable throwable = new Throwable("Bad gateway error");
    ResponseEntity<String> response = controller.handleThrowable(throwable);
    assertEquals(HttpStatus.BAD_GATEWAY, response.getStatusCode());
    assertEquals("Bad gateway error", response.getBody());
  }


}



