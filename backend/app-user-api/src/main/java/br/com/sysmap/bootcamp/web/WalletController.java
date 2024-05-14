package br.com.sysmap.bootcamp.web;

import br.com.sysmap.bootcamp.domain.entities.Wallet;
import br.com.sysmap.bootcamp.domain.service.WalletService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//import br.com.sysmap.bootcamp.config.swaggerInterfaces.WalletSwaggerApi;

/**
 * The type Wallet controller.
 */
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/wallet")
public class WalletController {

  private final WalletService walletService;

  /**
   * Add credit response entity.
   *
   * @param value the value
   * @return the response entity
   */
  @PostMapping("/credit/{value}")
  public ResponseEntity<Wallet> addCredit(
      @PathVariable @Valid @Positive(message = "Value cannot be negative") BigDecimal value) {
    return ResponseEntity.status(HttpStatus.CREATED).body(this.walletService.credit(value));
  }

  /**
   * Wallet response entity.
   *
   * @return the response entity
   */
  @GetMapping
  public ResponseEntity<Wallet> getWallet() {
    return ResponseEntity.ok(this.walletService.getWallet());
  }

}
