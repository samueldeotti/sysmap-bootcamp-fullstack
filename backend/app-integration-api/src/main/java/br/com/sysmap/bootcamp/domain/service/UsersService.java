package br.com.sysmap.bootcamp.domain.service;

import br.com.sysmap.bootcamp.domain.entities.Users;
import br.com.sysmap.bootcamp.domain.repository.UsersRepository;
import br.com.sysmap.bootcamp.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

/**
 * The type Users service.
 */
@Service
public class UsersService implements UserDetailsService {

  @Autowired
  private UsersRepository usersRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<Users> userDetail = usersRepository.findByEmail(username);

    return userDetail.map(
            users -> new User(users.getEmail(), users.getPassword(), new ArrayList<GrantedAuthority>()))
        .orElseThrow(UserNotFoundException::new);
  }

  /**
   * Find by email users.
   *
   * @param username the username
   * @return the users
   */
  public Users findByEmail(String username) {
    return usersRepository.findByEmail(username)
        .orElseThrow(UserNotFoundException::new);
  }


}