package school.sptech.sistema_gerenciamento_estoque.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {
    @Schema(
            example = "Cadeira Gamer",
            description = "Nome do produto"
    )
    @NotBlank(message = "Nome é obrigatório")
    @Size(
            min = 3,
            max = 100,
            message = "Nome deve ter entre 3 e 100 caracteres"
    )
    private String nome;

    @Schema(
            example = "Móveis",
            description = "Categoria do produto"
    )
    @NotBlank(message = "Categoria é obrigatória")
    @Size(
            min = 3,
            max = 50,
            message = "Categoria deve ter entre 3 e 50 caracteres"
    )
    private String categoria;

    @Schema(
            example = "10",
            description = "Quantidade em estoque"
    )
    @NotNull(message = "Quantidade é obrigatória")
    @Min(
            value = 0,
            message = "Quantidade deve ser maior ou igual a zero"
    )
    private Integer quantidade;

    @Schema(
            example = "299.90",
            description = "Preço unitário"
    )
    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(
            value = "0.01",
            message = "Preço deve ser maior que zero"
    )
    private Double preco;
}
