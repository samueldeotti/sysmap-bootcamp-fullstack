package br.com.sysmap.bootcamp.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The type Auth dto.
 */
@Builder
@Getter
@Setter
public class AuthDto {

  @NotEmpty
  private String email;
  @NotEmpty
  private String password;
  private Long id;
  private String token;

  /**
   * Instantiates a new Auth dto.
   *
   * @param email    the email
   * @param password the password
   * @param id       the id
   * @param token    the token
   */
  public AuthDto(String email, String password, Long id, String token) {
    this.email = email;
    this.password = password;
    this.id = id;
    this.token = token;
  }

  /**
   * Instantiates a new Auth dto.
   */
  public AuthDto() {
  }

  /**
   * Instantiates a new Auth dto.
   *
   * @param mail     the mail
   * @param password the password
   */
  public AuthDto(String mail, String password) {
  }
}