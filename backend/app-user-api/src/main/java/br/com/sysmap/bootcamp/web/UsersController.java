package br.com.sysmap.bootcamp.web;

import br.com.sysmap.bootcamp.domain.entities.Users;
import br.com.sysmap.bootcamp.domain.service.UsersService;
import br.com.sysmap.bootcamp.dto.AuthDto;
import br.com.sysmap.bootcamp.config.swaggerInterfaces.UsersSwaggerApi;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Users controller.
 */
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController implements UsersSwaggerApi {

  private final UsersService usersService;

  /**
   * Gets all users.
   *
   * @return the all users
   */

  @GetMapping
  public ResponseEntity<List<Users>> getAllUsers() {
    return ResponseEntity.ok(this.usersService.getAllUsers());
  }

  /**
   * Gets user by id.
   *
   * @param id the id
   * @return the user by id
   */

  @GetMapping("/{id}")
  public ResponseEntity<Users> getUserById(@PathVariable @Valid Long id) {
    return ResponseEntity.ok(this.usersService.getUserById(id));
  }

  /**
   * Create response entity.
   *
   * @param user the user
   * @return the response entity
   */

  @PostMapping("/create")
  public ResponseEntity<Users> createUser(@RequestBody @Valid Users user) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.usersService.createUser(user));
  }

  /**
   * Update response entity.
   *
   * @param updatedUser the updated user
   * @return the response entity
   */

  @PutMapping("/update")
  public ResponseEntity<Users> updateUser(@RequestBody @Valid Users updatedUser) {
    Users updated = usersService.updateUser(updatedUser);
    return ResponseEntity.status(HttpStatus.CREATED).body(updated);
  }

  /**
   * Auth response entity.
   *
   * @param user the user
   * @return the response entity
   */

  @PostMapping("/auth")
  public ResponseEntity<AuthDto> authUser(@RequestBody @Valid AuthDto user) {
    return ResponseEntity.ok(this.usersService.authUser(user));
  }

}