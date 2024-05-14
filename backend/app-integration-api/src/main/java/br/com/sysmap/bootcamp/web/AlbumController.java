package br.com.sysmap.bootcamp.web;


import br.com.sysmap.bootcamp.config.swaggerInterfaces.AlbumSwaggerApi;
import br.com.sysmap.bootcamp.domain.entities.Album;
import br.com.sysmap.bootcamp.domain.model.AlbumModel;
import br.com.sysmap.bootcamp.domain.service.AlbumService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;


import java.io.IOException;
import java.util.List;

/**
 * The type Album controller.
 */
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@RestController
@RequestMapping("/albums")
public class AlbumController implements AlbumSwaggerApi {

  private final AlbumService albumService;


  /**
   * Save album response entity.
   *
   * @param album the album
   * @return the response entity
   */
  @PostMapping("/sale")
  public ResponseEntity<Album> saveAlbum(@RequestBody @Valid Album album) {
    return ResponseEntity.ok(albumService.saveAlbum(album));
  }

  /**
   * Gets collection albums.
   *
   * @return the collection albums
   */
  @GetMapping("/my-collection")
  public ResponseEntity<List<Album>> getCollectionAlbums() {
    return ResponseEntity.ok(albumService.getCollectionAlbums());
  }

  /**
   * Gets albums.
   *
   * @param search the search
   * @return the albums
   * @throws IOException            the io exception
   * @throws ParseException         the parse exception
   * @throws SpotifyWebApiException the spotify web api exception
   */
  @Operation(summary = "Get all albums from Spotify service by Text parameter", tags = "Albums")
  @GetMapping("/all")
  public ResponseEntity<List<AlbumModel>> getAlbumsByName(
      @RequestParam("search") @Valid String search)
      throws IOException, ParseException, SpotifyWebApiException {
    return ResponseEntity.ok(albumService.getAlbums(search));
  }

  /**
   * Remove album response entity.
   *
   * @param id the id
   * @return the response entity
   */
  @Operation(summary = "Remove an album by ID", tags = "Albums")
  @DeleteMapping("/remove/{id}")
  public ResponseEntity<Void> removeAlbum(@PathVariable @Valid Long id) {
    albumService.removeAlbum(id);
    return ResponseEntity.noContent().build();
  }


}