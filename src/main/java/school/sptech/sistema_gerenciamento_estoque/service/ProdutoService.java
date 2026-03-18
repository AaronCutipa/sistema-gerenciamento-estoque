package school.sptech.sistema_gerenciamento_estoque.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.sistema_gerenciamento_estoque.dto.ProdutoRequest;
import school.sptech.sistema_gerenciamento_estoque.exception.ProdutoCodigoDuplicadoException;
import school.sptech.sistema_gerenciamento_estoque.exception.ProdutoNaoEncontradoException;
import school.sptech.sistema_gerenciamento_estoque.exception.ProdutoSemEstoqueException;
import school.sptech.sistema_gerenciamento_estoque.model.Produto;
import school.sptech.sistema_gerenciamento_estoque.repository.ProdutoRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    // Cadastro do produto
    public Produto cadastrarProduto(Produto produto){
        produto.setCodigo(UUID.randomUUID());
        produto.setAtivo(true);

        return produtoRepository.save(produto);
    }

    // Atualizar Produto
    public Produto atualizarProduto(Long id, ProdutoRequest  produtoRequest) {
        Produto produto = produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));

        if(!produto.getCodigo().equals(produtoRequest.getCodigo())) {
            if(produtoRepository.existsByCodigoAndIdNot(produtoRequest.getCodigo(), id)) {
                throw new ProdutoCodigoDuplicadoException("Código já cadastrado para outro produto");
            }
        }
            produto.setCodigo(produtoRequest.getCodigo());
            produto.setNome(produtoRequest.getNome());
            produto.setCategoria(produtoRequest.getCategoria());
            produto.setQuantidade(produtoRequest.getQuantidade());
            produto.setPreco(produtoRequest.getPreco());

        return produtoRepository.save(produto);
    }

    // Baixa no Estoque
    public Produto darBaixa(Long id) {
        Produto produto = produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));

        if(produto.getQuantidade() <= 0){
            throw new ProdutoSemEstoqueException("Produto se encontra sem estoque");
        }

        produto.setQuantidade(produto.getQuantidade() - 1);

        return produtoRepository.save(produto);
    }

    public Produto removerProduto(Long id) {
        Produto produto = produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException("Produto não encontrado"));

        produto.setAtivo(false);
        produto.setDataRemocao(LocalDateTime.now());

        return produtoRepository.save(produto);
    }

    public List<Produto> listarProdutos() {
        return produtoRepository.findAllByAtivoTrue();
    }
}