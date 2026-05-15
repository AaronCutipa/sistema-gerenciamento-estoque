package school.sptech.sistema_gerenciamento_estoque.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoUpdateRequest {

    @NotBlank(message = "Código é obrigatório")
    @Pattern(
            regexp = "^[A-Z]{3}-[A-Z0-9]{5,7}-[A-Z0-9]{8}$",
            message = "Código deve seguir o padrão AAA-NNNNN-XXXXXXXX"
    )
    private String codigo;

    @NotBlank(message = "Nome é obrigatório")
    @Size(
            min = 3,
            max = 100,
            message = "Nome deve ter entre 3 e 100 caracteres"
    )
    private String nome;

    @NotBlank(message = "Categoria é obrigatória")
    @Size(
            min = 3,
            max = 50,
            message = "Categoria deve ter entre 3 e 50 caracteres"
    )
    private String categoria;

    @NotNull(message = "Quantidade é obrigatória")
    @Min(
            value = 0,
            message = "Quantidade não pode ser negativa"
    )
    private Integer quantidade;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(
            value = "0.01",
            message = "Preço deve ser maior que zero"
    )
    private Double preco;
}
