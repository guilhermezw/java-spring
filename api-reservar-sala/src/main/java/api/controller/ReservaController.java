package api.controller;

import api.dto.ReservaRequestDTO;
import api.dto.ReservaResponseDTO;
import api.repository.ReservaRepository;
import api.service.ReservaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    private ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> listarReserva(){
        return ResponseEntity.status(HttpStatus.OK).body(reservaService.listarReserva());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> buscarPorId(@RequestParam Long id){
        return ResponseEntity.status(HttpStatus.OK).body(reservaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Map<String  , Object>> salvarReserva(@RequestBody @Valid ReservaRequestDTO reservaDTO){
        reservaService.salvarReserva(reservaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message" , "Reserva salva" , "success" , true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String , Object>> atualizarReserva(@PathVariable Long id , @RequestBody @Valid ReservaRequestDTO reservaDTO){
        reservaService.atualizarReserva(id,reservaDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message" , "Reserva atualizada" , "success" , true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String , Object>> deletarReserva(@PathVariable Long id){
        reservaService.deletarReserva(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message" , "Reserva deletada" , "success" , true));
    }
}
