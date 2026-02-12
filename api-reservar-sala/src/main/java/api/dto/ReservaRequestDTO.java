package api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class ReservaRequestDTO {
    @NotNull(message = "A sala é obrigatória.")
    private Long salaId;

    @NotBlank(message = "O nome do responsável da reserva é obrigatória.")
    @Size(min = 2 , max = 50 , message = "O nome do responsável deve ter entre 2 a 50 caracteres para ser considerado válido. ")
    private String responsavel;

    @NotNull(message = "O horário de inicio da reserva é obrigatório")
    private LocalDateTime inicio;

    @NotNull(message = "O horário de fim da reserva é obrigatório")
    private LocalDateTime fim;

    public ReservaRequestDTO() {
    }

    public ReservaRequestDTO(Long salaId, String responsavel, LocalDateTime inicio, LocalDateTime fim) {
        this.salaId = salaId;
        this.responsavel = responsavel;
        this.inicio = inicio;
        this.fim = fim;
    }

    public @NotNull(message = "A sala é obrigatória.") Long getSalaId() {
        return salaId;
    }

    public void setSalaId(@NotNull(message = "A sala é obrigatória.") Long salaId) {
        this.salaId = salaId;
    }

    public @NotBlank(message = "O nome do responsável da reserva é obrigatória.") @Size(min = 2, max = 50, message = "O nome do responsável deve ter entre 2 a 50 caracteres para ser considerado válido. ") String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(@NotBlank(message = "O nome do responsável da reserva é obrigatória.") @Size(min = 2, max = 50, message = "O nome do responsável deve ter entre 2 a 50 caracteres para ser considerado válido. ") String responsavel) {
        this.responsavel = responsavel;
    }

    public @NotNull(message = "O horário de inicio da reserva é obrigatório") LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(@NotNull(message = "O horário de inicio da reserva é obrigatório") LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public @NotNull(message = "O horário de fim da reserva é obrigatório") LocalDateTime getFim() {
        return fim;
    }

    public void setFim(@NotNull(message = "O horário de fim da reserva é obrigatório") LocalDateTime fim) {
        this.fim = fim;
    }
}
