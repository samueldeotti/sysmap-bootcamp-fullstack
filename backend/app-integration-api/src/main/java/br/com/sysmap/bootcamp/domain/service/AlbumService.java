package br.com.sysmap.bootcamp.domain.service;

import br.com.sysmap.bootcamp.domain.entities.Album;
import br.com.sysmap.bootcamp.domain.entities.Users;
import br.com.sysmap.bootcamp.domain.model.AlbumModel;
import br.com.sysmap.bootcamp.domain.repository.AlbumRepository;
import br.com.sysmap.bootcamp.domain.service.integration.SpotifyApi;
import br.com.sysmap.bootcamp.dto.WalletDto;
import br.com.sysmap.bootcamp.exceptions.AlbumAlreadyInCollectionException;
import br.com.sysmap.bootcamp.exceptions.AlbumNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

import java.io.IOException;
import java.util.List;


/**
 * The type Album service.
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class AlbumService {

  private final Queue queue;
  private final RabbitTemplate template;
  private final SpotifyApi spotifyApi;
  private final AlbumRepository albumRepository;
  private final UsersService usersService;


  /**
   * Gets albums.
   *
   * @param search the search
   * @return the albums
   * @throws IOException            the io exception
   * @throws ParseException         the parse exception
   * @throws SpotifyWebApiException the spotify web api exception
   */
  public List<AlbumModel> getAlbums(String search)
      throws IOException, ParseException, SpotifyWebApiException {
    return this.spotifyApi.getAlbums(search);
  }

  /**
   * Gets collection albums.
   *
   * @return the collection albums
   */
  public List<Album> getCollectionAlbums() {
    Users users = getUser();
    return this.albumRepository.findAllByUsers(users);
  }

  /**
   * Verify if user has albums.
   *
   * @param newAlbum the new album
   */
  public void verifyIfUserHasAlbums(Album newAlbum) {

    List<Album> albums = this.getCollectionAlbums();

    for (Album album : albums) {
      if (album.getIdSpotify().equals(newAlbum.getIdSpotify())) {
        throw new AlbumAlreadyInCollectionException();
      }
    }
  }

  /**
   * Save album album.
   *
   * @param newAlbum the new album
   * @return the album
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public Album saveAlbum(Album newAlbum) {

    verifyIfUserHasAlbums(newAlbum);

    newAlbum.setUsers(getUser());
    Album albumSaved = albumRepository.save(newAlbum);

    WalletDto walletDto = new WalletDto(albumSaved.getUsers().getEmail(),
        albumSaved.getValue());

    this.template.convertAndSend(queue.getName(), walletDto);

    return albumSaved;

  }

  /**
   * Remove album.
   *
   * @param id the id
   */
  @Transactional(propagation = Propagation.REQUIRED)
  public void removeAlbum(Long id) {

    Album album = albumRepository.findById(id)
        .orElseThrow(AlbumNotFoundException::new);
    albumRepository.delete(album);
  }

  private Users getUser() {
    String username = SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal().toString();
    return usersService.findByEmail(username);
  }


}