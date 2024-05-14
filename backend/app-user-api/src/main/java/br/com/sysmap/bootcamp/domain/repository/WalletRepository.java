package br.com.sysmap.bootcamp.domain.repository;

import br.com.sysmap.bootcamp.domain.entities.Users;
import br.com.sysmap.bootcamp.domain.entities.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Wallet repository.
 */
@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

  /**
   * Find by users optional.
   *
   * @param users the users
   * @return the optional
   */
  Optional<Wallet> findByUsers(Users users);
}