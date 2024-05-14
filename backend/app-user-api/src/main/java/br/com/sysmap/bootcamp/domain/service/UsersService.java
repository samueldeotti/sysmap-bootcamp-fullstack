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
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Optional;

/**
 * The type Users service.
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class UsersService implements UserDetailsService {

  private final UsersRepository usersRepository;
  private final PasswordEncoder passwordEncoder;
  private final WalletRepository walletRepository;


  /**
   * Gets all users.
   *
   * @return the all users
   */
  public List<Users> getAllUsers() {
    return usersRepository.findAll();
  }

  /**
   * Gets user by id.
   *
   * @param id the id
   * @return the user by id
   */
  public Users getUserById(Long id) throws UserNotFoundException {
    return usersRepository.findById(id)
        .orElseThrow(UserNotFoundException::new);
  }


  /**
   * Create users.
   *
   * @param user the user
   * @return the users
   * @throws EmailInUseException the email in use exception
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public Users createUser(Users user) throws EmailInUseException {

    Optional<Users> usersOptional = this.usersRepository.findByEmail(user.getEmail());

    if (usersOptional.isPresent()) {
      throw new EmailInUseException();
    }

    user = user.toBuilder().password(this.passwordEncoder.encode(user.getPassword())).build();

    Wallet wallet = Wallet.builder()
        .balance(BigDecimal.valueOf(1000))
        .points(0L)
        .lastUpdate(LocalDateTime.now())
        .users(user)
        .build();

    walletRepository.save(wallet);

    return this.usersRepository.save(user);
  }

  /**
   * Update users.
   *
   * @param updatedUser the updated user
   * @return the users
   * @throws EmailInUseException the email in use exception
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public Users updateUser(Users updatedUser) throws EmailInUseException, UserNotFoundException {
    Users existingUser = this.getUserById(updatedUser.getId());

    Optional<Users> usersOptional = this.usersRepository.findByEmail(updatedUser.getEmail());

    if (usersOptional.isPresent() && !Objects.equals(usersOptional.get().getId(),
        existingUser.getId())) {
      throw new EmailInUseException();
    }

    return usersRepository.save(existingUser.toBuilder()
        .name(updatedUser.getName())
        .email(updatedUser.getEmail())
        .password(this.passwordEncoder.encode(updatedUser.getPassword()))
        .build());
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
    Optional<Users> usersOptional = this.usersRepository.findByEmail(username);

    return usersOptional.map(
            users -> new User(users.getEmail(), users.getPassword(), new ArrayList<GrantedAuthority>()))
        .orElseThrow(UserNotFoundException::new);
  }

  /**
   * Find by email users.
   *
   * @param email the email
   * @return the users
   * @throws EmailInUseException the email in use exception
   */
  public Users findByEmail(String email) throws UserNotFoundException {
    return this.usersRepository.findByEmail(email)
        .orElseThrow(UserNotFoundException::new);
  }

  /**
   * Auth dto.
   *
   * @param authDto the auth dto
   * @return the auth dto
   */
  public AuthDto authUser(AuthDto authDto) {
    Users users = this.findByEmail(authDto.getEmail());

    if (!this.passwordEncoder.matches(authDto.getPassword(), users.getPassword())) {
      throw new RuntimeException("Invalid password");
    }

    String password = users.getEmail() + ":" + users.getPassword();

    return AuthDto.builder().email(users.getEmail()).token(
        Base64.getEncoder().withoutPadding().encodeToString(password.getBytes())
    ).id(users.getId()).build();
  }


}