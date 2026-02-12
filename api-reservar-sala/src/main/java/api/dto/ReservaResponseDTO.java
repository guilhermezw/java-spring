package api.dto;

import java.time.LocalDateTime;

public class ReservaResponseDTO {
    private Long id;
    private SalaResponseDTO sala;
    private String responsavel;
    private LocalDateTime inicio;
    private LocalDateTime fim;

    public ReservaResponseDTO() {
    }

    public ReservaResponseDTO(Long id, SalaResponseDTO sala, String responsavel, LocalDateTime inicio, LocalDateTime fim) {
        this.id = id;
        this.sala = sala;
        this.responsavel = responsavel;
        this.inicio = inicio;
        this.fim = fim;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SalaResponseDTO getSala() {
        return sala;
    }

    public void setSala(SalaResponseDTO sala) {
        this.sala = sala;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void setInicio(LocalDateTime inicio) {
        this.inicio = inicio;
    }

    public LocalDateTime getFim() {
        return fim;
    }

    public void setFim(LocalDateTime fim) {
        this.fim = fim;
    }
}
