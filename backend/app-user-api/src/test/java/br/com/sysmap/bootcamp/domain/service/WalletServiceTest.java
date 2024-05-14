package br.com.sysmap.bootcamp.domain.service;

import br.com.sysmap.bootcamp.domain.entities.Users;
import br.com.sysmap.bootcamp.domain.entities.Wallet;
import br.com.sysmap.bootcamp.domain.repository.UsersRepository;
import br.com.sysmap.bootcamp.domain.repository.WalletRepository;
import br.com.sysmap.bootcamp.dto.WalletDto;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.springframework.security.core.Authentication;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class WalletServiceTest {

  @Autowired
  private WalletService walletService;

  @MockBean
  private UsersRepository userRepository;

  @MockBean
  private WalletRepository walletRepository;


  @BeforeEach
  public void setup() {
    MockitoAnnotations.initMocks(this);
    Authentication authentication = mock(Authentication.class);

    when(authentication.getPrincipal()).thenReturn("john@example.com");

    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  @Test
  @DisplayName("Should debit from wallet")
  public void shouldDebitFromWallet() {
    Users mockedUser = Users.builder().id(1L).name("john").email("john@example.com").build();

    Wallet mockedWallet = Wallet.builder().id(1L).balance(BigDecimal.valueOf(1000)).points(0L)
        .users(mockedUser)
        .build();

    WalletDto walletDto = new WalletDto("john@example.com", BigDecimal.valueOf(100));

    int points = walletService.calculatePoints();

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockedUser));
    when(walletRepository.findByUsers(mockedUser)).thenReturn(Optional.of(mockedWallet));

    walletService.debit(walletDto);

    assertThat(mockedWallet.getBalance()).isEqualTo(BigDecimal.valueOf(900));
    assertThat(mockedWallet.getPoints()).isEqualTo(points);
    verify(walletRepository, times(1)).save(mockedWallet);

  }

  @Test
  @DisplayName("Should credit to wallet")
  public void shouldCreditToWallet() {
    Users mockedUser = Users.builder().id(1L).name("john").email("john@example.com").build();

    Wallet mockedWallet = Wallet.builder().id(1L).balance(BigDecimal.valueOf(1000)).points(0L)
        .users(mockedUser)
        .build();

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockedUser));
    when(walletRepository.findByUsers(mockedUser)).thenReturn(Optional.of(mockedWallet));
    when(walletRepository.save(mockedWallet)).thenReturn(mockedWallet);

    Wallet wallet = walletService.credit(BigDecimal.valueOf(100));

    assertEquals(wallet.getBalance(), BigDecimal.valueOf(1100));
    assertEquals(wallet.getPoints(), 0L);
    assertEquals(mockedWallet.getUsers().getId(), wallet.getUsers().getId());
    verify(walletRepository, times(1)).save(mockedWallet);


  }

  @Test
  @DisplayName("Should get wallet")
  public void shouldGetWallet() {
    Users mockedUser = Users.builder().id(1L).name("john").email("john@example.com").build();

    Wallet mockedWallet = Wallet.builder().id(1L).balance(BigDecimal.valueOf(1000)).points(0L)
        .users(mockedUser)
        .build();

    when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(mockedUser));
    when(walletRepository.findByUsers(mockedUser)).thenReturn(Optional.of(mockedWallet));

    Wallet wallet = walletService.getWallet();

    assertEquals(mockedWallet.getId(), wallet.getId());
    assertEquals(mockedWallet.getBalance(), wallet.getBalance());
    assertEquals(mockedWallet.getPoints(), wallet.getPoints());
    assertEquals(mockedWallet.getUsers(), wallet.getUsers());
    assertEquals(mockedWallet.getUsers().getId(), wallet.getUsers().getId());

  }

}