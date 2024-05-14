package br.com.sysmap.bootcamp.domain.service;

import br.com.sysmap.bootcamp.domain.entities.Users;
import br.com.sysmap.bootcamp.domain.entities.Wallet;
import br.com.sysmap.bootcamp.domain.repository.UsersRepository;
import br.com.sysmap.bootcamp.domain.repository.WalletRepository;
import br.com.sysmap.bootcamp.dto.AuthDto;
import br.com.sysmap.bootcamp.exceptions.EmailInUseException;
import br.com.sysmap.bootcamp.exceptions.UserNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UsersServiceTest {

  @Autowired
  private UsersService usersService;

  @MockBean
  private UsersRepository usersRepository;

  @MockBean
  private WalletRepository walletRepository;

  @Nested
  @DisplayName("Tests for create user")
  class CreateUser {

    @Test
    @DisplayName("Should create a user")
    public void shouldCreateAUser() {
      Users user = Users.builder().email("john@example.com").password("password").build();

      when(usersRepository.findByEmail(user.getEmail())).thenReturn(java.util.Optional.empty());
      when(usersRepository.save(any(Users.class))).thenReturn(user);
      when(walletRepository.save(any(Wallet.class))).thenReturn(Wallet.builder().build());

      Users userUpdated = usersService.createUser(user);

      assertEquals(user.getId(), userUpdated.getId());
      assertEquals(user.getName(), userUpdated.getName());
      assertEquals(user.getEmail(), userUpdated.getEmail());
      assertEquals(user.getPassword(), userUpdated.getPassword());

    }

    @Test
    @DisplayName("Should return an exception when email is already in use")
    public void shouldReturnAnExceptionWhenEmailIsAlreadyInUse() {
      Users user = Users.builder().email("john@example.com").password("password").build();
      when(usersRepository.findByEmail(user.getEmail())).thenReturn(java.util.Optional.of(user));
      assertThrows(EmailInUseException.class, () -> usersService.createUser(user));

    }

  }

  @Nested
  @DisplayName("Tests for user authentication")
  class AuthTests {

    @Test
    @DisplayName("Should authenticate user")
    public void shouldAuthenticateUser() {
      String email = "john@example.com";
      String password = "password";
      BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      String encodedPassword = passwordEncoder.encode(password);

      Users mockedUser = Users.builder().email(email).password(encodedPassword).build();

      when(usersRepository.findByEmail(email)).thenReturn(Optional.of(mockedUser));

      AuthDto authDto = AuthDto.builder().email(email).password(password).build();
      AuthDto authenticatedUser = usersService.authUser(authDto);

      assertEquals(email, authenticatedUser.getEmail());
    }

    @Test
    @DisplayName("Should throw exception when authenticating with invalid password")
    public void shouldThrowExceptionWhenAuthenticatingWithInvalidPassword() {
      String email = "john@example.com";
      String password = "password";
      String invalidPassword = "invalid_password";
      Users mockedUser = Users.builder().email(email).password(password).build();

      when(usersRepository.findByEmail(email)).thenReturn(Optional.of(mockedUser));

      AuthDto authDto = new AuthDto(email, invalidPassword);

      assertThrows(RuntimeException.class, () -> usersService.authUser(authDto));
    }

  }

  @Nested
  @DisplayName("Tests for update user")
  class UpdateUser {

    @Test
    @DisplayName("Should update a user")
    public void shouldUpdateAUser() {
      Users mockedUser = Users.builder().id(1L).email("john@example.com")
          .password("password")
          .build();
      when(usersRepository.findById(1L)).thenReturn(java.util.Optional.of(mockedUser));
      when(usersRepository.findByEmail(mockedUser.getEmail())).thenReturn(
          java.util.Optional.empty());
      when(usersRepository.save(any(Users.class))).thenReturn(mockedUser);

      Users userUpdated = usersService.updateUser(mockedUser);

      assertEquals(mockedUser.getId(), userUpdated.getId());
      assertEquals(mockedUser.getName(), userUpdated.getName());
      assertEquals(mockedUser.getEmail(), userUpdated.getEmail());
      assertEquals(mockedUser.getPassword(), userUpdated.getPassword());

    }

    @Test
    @DisplayName("Should return an exception when userId does not exist")
    public void shouldReturnAnExceptionWhenUserNotFound() {

      Users users = Users.builder().id(1L).email("john@example.com").password("password")
          .name("john")
          .build();

      when(usersRepository.findById(1L)).thenReturn(java.util.Optional.empty());
      assertThrows(UserNotFoundException.class, () -> usersService.updateUser(users));
    }

  }


  @Nested
  @DisplayName("Tests for get all users")
  class GetAllUsers {

    @Test
    @DisplayName("Should get all users")
    public void shouldGetAllUsers() {

      List<Users> mockedUsers = List.of(
          Users.builder().id(1L).name("john").email("john@example.com").password("password")
              .build(),
          Users.builder().id(2L).name("john2").email("john@example.com").password("password2")
              .build()
      );

      when(usersRepository.findAll()).thenReturn(mockedUsers);

      List<Users> usersList = usersService.getAllUsers();

      assertEquals(2, usersList.size());
      assertEquals(mockedUsers.get(0).getId(), usersList.get(0).getId());
      assertEquals(mockedUsers.get(0).getName(), usersList.get(0).getName());
      assertEquals(mockedUsers.get(0).getPassword(), usersList.get(0).getPassword());
      assertEquals(mockedUsers.get(1).getId(), usersList.get(1).getId());
      assertEquals(mockedUsers.get(1).getName(), usersList.get(1).getName());
      assertEquals(mockedUsers.get(1).getPassword(), usersList.get(1).getPassword());

    }

  }

  @Nested
  @DisplayName("Tests for get user by id")
  class GetUserById {

    @Test
    @DisplayName("Should get user by id")
    public void shouldGetUserById() {

      Users mockedUser = Users.builder().id(1L).name("john").email("john@example.com").build();

      when(usersRepository.findById(1L)).thenReturn(
          java.util.Optional.of(mockedUser));

      Users user = usersService.getUserById(1L);

      assertEquals(mockedUser.getId(), user.getId());
      assertEquals(mockedUser.getName(), user.getName());
      assertEquals(mockedUser.getEmail(), user.getEmail());
    }

    @Test
    @DisplayName("Should return an exception when findById not found user")
    public void shouldReturnAnExceptionWhenUserNotFound() {
      when(usersRepository.findById(1L)).thenReturn(java.util.Optional.empty());
      assertThrows(UserNotFoundException.class, () -> usersService.getUserById(1L));
    }

  }

  @Nested
  @DisplayName("Tests for find by user")
  class FindByUser {

    @Test
    @DisplayName("Should load by username")
    public void shouldLoadByUsername() {
      Users mockedUser = Users.builder().id(1L).email("john@example.com")
          .password("password")
          .build();
      when(usersRepository.findByEmail(mockedUser.getEmail())).thenReturn(
          java.util.Optional.of(mockedUser));
      assertEquals(mockedUser.getEmail(),
          usersService.loadUserByUsername(mockedUser.getEmail()).getUsername());

    }

    @Test
    @DisplayName("Should return an exception when loadByUsername not found user")
    public void shouldReturnAnExceptionWhenLoadByUsernameNotFound() {
      Users mockedUser = Users.builder().id(1L).email("john@example.com")
          .password("password")
          .build();
      when(usersRepository.findByEmail(mockedUser.getEmail())).thenReturn(
          java.util.Optional.empty());
      assertThrows(UserNotFoundException.class,
          () -> usersService.loadUserByUsername(mockedUser.getEmail()));
    }


  }

  @Nested
  @DisplayName("Tests for user email")
  class EmailTests {

    @Test
    @DisplayName("Should find by email")
    public void shouldFindByEmail() {
      Users mockedUser = Users.builder().id(1L).email("john@example.com")
          .password("password")
          .build();

      when(usersRepository.findByEmail(mockedUser.getEmail())).thenReturn(
          java.util.Optional.of(mockedUser));

      Users user = usersService.findByEmail(mockedUser.getEmail());

      assertEquals(mockedUser.getId(), user.getId());
      assertEquals(mockedUser.getEmail(), user.getEmail());
      assertEquals(mockedUser.getPassword(), user.getPassword());

    }

    @Test
    @DisplayName("Should return an exception when email is not found")
    public void shouldReturnAnExceptionWhenEmailIsNotFound() {
      Users mockedUser = Users.builder().id(1L).email("notFound@gmail.com")
          .password("password")
          .build();
      when(usersRepository.findByEmail(mockedUser.getEmail())).thenReturn(
          java.util.Optional.empty());
      assertThrows(UserNotFoundException.class,
          () -> usersService.findByEmail(mockedUser.getEmail()));
    }

  }


}