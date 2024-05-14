package br.com.sysmap.bootcamp.domain.service;

import br.com.sysmap.bootcamp.domain.entities.Users;
import br.com.sysmap.bootcamp.domain.entities.Wallet;
import br.com.sysmap.bootcamp.domain.repository.WalletRepository;
import br.com.sysmap.bootcamp.dto.WalletDto;
import br.com.sysmap.bootcamp.exceptions.UserNotFoundException;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * The type Wallet service.
 */
@RequiredArgsConstructor
@Service
public class WalletService {

  private final UsersService usersService;
  private final WalletRepository walletRepository;

  /**
   * Debit.
   *
   * @param walletDto the wallet dto
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public void debit(WalletDto walletDto) {
    Users users = usersService.findByEmail(walletDto.getEmail());
    Wallet wallet = walletRepository.findByUsers(users).orElseThrow(UserNotFoundException::new);

    wallet.setBalance(wallet.getBalance().subtract(walletDto.getValue()));

    wallet.setPoints(wallet.getPoints() + calculatePoints());

    walletRepository.save(wallet);

  }

  /**
   * Calculate points int.
   *
   * @return the int
   */
  public int calculatePoints() {
    DayOfWeek dayOfWeek = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).getDayOfWeek();

    return switch (dayOfWeek) {
      case MONDAY -> 7;
      case TUESDAY -> 6;
      case WEDNESDAY -> 2;
      case THURSDAY -> 10;
      case FRIDAY -> 15;
      case SATURDAY -> 20;
      case SUNDAY -> 25;
    };
  }

  /**
   * Credit wallet.
   *
   * @param value the value
   * @return the wallet
   */
  public Wallet credit(BigDecimal value) {
    Users users = getUser();
    Wallet wallet = walletRepository.findByUsers(users).orElseThrow(UserNotFoundException::new);
    wallet.setBalance(wallet.getBalance().add(value));

    return walletRepository.save(wallet);
  }

  /**
   * Gets wallet.
   *
   * @return the wallet
   */
  public Wallet getWallet() {
    Users users = getUser();
    return this.walletRepository.findByUsers(users).orElseThrow();
  }

  private Users getUser() {
    String username = SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal().toString();
    return usersService.findByEmail(username);
  }

}