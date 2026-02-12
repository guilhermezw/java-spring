package api.mapper;

import api.dto.ReservaRequestDTO;
import api.dto.ReservaResponseDTO;
import api.model.ReservaModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReservaMapper {

    ReservaModel toEntity(ReservaRequestDTO reservaRequestDTO);
    ReservaResponseDTO toResponseDto(ReservaModel reservaModel);

    List<ReservaResponseDTO> toResponseDtoList(List<ReservaModel> reservas);

    @Mapping(target = "id" , ignore = true)
    void atualizarReserva(ReservaRequestDTO reservaRequestDTO , @MappingTarget ReservaModel reservaModel);
}
