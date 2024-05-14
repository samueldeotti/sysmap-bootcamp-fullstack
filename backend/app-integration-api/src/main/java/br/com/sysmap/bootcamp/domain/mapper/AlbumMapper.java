package br.com.sysmap.bootcamp.domain.mapper;


import br.com.sysmap.bootcamp.domain.model.AlbumModel;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

import java.util.List;

/**
 * The interface Album mapper.
 */
@Named("AlbumMapper")
@Mapper
public interface AlbumMapper {

  /**
   * The constant INSTANCE.
   */
  AlbumMapper INSTANCE = Mappers.getMapper(AlbumMapper.class);

  /**
   * To model list.
   *
   * @param algumSimplifiedPaging the algum simplified paging
   * @return the list
   */
  List<AlbumModel> toModel(AlbumSimplified[] algumSimplifiedPaging);
}