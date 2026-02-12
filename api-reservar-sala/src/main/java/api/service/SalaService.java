package api.service;

import api.dto.SalaRequestDTO;
import api.dto.SalaResponseDTO;
import api.exception.ErroBuscarException;
import api.exception.RegistroDuplicadoException;
import api.mapper.SalaMapper;
import api.model.SalaModel;
import api.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SalaService {

    private SalaRepository salaRepository;
    private SalaMapper salaMapper;

    public SalaService(SalaRepository salaRepository, SalaMapper salaMapper) {
        this.salaRepository = salaRepository;
        this.salaMapper = salaMapper;
    }

    public List<SalaResponseDTO> listarSala(){
        List<SalaModel> salas = salaRepository.findAll();
        return salaMapper.toResponseDtoList(salas);
    }

    public SalaResponseDTO buscarPorId(Long id){
        SalaModel sala = salaRepository.findById(id)
                .orElseThrow(() -> new ErroBuscarException("Essa sala não foi encontrada."));
        return salaMapper.toResponseDto(sala);
    }

    public SalaModel salvarSala(SalaRequestDTO salaDTO){
        if(salaRepository.findByNome(salaDTO.getNome()).isPresent()){
            throw new RegistroDuplicadoException("Essa sala já foi cadastrada.");
        }
        SalaModel entidade  = salaMapper.ToEntity(salaDTO);
        return salaRepository.save(entidade);
    }

    public SalaModel atualizarSala(Long id , SalaRequestDTO salaDTO){
        SalaModel sala = salaRepository.findById(id)
                .orElseThrow(() -> new ErroBuscarException("Essa sala não foi encontrada."));

        salaMapper.atualizarSala(salaDTO , sala);
        return salaRepository.save(sala);
    }

    public void deletarSala(Long id){
        if(salaRepository.existsById(id)){
            salaRepository.deleteById(id);
        } else {
            throw new ErroBuscarException("Essa sala não foi encontrada.");
        }
    }
}
