package school.sptech.sistema_gerenciamento_estoque.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.sistema_gerenciamento_estoque.dto.ProdutoRequest;
import school.sptech.sistema_gerenciamento_estoque.exception.ProdutoCodigoDuplicadoException;
import school.sptech.sistema_gerenciamento_estoque.exception.ProdutoNaoEncontradoException;
import school.sptech.sistema_gerenciamento_estoque.model.Produto;
import school.sptech.sistema_gerenciamento_estoque.repository.ProdutoRepository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Produto cadastrarProduto(Produto produto){
        produto.setCodigo(UUID.randomUUID());

        if(produtoRepository.existsByCodigo(produto.getCodigo())){
            throw new ProdutoCodigoDuplicadoException("Código já cadastrado");
        }
        produto.setAtivo(true);

        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAllByAtivoTrue();
    }

    public Produto atualizarProduto(Long id, ProdutoRequest  produtoRequest) {
        Produto produto = produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));

        produto.setNome(produtoRequest.getNome());
        produto.setCategoria(produtoRequest.getCategoria());
        produto.setQuantidade(produtoRequest.getQuantidade());
        produto.setPreco(produtoRequest.getPreco());

        return produtoRepository.save(produto);
    }

}
