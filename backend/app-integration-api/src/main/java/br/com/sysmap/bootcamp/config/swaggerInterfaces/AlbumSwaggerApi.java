package br.com.sysmap.bootcamp.config.swaggerInterfaces;

import br.com.sysmap.bootcamp.domain.entities.Album;
import br.com.sysmap.bootcamp.domain.model.AlbumModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import org.apache.hc.core5.http.ParseException;


/**
 * The interface Users swagger api.
 */
@Tag(name = "Albums", description = "Albums Api")
public interface AlbumSwaggerApi {

  /**
   * Gets all users.
   *
   * @param album the album
   * @return the all users
   */

  @Operation(summary = "Buy an album", tags = "Albums")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Buy an album and return it"),
      @ApiResponse(responseCode = "401", description = "Unauthorized User | Invalid Token", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Unauthorized User | Invalid Token\"")
          }
      )),
      @ApiResponse(responseCode = "409", description = "Album already in collection", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Album already in collection\"")
          }
      )),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Internal Server Error\"")
          }
      ))
  })
  ResponseEntity<Album> saveAlbum(Album album);


  /**
   * Gets collection albums.
   *
   * @return the collection albums
   */
  @Operation(summary = "Get all albums from my collection", tags = "Albums")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get all albums from user collection"),
      @ApiResponse(responseCode = "401", description = "Unauthorized User | Invalid Token", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Unauthorized User | Invalid Token\"")
          }
      )),
      @ApiResponse(responseCode = "404", description = "User not found", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"User not found\"")
          }
      )),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Internal Server Error\"")
          }
      ))
  })
  ResponseEntity<List<Album>> getCollectionAlbums();

  @Operation(summary = "Get all albums from Spotify service by Text parameter", tags = "Albums")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Get all albums from search parameter"),
      @ApiResponse(responseCode = "400", description = "Invalid parameter", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Invalid parameter\"")
          }
      )),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Internal Server Error\"")
          }
      ))
  })
  ResponseEntity<List<AlbumModel>> getAlbumsByName(String search)
      throws ParseException, SpotifyWebApiException, IOException;

  @Operation(summary = "Remove an album by ID", tags = "Albums")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Remove an album by ID"),
      @ApiResponse(responseCode = "400", description = "Invalid parameter", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Invalid parameter\"")
          }
      )),
      @ApiResponse(responseCode = "401", description = "Unauthorized User | Invalid Token", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Unauthorized User | Invalid Token\"")
          }
      )),
      @ApiResponse(responseCode = "404", description = "Album not found", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Album not found\"")
          }
      )),
      @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @io.swagger.v3.oas.annotations.media.Content(
          schema = @Schema(implementation = String.class),
          examples = {
              @ExampleObject(value = "\"Internal Server Error\"")
          }
      ))
  })
  ResponseEntity<Void> removeAlbum(Long id);

}