package br.com.sysmap.bootcamp.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The type Wallet dto.
 */
@Getter
@Setter
public class WalletDto implements Serializable {

  private String email;
  private BigDecimal value;

  /**
   * Instantiates a new Wallet dto.
   *
   * @param email the email
   * @param value the value
   */
  public WalletDto(String email, BigDecimal value) {
    this.email = email;
    this.value = value;
  }

}