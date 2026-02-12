package api.controller;

import api.dto.SalaRequestDTO;
import api.dto.SalaResponseDTO;
import api.service.SalaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/salas")
public class SalaController {

    private SalaService salaService;

    public SalaController(SalaService salaService) {
        this.salaService = salaService;
    }

    @GetMapping
    public ResponseEntity<List<SalaResponseDTO>> listarSala(){
        return ResponseEntity.status(HttpStatus.OK).body(salaService.listarSala());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaResponseDTO> buscarPorId(@RequestParam Long id){
        return ResponseEntity.status(HttpStatus.OK).body(salaService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Map<String , Object>> salvarSala(@RequestBody @Valid SalaRequestDTO salaDTO){
        salaService.salvarSala(salaDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message" , "Sala salva" ,"success",true));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String , Object>> atualizarSala(@PathVariable Long id , @RequestBody @Valid SalaRequestDTO salaDTO){
        salaService.atualizarSala(id , salaDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message" , "Sala atualizada" , "success" , true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deletarSala(@PathVariable Long id){
        salaService.deletarSala(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Map.of("message" , "Sala deletada" , "success" , true));
    }
}
