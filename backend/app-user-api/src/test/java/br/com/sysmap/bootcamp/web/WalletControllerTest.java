package br.com.sysmap.bootcamp.web;

import br.com.sysmap.bootcamp.domain.entities.Wallet;
import br.com.sysmap.bootcamp.domain.service.WalletService;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class WalletControllerTest {

  @Autowired
  private WalletController walletController;

  @MockBean
  private WalletService walletService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }


  @Nested
  @DisplayName("Tests for addCredit")
  class AddCredit {

    @Test
    @DisplayName("Should add credit to the wallet")
    public void shouldAddCreditToWallet() {

      Wallet mockedWallet = Wallet.builder().id(1L).balance(new BigDecimal(1000)).build();

      when(walletService.credit(new BigDecimal(100))).thenReturn(mockedWallet);

      ResponseEntity<Wallet> response = walletController.addCredit(new BigDecimal(100));

      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals(new BigDecimal(1000), response.getBody().getBalance());
    }

  }

  @Nested
  @DisplayName("Tests for get Wallet")
  class GetWallet {

    @Test
    @DisplayName("Should return the user wallet")
    public void shouldReturnTheUserWallet() {
      Wallet mockedWallet = Wallet.builder().id(1L).points(0L)
          .balance(new BigDecimal(1000)).build();

      when(walletService.getWallet()).thenReturn(mockedWallet);

      ResponseEntity<Wallet> response = walletController.getWallet();

      assertEquals(HttpStatus.OK, response.getStatusCode());
      assertNotNull(response.getBody());
      assertEquals(new BigDecimal(1000), response.getBody().getBalance());
      assertEquals(0L, response.getBody().getPoints());
      assertEquals(1L, response.getBody().getId());

    }

  }


}