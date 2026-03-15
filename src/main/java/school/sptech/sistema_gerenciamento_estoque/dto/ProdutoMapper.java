package school.sptech.sistema_gerenciamento_estoque.dto;

import school.sptech.sistema_gerenciamento_estoque.model.Produto;

public class ProdutoMapper {
    public static Produto toEntity(ProdutoRequest dto) {
        Produto produto = new Produto();

        produto.setNome(dto.getNome());
        produto.setCategoria(dto.getCategoria());
        produto.setQuantidade(dto.getQuantidade());
        produto.setPreco(dto.getPreco());

        return produto;
    }

    public static ProdutoResponse toResponseDTO(Produto produto) {
        ProdutoResponse dto = new ProdutoResponse();

        dto.setId(produto.getId());
        dto.setCodigo(produto.getCodigo());
        dto.setNome(produto.getNome());
        dto.setCategoria(produto.getCategoria());
        dto.setQuantidade(produto.getQuantidade());
        dto.setPreco(produto.getPreco());
        dto.setAtivo(produto.getAtivo());
        dto.setDataRemocao(produto.getDataRemocao());

        return dto;
    }
}
