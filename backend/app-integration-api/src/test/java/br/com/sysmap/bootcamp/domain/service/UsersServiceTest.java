package br.com.sysmap.bootcamp.domain.service;

import br.com.sysmap.bootcamp.domain.entities.Users;
import br.com.sysmap.bootcamp.domain.repository.UsersRepository;
import br.com.sysmap.bootcamp.exceptions.UserNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class UsersServiceTest {

  @MockBean
  UsersRepository usersRepository;

  @Autowired
  UsersService usersService;

  @BeforeEach
  public void setup() {
    Mockito.when(usersRepository.findByEmail(anyString())).thenReturn(null);
  }

  @Nested
  @DisplayName("Test load user by username")
  class LoadUserByUsername {

    @Test
    @DisplayName("Should load user by username")
    void testLoadUserByUsername() {
      String username = "test@example.com";
      Users mockeduser = Users.builder().id(1L).email(username).password("password").build();
      Mockito.when(usersRepository.findByEmail(username)).thenReturn(Optional.of(mockeduser));

      UserDetails userDetails = usersService.loadUserByUsername(username);

      assertEquals(mockeduser.getEmail(), userDetails.getUsername());
      assertEquals(mockeduser.getPassword(), userDetails.getPassword());

    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user not found")
    void testLoadUserByUsernameNotFound() {
      Mockito.when(usersRepository.findByEmail(anyString())).thenReturn(Optional.empty());

      assertThrows(UserNotFoundException.class,
          () -> usersService.loadUserByUsername("nonExistentUser@example.com"));
    }

  }

  @Nested
  @DisplayName("Test find user by email")
  class FindByEmail {

    @Test
    @DisplayName("Should find user by email")
    void testFindByEmail() {
      String email = "test@example.com";
      Users mockeduser = Users.builder().email(email).password("password").build();

      Mockito.when(usersRepository.findByEmail(email))
          .thenReturn(Optional.ofNullable(mockeduser));

      Users foundUser = usersService.findByEmail(email);

      assertNotNull(foundUser);
      assertNotNull(mockeduser);
      assertEquals(mockeduser.getId(), foundUser.getId());
      assertEquals(mockeduser.getEmail(), foundUser.getEmail());
      assertEquals(mockeduser.getPassword(), foundUser.getPassword());

    }

    @Test
    @DisplayName("Should throw UsernameNotFoundException when user not found")
    void testFindByEmailNotFound() {
      String email = "test@example.com";

      Mockito.when(usersRepository.findByEmail(email)).thenReturn(Optional.empty());

      assertThrows(UserNotFoundException.class,
          () -> usersService.findByEmail(email));

    }

  }


}