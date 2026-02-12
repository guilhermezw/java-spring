package api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class SalaRequestDTO {
    @NotBlank(message = "O nome da sala é obrigatório")
    @Size(min = 3, max = 250, message = "O nome dever ter no minimo 3 caracteres")
    private String nome;
    @NotNull(message = "A capacidade da sala é obrigatória")
    private Integer capacidade;

    public SalaRequestDTO() {
    }

    public @NotBlank(message = "O nome da sala é obrigatório") @Size(min = 3, max = 250, message = "O nome dever ter no minimo 3 caracteres") String getNome() {
        return nome;
    }

    public void setNome(@NotBlank(message = "O nome da sala é obrigatório") @Size(min = 3, max = 250, message = "O nome dever ter no minimo 3 caracteres") String nome) {
        this.nome = nome;
    }

    public @NotNull(message = "A capacidade da sala é obrigatória") Integer getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(@NotNull(message = "A capacidade da sala é obrigatória") Integer capacidade) {
        this.capacidade = capacidade;
    }
}
