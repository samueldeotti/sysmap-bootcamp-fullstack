package br.com.sysmap.bootcamp.domain.entities;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Getter.AnyAnnotation;
import lombok.NoArgsConstructor;


/**
 * The type Users.
 */
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "USERS")
public class Users {

  @Schema(description = "User's id", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Schema(description = "User's name", example = "John Doe")
  @NotEmpty(message = "Invalid name")
  @Column(name = "name")
  private String name;

  @Schema(description = "User's email", example = "johndoe@example.com")
  @NotEmpty(message = "Invalid email")
  @Email(message = "Email should be valid")
  @Column(name = "email")
  private String email;

  @Schema(description = "User's password", example = "123456")
  @NotEmpty(message = "Invalid password")
  @Column(name = "password")
  private String password;

}