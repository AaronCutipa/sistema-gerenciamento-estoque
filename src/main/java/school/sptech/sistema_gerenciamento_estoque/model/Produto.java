package school.sptech.sistema_gerenciamento_estoque.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor // Cria construtor vazio
@AllArgsConstructor // Cria construtor cheio
@Entity
public class Produto {

    @Id
    private Long id;
    @NotBlank
    private String codigo;
    @NotBlank
    private String name;
    @NotBlank
    private String categoria;
    @NotNull
    private Integer quantidade;
    @Positive
    private Double preco;
    private Boolean ativo;
    private LocalDate dataRemocao;

}

