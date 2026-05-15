package school.sptech.sistema_gerenciamento_estoque.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoResponse {

    @Schema(
            example = "1",
            description = "ID interno do produto"
    )
    private Long id;

    @Schema(
            example = "AUD-HEADS-943FAA97",
            description = "Código único do produto"
    )
    private String codigo;

    @Schema(
            example = "Headset Gamer",
            description = "Nome do produto"
    )
    private String nome;

    @Schema(
            example = "Áudio",
            description = "Categoria do produto"
    )
    private String categoria;

    @Schema(
            example = "15",
            description = "Quantidade disponível em estoque"
    )
    private Integer quantidade;

    @Schema(
            example = "280.00",
            description = "Preço unitário"
    )
    private Double preco;

    @Schema(
            example = "true",
            description = "Indica se o produto está ativo"
    )
    private Boolean ativo;

    @Schema(
            example = "2026-05-14T20:30:00",
            description = "Data de remoção lógica do produto"
    )
    private LocalDateTime dataRemocao;
}
