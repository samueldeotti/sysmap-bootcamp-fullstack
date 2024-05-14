package br.com.sysmap.bootcamp.domain.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * The type Wallet.
 */
@Entity
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "WALLET")
public class Wallet {

  @Schema(description = "Wallet's id", example = "1")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Schema(description = "Wallet's balance", example = "100.00")
  @Column(name = "balance")
  private BigDecimal balance;

  @Schema(description = "Wallet's points", example = "100")
  @Column(name = "points")
  private Long points;

  @Schema(description = "Wallet's last update", example = "2021-12-31T23:59:59")
  @Column(name = "last_update")
  private LocalDateTime lastUpdate;

  @OneToOne
  @JoinColumn(name = "user_id")
  private Users users;


}