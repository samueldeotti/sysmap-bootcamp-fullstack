package br.com.sysmap.bootcamp.web;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.sysmap.bootcamp.domain.entities.Album;
import br.com.sysmap.bootcamp.domain.entities.Users;
import br.com.sysmap.bootcamp.domain.model.AlbumModel;
import br.com.sysmap.bootcamp.domain.service.AlbumService;
import br.com.sysmap.bootcamp.exceptions.AlbumAlreadyInCollectionException;
import br.com.sysmap.bootcamp.exceptions.AlbumNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import org.apache.hc.core5.http.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest
public class AlbumControllerTest {

  @MockBean
  private AlbumService albumService;

  @Autowired
  private AlbumController albumController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }


  @Nested
  @DisplayName("Test albums get by search parameter")
  class GetAlbumsBySearchParameter {

    @Test
    @DisplayName("Should return albums from Search parameter")
    public void shouldReturnAlbumsFromSearchParameter()
        throws IOException, ParseException, SpotifyWebApiException {
      List<AlbumModel> mockedAlbums = Arrays.asList(new AlbumModel(), new AlbumModel());

      Mockito.when(albumService.getAlbums(anyString()))
          .thenReturn(mockedAlbums);

      ResponseEntity<List<AlbumModel>> response = albumController.getAlbumsByName("search");

      assertEquals(200, response.getStatusCode().value());
      assertEquals(2, Objects.requireNonNull(response.getBody()).size());
      assertEquals(mockedAlbums, response.getBody());

    }

    @Test
    @DisplayName("Should return an exception when trying to get albums from search parameter")
    public void shouldReturnAnExceptionWhenTryingToGetAlbumsFromSearchParameter()
        throws IOException, ParseException, SpotifyWebApiException {

      when(albumService.getAlbums(anyString())).thenThrow(SpotifyWebApiException.class);

      assertThrows(SpotifyWebApiException.class, () -> albumService.getAlbums("search"));
    }

  }

  @Nested
  @DisplayName("Test get albums in user collection")
  class GetAlbumsInUserCollection {

    @Test
    @DisplayName("Should return albums in user collection")
    public void shouldReturnAlbumsInCollection() {
      List<Album> mockedAlbums = Arrays.asList(Album.builder().build(), Album.builder().build());

      Mockito.when(albumService.getCollectionAlbums())
          .thenReturn(mockedAlbums);

      ResponseEntity<List<Album>> response = albumController.getCollectionAlbums();

      assertEquals(200, response.getStatusCode().value());
      assertEquals(2, Objects.requireNonNull(response.getBody()).size());
      assertEquals(mockedAlbums, response.getBody());
    }

    @Test
    @DisplayName("Should return no albums when user has no albums in collection")
    public void shouldReturnNoAlbumsWhenUserHasNoAlbumsInCollection() {
      List<Album> mockedAlbums = new ArrayList<Album>();

      when(albumService.getCollectionAlbums()).thenReturn(mockedAlbums);

      ResponseEntity<List<Album>> response = albumController.getCollectionAlbums();

      assertEquals(200, response.getStatusCode().value());
      assertTrue(Objects.requireNonNull(response.getBody()).isEmpty());
    }


  }

  @Nested
  @DisplayName("Tests buy an album")
  class buyAnAlbum {

    @Test
    @DisplayName("Should save an album")
    public void shouldSaveAnAlbum() {
      Album mockedAlbum = Album.builder().idSpotify("password").name("john")
          .value(BigDecimal.valueOf(100))
          .build();

      when(albumService.saveAlbum(any(Album.class))).thenReturn(mockedAlbum);

      ResponseEntity<Album> response = albumController.saveAlbum(mockedAlbum);

      assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
      assertThat(response.getBody()).isEqualTo(mockedAlbum);
    }

    @Test
    @DisplayName("Should throw an exception when trying to save a duplicate album")
    public void shouldThrowExceptionWhenSavingDuplicateAlbum() {
      Album mockedAlbum1 = Album.builder().idSpotify("password").name("john").build();
      Album mockedAlbum2 = Album.builder().idSpotify("password").name("john1").build();

      when(albumService.saveAlbum(mockedAlbum1)).thenReturn(mockedAlbum1);
      when(albumService.saveAlbum(mockedAlbum2)).thenThrow(AlbumAlreadyInCollectionException.class);

      Album savedAlbum = albumService.saveAlbum(mockedAlbum1);
      assertEquals(mockedAlbum1, savedAlbum);

      assertThrows(AlbumAlreadyInCollectionException.class,
          () -> albumService.saveAlbum(mockedAlbum2));
    }

  }


  @Nested
  @DisplayName("Tests remove an album")
  class RemoveAnAlbum {

    @Test
    @DisplayName("Should remove an album")
    public void shouldRemoveAnAlbum() {
      long albumId = 1L;

      doNothing().when(albumService).removeAlbum(anyLong());

      ResponseEntity<Void> response = albumController.removeAlbum(albumId);

      assertEquals(204, response.getStatusCode().value());

      verify(albumService).removeAlbum(albumId);
    }

    @Test
    @DisplayName("Should return an exception when trying to remove an album that does not exist")
    public void shouldReturnAnExceptionWhenTryingToRemoveAnAlbumThatDoesNotExist() {

      when(albumController.removeAlbum(2L)).thenThrow(AlbumNotFoundException.class);
      assertThrows(AlbumNotFoundException.class, () -> albumService.removeAlbum(2L));
    }

  }


}