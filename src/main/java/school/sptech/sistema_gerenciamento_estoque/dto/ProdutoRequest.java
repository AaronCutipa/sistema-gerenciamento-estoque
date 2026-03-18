package school.sptech.sistema_gerenciamento_estoque.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {
    @NotNull
    private UUID codigo;
    @NotBlank
    private String nome;
    @NotBlank
    private String categoria;
    @NotNull
    private Integer quantidade;
    @NotNull
    private Double preco;
}
