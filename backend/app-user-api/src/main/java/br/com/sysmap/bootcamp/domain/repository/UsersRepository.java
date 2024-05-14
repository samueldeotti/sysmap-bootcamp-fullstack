package br.com.sysmap.bootcamp.domain.repository;

import br.com.sysmap.bootcamp.domain.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * The interface Users repository.
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {

  /**
   * Find by email optional.
   *
   * @param email the email
   * @return the optional
   */
  Optional<Users> findByEmail(String email);
}