package school.sptech.sistema_gerenciamento_estoque.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequest {
    private String nome;
    private String categoria;
    private Integer quantidade;
    private Double preco;
}
