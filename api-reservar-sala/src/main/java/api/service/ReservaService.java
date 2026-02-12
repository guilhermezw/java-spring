package api.service;

import api.dto.ReservaRequestDTO;
import api.dto.ReservaResponseDTO;
import api.exception.ErroBuscarException;
import api.exception.HorariosConflitantesException;
import api.exception.InversaoHorario;
import api.exception.MinutosException;
import api.mapper.ReservaMapper;
import api.model.ReservaModel;
import api.model.SalaModel;
import api.repository.ReservaRepository;
import api.repository.SalaRepository;
import org.springframework.stereotype.Service;

import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservaService {

    private ReservaRepository reservaRepository;
    private SalaRepository salaRepository;
    private ReservaMapper reservaMapper;

    public ReservaService(ReservaRepository reservaRepository, SalaRepository salaRepository, ReservaMapper reservaMapper) {
        this.reservaRepository = reservaRepository;
        this.salaRepository = salaRepository;
        this.reservaMapper = reservaMapper;
    }

    public List<ReservaResponseDTO> listarReserva(){
        List<ReservaModel> reservas = reservaRepository.findAll();
        return reservaMapper.toResponseDtoList(reservas);
    }

    public ReservaResponseDTO buscarPorId(Long id){
        ReservaModel reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ErroBuscarException("Essa reserva não foi encontrada."));
        return reservaMapper.toResponseDto(reserva);
    }

    public ReservaModel salvarReserva(ReservaRequestDTO reservaDTO){
        SalaModel sala  = salaRepository.findById(reservaDTO.getSalaId())
                .orElseThrow(() -> new ErroBuscarException("Essa sala não foi encontrada."));

        if (!reservaDTO.getInicio().isBefore(reservaDTO.getFim())) {
            throw new InversaoHorario("Horário inicial deve ser antes do horário final.");
        }

        if(reservaRepository.consultarHorariosConflitantes(reservaDTO.getSalaId() , reservaDTO.getInicio() , reservaDTO.getFim()).isPresent()){
            throw new HorariosConflitantesException("Conflito de horário: sala já reservada nesse período.");
        }

        long diferencaEmMinutos = ChronoUnit.MINUTES.between(reservaDTO.getInicio() , reservaDTO.getFim());

        if(diferencaEmMinutos < 30){
            throw new MinutosException("A reserva deve ter no mínimo 30 minutos.");
        }

        ReservaModel reserva = reservaMapper.toEntity(reservaDTO);
        reserva.setSala(sala);
        return reservaRepository.save(reserva);
    }

    public ReservaModel atualizarReserva(Long id , ReservaRequestDTO reservaDTO){
        ReservaModel reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new ErroBuscarException("Essa reserva não foi encontrada."));

        SalaModel sala = salaRepository.findById(reservaDTO.getSalaId())
                .orElseThrow(() -> new ErroBuscarException("Essa sala não foi encontrada."));

        if (!reservaDTO.getInicio().isBefore(reservaDTO.getFim())) {
            throw new InversaoHorario("Horário inicial deve ser antes do horário final");
        }

        long diferencaEmMinutos = ChronoUnit.MINUTES.between(reservaDTO.getInicio() , reservaDTO.getFim());

        if(diferencaEmMinutos < 30){
            throw new MinutosException("A reserva deve ter no mínimo 30 minutos");
        }

        reservaMapper.atualizarReserva(reservaDTO , reserva);
        reserva.setSala(sala);
        return reservaRepository.save(reserva);
    }

    public void deletarReserva(Long id){
        if(reservaRepository.existsById(id)){
            reservaRepository.deleteById(id);
        } else {
            throw new ErroBuscarException("Essa reserva não foi encontrada");
        }
    }


}
