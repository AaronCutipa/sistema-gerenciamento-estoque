package school.sptech.sistema_gerenciamento_estoque.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponse {
    private Long id;
    private UUID codigo;
    private String nome;
    private String categoria;
    private Integer quantidade;
    private Double preco;
    private Boolean ativo;
    private LocalDateTime dataRemocao;
}
