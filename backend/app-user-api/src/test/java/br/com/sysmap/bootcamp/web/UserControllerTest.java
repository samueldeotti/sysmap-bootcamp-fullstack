package br.com.sysmap.bootcamp.web;

import br.com.sysmap.bootcamp.domain.entities.Users;
import br.com.sysmap.bootcamp.exceptions.EmailInUseException;
import br.com.sysmap.bootcamp.exceptions.UserNotFoundException;
import br.com.sysmap.bootcamp.domain.service.UsersService;
import br.com.sysmap.bootcamp.dto.AuthDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

  @Autowired
  private UsersController userController;

  @MockBean
  private UsersService userService;

  @MockBean
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }


  @Nested
  @DisplayName("Tests for create User")
  class CreateUser {

    @Test
    @DisplayName("Should return a new user when valid")
    public void shouldCreateANewUser() {
      Users mockedUser = Users.builder().id(1L).name("jhon").email("john@example.com").build();

      when(userService.createUser(mockedUser)).thenReturn(mockedUser);

      ResponseEntity<Users> response = userController.createUser(mockedUser);

      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals(mockedUser.getId(), response.getBody().getId());
      assertThat(response.getBody()).isEqualTo(mockedUser);
    }

    @Test
    @DisplayName("Should return an exception when user email already exists")

    public void shouldReturnAnException() {
      Users mockedUser = Users.builder().name("john").email("john@example.com").build();

      when(userService.createUser(mockedUser)).thenThrow(new EmailInUseException());
      assertThrows(EmailInUseException.class, () -> userService.createUser(mockedUser));

    }

  }

  @Nested
  @DisplayName("Tests for user auth")
  class UserAuth {

    @Test
    @DisplayName("Should return an authenticated user")
    public void should_return_an_authenticated_user() {
      String email = "john@example.com";
      String password = "password";

      String expectedToken = Base64.getEncoder().withoutPadding()
          .encodeToString((email + ":" + password).getBytes());

      AuthDto authDto = AuthDto.builder().email(email).password(password).build();
      Users userEntity = Users.builder().id(1L).email(email)
          .password(passwordEncoder.encode(password))
          .build();

      when(userService.findByEmail(email)).thenReturn(userEntity);

      ResponseEntity<AuthDto> response = userController.authUser(authDto);

      Optional.ofNullable(response.getBody()).ifPresent(body -> {
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(body.getEmail()).isEqualTo(email);
        assertThat(body.getToken()).isEqualTo(expectedToken);
        assertThat(body.getId()).isEqualTo(userEntity.getId());

      });
    }

    @Test
    @DisplayName("Should return an exception when invalid password")
    public void shouldReturnAnExceptionWhenInvalidPassword() {
      String email = "john@example.com";
      String invalidPassword = "invalid_password";
      String encodedPassword = passwordEncoder.encode(invalidPassword);

      AuthDto invalidAuthDto = new AuthDto(email, encodedPassword);

      when(userService.authUser(invalidAuthDto)).thenThrow(
          new RuntimeException("Invalid Password"));

      assertThrows(RuntimeException.class, () -> userService.authUser(invalidAuthDto));

    }

  }

  @Nested
  @DisplayName("Tests for update User")
  class UpdateUser {

    @Test
    @DisplayName("Should return an updated user")
    public void shouldReturnAnUpdateUser() {
      Users mockedUser = Users.builder().id(1L).name("john").email("john@example.com")
          .build();

      when(userService.updateUser(mockedUser)).thenReturn(mockedUser);

      ResponseEntity<Users> response = userController.updateUser(mockedUser);

      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      assertEquals(mockedUser, response.getBody());
    }

    @Test
    @DisplayName("Should return an exception when user not found")
    public void shouldReturnAnException() {
      Users mockedUser = Users.builder().id(1L).name("invalidJohn").email("invalid@gmail.com")
          .build();

      when(userService.updateUser(mockedUser)).thenThrow(new UserNotFoundException());
      assertThrows(UserNotFoundException.class, () -> userService.updateUser(mockedUser));

    }

  }

  @Nested
  @DisplayName("Tests for get all Users")
  class GetAllUsers {

    @Test
    @DisplayName("Should return all users")
    public void shouldReturnAllUsers() {
      List<Users> mockedUsers = Arrays.asList(
          Users.builder().id(1L).name("john").build(),
          Users.builder().id(2L).name("john2").build()
      );

      when(userService.getAllUsers()).thenReturn(mockedUsers);

      ResponseEntity<List<Users>> response = userController.getAllUsers();

      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals(2, response.getBody().size());
      assertEquals(mockedUsers, response.getBody());
    }
  }

  @Nested
  @DisplayName("Tests for get User By Id")
  class GetUserById {

    @Test
    @DisplayName("Should return a user search by id")
    public void shouldReturnAnUser() {
      long userId = 1L;

      Users mockedUser = Users.builder().id(userId).name("john").email("john@example.com")
          .build();

      when(userService.getUserById(userId)).thenReturn(mockedUser);

      ResponseEntity<Users> response = userController.getUserById(userId);

      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals(mockedUser.getId(), response.getBody().getId());
      assertEquals(mockedUser.getName(), response.getBody().getName());
      assertEquals(mockedUser.getEmail(), response.getBody().getEmail());

    }

    @Test
    @DisplayName("Should return an exception when user not found")
    public void shouldReturnAnException() {
      when(userService.getUserById(1L)).thenThrow(new UserNotFoundException());
      assertThrows(UserNotFoundException.class, () -> userService.getUserById(1L));
    }

  }


}