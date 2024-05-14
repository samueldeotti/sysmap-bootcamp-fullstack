package br.com.sysmap.bootcamp.domain.service;

import br.com.sysmap.bootcamp.domain.entities.Album;
import br.com.sysmap.bootcamp.domain.entities.Users;
import br.com.sysmap.bootcamp.domain.model.AlbumModel;
import br.com.sysmap.bootcamp.domain.repository.AlbumRepository;
import br.com.sysmap.bootcamp.domain.service.integration.SpotifyApi;
import br.com.sysmap.bootcamp.exceptions.AlbumAlreadyInCollectionException;
import br.com.sysmap.bootcamp.exceptions.AlbumNotFoundException;
import java.io.IOException;
import java.util.List;
import org.apache.hc.core5.http.ParseException;
import org.checkerframework.checker.units.qual.N;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import se.michaelthelin.spotify.enums.AlbumType;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class AlbumServiceTest {

  @MockBean
  Queue queue;

  @MockBean
  RabbitTemplate template;

  @MockBean
  SpotifyApi spotifyApi;

  @MockBean
  AlbumRepository albumRepository;

  @Mock
  private UsersService usersService;

  @Autowired
  private AlbumService albumService;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
    albumService = new AlbumService(queue, template, spotifyApi, albumRepository, usersService);
  }

  @Nested
  @DisplayName("Test save album")
  class SaveAlbum {

    @Test
    @DisplayName("Should save an album")
    public void shouldSaveAnAlbum() {

      Authentication auth = mock(Authentication.class);
      when(auth.getPrincipal()).thenReturn("john.doe");

      SecurityContextHolder.getContext().setAuthentication(auth);

      Users users = Mockito.mock(Users.class);

      Album mockedAlbum = Album.builder()
          .id(1L)
          .idSpotify("12345")
          .name("firstAlbum")
          .value(BigDecimal.valueOf(10))
          .users(users)
          .build();

      Mockito.when(albumRepository.save(any(Album.class))).thenReturn(mockedAlbum);

      Album album = Album.builder()
          .id(1L)
          .idSpotify("12345")
          .name("firstAlbum")
          .value(BigDecimal.valueOf(10))
          .build();

      Album albumSaved = albumService.saveAlbum(album);

      assertEquals(album.getId(), albumSaved.getId());
      assertEquals(album.getIdSpotify(), albumSaved.getIdSpotify());
      assertEquals(album.getName(), albumSaved.getName());
      assertEquals(album.getValue(), albumSaved.getValue());

      Mockito.verify(albumRepository, Mockito.times(1)).save(any(Album.class));
    }

    @Test
    @DisplayName("Should not save an album that is already in the collection")
    public void shouldNotSaveAnAlbumThatIsAlreadyInTheCollection() {

      Authentication auth = mock(Authentication.class);
      when(auth.getPrincipal()).thenReturn("john.doe");

      SecurityContextHolder.getContext().setAuthentication(auth);

      Album mockedAlbum = Album.builder()
          .id(1L)
          .idSpotify("12345")
          .value(BigDecimal.valueOf(100.00))
          .build();

      List<Album> albums = List.of(mockedAlbum);

      Mockito.when(albumRepository.findAllByUsers(any())).thenReturn(albums);

      Album album = Album.builder()
          .id(1L)
          .idSpotify("12345")
          .value(BigDecimal.valueOf(100.00))
          .build();

      assertThrows(AlbumAlreadyInCollectionException.class, () -> albumService.saveAlbum(album));

      Mockito.verify(albumRepository, Mockito.times(0)).save(any(Album.class));

    }
  }

  @Nested
  @DisplayName("Test get collection albums")
  class GetUserAlbumCollection {

    @Test
    @DisplayName("Should get the user collection albums")
    public void shouldGetTheUserCollectionAlbums() {

      when(albumRepository.findAllByUsers(any())).thenReturn(
          List.of(
              Album.builder().id(1L).idSpotify("1234").name("test")
                  .value(BigDecimal.valueOf(100.00))
                  .build(),
              Album.builder().id(2L).idSpotify("234").name("test2")
                  .value(BigDecimal.valueOf(100.00))
                  .build()));

      Authentication auth = mock(Authentication.class);
      when(auth.getPrincipal()).thenReturn("testUser");

      SecurityContextHolder.getContext().setAuthentication(auth);

      assertEquals(2, albumService.getCollectionAlbums().size());
      assertEquals("test", albumService.getCollectionAlbums().get(0).getName());
      assertEquals("test2", albumService.getCollectionAlbums().get(1).getName());
      assertEquals(1L, albumService.getCollectionAlbums().get(0).getId());
      assertEquals(2L, albumService.getCollectionAlbums().get(1).getId());

    }

    @Test
    @DisplayName("Should return no albums when user has no albums in collection")
    public void shouldReturnNoAlbumsWhenUserHasNoAlbumsInCollection() {
      when(albumRepository.findAllByUsers(any())).thenReturn(List.of());

      Authentication auth = mock(Authentication.class);
      when(auth.getPrincipal()).thenReturn("john.doe");

      SecurityContextHolder.getContext().setAuthentication(auth);

      assertEquals(0, albumService.getCollectionAlbums().size());
    }

  }

  @Nested
  @DisplayName("Test get albums by search parameter")
  class GetAlbumByParameter {

    @Test
    @DisplayName("Should return albums by search paramenter")
    public void shouldReturnAlbumsBySearchParameters()
        throws IOException, ParseException, SpotifyWebApiException {
      AlbumModel album1 = new AlbumModel();
      AlbumModel album2 = new AlbumModel();
      AlbumModel album3 = new AlbumModel();

      ArtistSimplified artist1 = new ArtistSimplified.Builder().setName("Ana").build();
      Image image1 = new Image.Builder().setUrl("http://test.com").build();
      ArtistSimplified artist2 = new ArtistSimplified.Builder().setName("Ana").build();
      ArtistSimplified artist3 = new ArtistSimplified.Builder().setName("Pedro").build();

      album1.setAlbumType(AlbumType.valueOf("SINGLE"));
      album1.setId("11");
      album1.setValue(BigDecimal.valueOf(30.00));
      album1.setArtists(new ArtistSimplified[]{artist1});
      album1.setReleaseDate("2024-02-29");
      album1.setExternalUrl(null);
      album1.setImages(new Image[]{image1});
      album2.setAlbumType(AlbumType.valueOf("SINGLE"));
      album2.setId("22");
      album2.setArtists(new ArtistSimplified[]{artist2});
      album2.setValue(BigDecimal.valueOf(50.00));
      album1.setReleaseDate("2024-02-30");
      album3.setAlbumType(AlbumType.valueOf("SINGLE"));
      album3.setId("33");
      album1.setReleaseDate("2024-02-28");
      album3.setArtists(new ArtistSimplified[]{artist3});
      album3.setValue(BigDecimal.valueOf(100.00));

      List<AlbumModel> mockedAlbum = List.of(album1, album2);

      Mockito.when(spotifyApi.getAlbums("ana")).thenReturn(mockedAlbum);

      List<AlbumModel> albums = albumService.getAlbums("ana");

      assertEquals(2, albums.size());
      assertEquals(album1.getId(), albums.get(0).getId());
      assertEquals(album1.getValue(), albums.get(0).getValue());
      assertEquals(album1.getAlbumType(), albums.get(0).getAlbumType());
      assertEquals(album1.getReleaseDate(), albums.get(0).getReleaseDate());
      assertEquals(album1.getArtists(), albums.get(0).getArtists());
      assertEquals(album1.getImages(), albums.get(0).getImages());
      assertNull(albums.get(0).getExternalUrl());
      assertEquals(album2.getId(), albums.get(1).getId());
      assertEquals(album2.getValue(), albums.get(1).getValue());
      assertEquals(album2.getAlbumType(), albums.get(1).getAlbumType());

    }

    @Test
    @DisplayName("Should return an exception when trying to get albums from search parameter")
    public void shouldReturnAnExceptionWhenTryingToGetAlbumsFromSearchParameter()
        throws IOException, ParseException, SpotifyWebApiException {

      when(spotifyApi.getAlbums(anyString())).thenThrow(SpotifyWebApiException.class);

      assertThrows(SpotifyWebApiException.class, () -> albumService.getAlbums("search"));
    }

  }


  @Nested
  @DisplayName("Test remove album")
  class RemoveAlbum {

    @Test
    @DisplayName("Should remove an album")
    public void shouldRemoveAnAlbum() {

      Album mockedAlbum = Album.builder()
          .id(1L)
          .idSpotify("12345")
          .value(BigDecimal.valueOf(10.00))
          .build();

      Mockito.when(albumRepository.findById(1L)).thenReturn(Optional.of(mockedAlbum));

      albumService.removeAlbum(1L);

      Mockito.verify(albumRepository, Mockito.times(1)).delete(mockedAlbum);
    }

    @Test
    @DisplayName("Should not remove an album that does not exist")
    public void shouldNotRemoveAnAlbumThatDoesNotExist() {
      Mockito.when(albumRepository.findById(1L)).thenReturn(Optional.empty());

      assertThrows(AlbumNotFoundException.class, () -> albumService.removeAlbum(1L));

      Mockito.verify(albumRepository, Mockito.times(0)).delete(any(Album.class));
    }

  }

}