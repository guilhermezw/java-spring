package api.mapper;

import api.dto.SalaRequestDTO;
import api.dto.SalaResponseDTO;
import api.model.SalaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SalaMapper {

    SalaModel ToEntity(SalaRequestDTO salaRequestDTO);
    SalaResponseDTO toResponseDto(SalaModel salaModel);

    // Listar sala com mapper
    List<SalaResponseDTO> toResponseDtoList(List<SalaModel> salas);

    // Atualizar sala com mapper
    @Mapping(target = "id" , ignore = true)
    void atualizarSala(SalaRequestDTO salaRequestDTO , @MappingTarget SalaModel salaModel);
}
