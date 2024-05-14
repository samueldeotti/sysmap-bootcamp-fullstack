package br.com.sysmap.bootcamp.domain.service.integration;


import br.com.sysmap.bootcamp.domain.mapper.AlbumMapper;
import br.com.sysmap.bootcamp.domain.model.AlbumModel;
import com.neovisionaries.i18n.CountryCode;
import java.math.RoundingMode;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * The type Spotify api.
 */
@Service
public class SpotifyApi {

  private se.michaelthelin.spotify.SpotifyApi spotifyApi = new se.michaelthelin.spotify.SpotifyApi.Builder()
      .setClientId("b8a9625c99a44a5894b986bd7ec1aca7")
      .setClientSecret("3c24b8130486423dad3cba36de136994")
      .build();

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

    spotifyApi.setAccessToken(getToken());

    List<AlbumModel> albums = fetchAlbumsFromSpotify(search);
    setRandomValueToAlbums(albums);

    return albums;
  }

  /**
   * Fetch albums from spotify list.
   *
   * @param search the search
   * @return the list
   * @throws IOException            the io exception
   * @throws ParseException         the parse exception
   * @throws SpotifyWebApiException the spotify web api exception
   */
  public List<AlbumModel> fetchAlbumsFromSpotify(String search)
      throws IOException, ParseException, SpotifyWebApiException {
    return AlbumMapper.INSTANCE.toModel(spotifyApi.searchAlbums(search).market(CountryCode.BR)
        .limit(30)
        .build().execute().getItems());
  }

  private void setRandomValueToAlbums(List<AlbumModel> albums) {
    albums.forEach(album -> album.setValue(
        generateRandomValueInRange(new BigDecimal("12.00"), new BigDecimal("100.00"))));
  }

  private BigDecimal generateRandomValueInRange(BigDecimal min, BigDecimal max) {
    BigDecimal randomBigDecimal = min.add(
        BigDecimal.valueOf(Math.random()).multiply(max.subtract(min)));
    return randomBigDecimal.setScale(2, RoundingMode.HALF_UP);
  }


  /**
   * Gets token.
   *
   * @return the token
   * @throws IOException            the io exception
   * @throws ParseException         the parse exception
   * @throws SpotifyWebApiException the spotify web api exception
   */
  public String getToken() throws IOException, ParseException, SpotifyWebApiException {
    ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
    return clientCredentialsRequest.execute().getAccessToken();
  }

}