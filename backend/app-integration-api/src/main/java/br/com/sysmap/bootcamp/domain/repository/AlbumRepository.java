package br.com.sysmap.bootcamp.domain.repository;

import br.com.sysmap.bootcamp.domain.entities.Album;
import br.com.sysmap.bootcamp.domain.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Album repository.
 */
@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {

  /**
   * Find all by users list.
   *
   * @param users the users
   * @return the list
   */
  List<Album> findAllByUsers(Users users);
}