package br.com.sysmap.bootcamp.config.swaggerInterfaces;

import br.com.sysmap.bootcamp.domain.entities.Users;
import br.com.sysmap.bootcamp.dto.AuthDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Users", description = "Users Api")
public interface UsersSwaggerApi {

  @Operation(summary = "List users", tags = "Users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Return all Users"),
      @ApiResponse(responseCode = "401", description = "Unauthorized User | Invalid Token", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Unauthorized User | Invalid Token\"")
          }
      )),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Internal Server Error\"")
          }
      ))
  })
  ResponseEntity<List<Users>> getAllUsers();

  @Operation(summary = "Get user by id", tags = "Users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Return an User by id"),
      @ApiResponse(responseCode = "401", description = "Unauthorized User | Invalid Token", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Unauthorized User | Invalid Token\"")
          }
      )),
      @ApiResponse(responseCode = "404", description = "User not found", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"User not found\"")
          }
      )),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Internal Server Error\"")
          }
      ))
  })
  ResponseEntity<Users> getUserById(Long id);

  @Operation(summary = "Save user", tags = "Users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Create and return an User"),
      @ApiResponse(responseCode = "400", description = "Invalid user data", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Invalid user data\"")
          }
      )),
      @ApiResponse(responseCode = "409", description = "Email already in use", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Email already in use\"")
          }
      )),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Internal Server Error\"")
          }
      ))
  })
  ResponseEntity<Users> createUser(Users user);

  @Operation(summary = "Update user", tags = "Users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Update and return an User"),
      @ApiResponse(responseCode = "400", description = "Invalid user data", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Invalid user data\"")
          }
      )),
      @ApiResponse(responseCode = "401", description = "Unauthorized User | Invalid Token", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Unauthorized User | Invalid Token\"")
          }
      )),
      @ApiResponse(responseCode = "404", description = "User not found", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"User not found\"")
          }
      )),
      @ApiResponse(responseCode = "409", description = "Email already in use", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Email already in use\"")
          }
      )),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Internal Server Error\"")
          }
      ))
  })
  ResponseEntity<Users> updateUser(Users user);

  @Operation(summary = "Auth user", tags = "Users")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Auth an User and return token"),
      @ApiResponse(responseCode = "400", description = "Invalid credentials", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Invalid credentials\"")
          }
      )),
      @ApiResponse(responseCode = "404", description = "User not found", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"User not found\"")
          }
      )),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Internal Server Error\"")
          }
      ))
  })
  ResponseEntity<AuthDto> authUser(AuthDto user);
}