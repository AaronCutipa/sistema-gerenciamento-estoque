package school.sptech.sistema_gerenciamento_estoque.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
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
    @NotBlank
    @Size(min = 3, max = 100)
    private String nome;
    @NotBlank
    @Size(min = 3, max = 50)
    private String categoria;
    @NotNull
    @Min(value = 0)
    private Integer quantidade;
    @NotNull
    @DecimalMin(value = "0.01")
    private Double preco;
}
