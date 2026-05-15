package school.sptech.sistema_gerenciamento_estoque.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.sistema_gerenciamento_estoque.codigo_unico.CodigoUnico;
import school.sptech.sistema_gerenciamento_estoque.dto.ProdutoUpdateRequest;
import school.sptech.sistema_gerenciamento_estoque.exception.ProdutoCodigoDuplicadoException;
import school.sptech.sistema_gerenciamento_estoque.exception.ProdutoNaoEncontradoException;
import school.sptech.sistema_gerenciamento_estoque.exception.ProdutoSemEstoqueException;
import school.sptech.sistema_gerenciamento_estoque.exception.QuantidadeInvalidaException;
import school.sptech.sistema_gerenciamento_estoque.model.Produto;
import school.sptech.sistema_gerenciamento_estoque.repository.ProdutoRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;

    public Produto cadastrarProduto(Produto produto){
        String codigo;

        do {
            codigo = CodigoUnico.gerarCodigoUnico(
                    produto.getCategoria(),
                    produto.getNome()
            );
        } while (produtoRepository.existsByCodigo(codigo));

        produto.setCodigo(codigo);
        produto.setAtivo(true);

        return produtoRepository.save(produto);
    }

    public Produto atualizarProduto(Long id, ProdutoUpdateRequest  produtoUpdateRequest) {
        Produto produto = buscarProdutoAtivo(id);

        if(!produto.getCodigo().equals(produtoUpdateRequest.getCodigo())) {
            if(produtoRepository.existsByCodigoAndIdNot(produtoUpdateRequest.getCodigo(), id)) {
                throw new ProdutoCodigoDuplicadoException("Código já cadastrado para outro produto");
            }
        }
            produto.setCodigo(produtoUpdateRequest.getCodigo());
            produto.setNome(produtoUpdateRequest.getNome());
            produto.setCategoria(produtoUpdateRequest.getCategoria());
            produto.setQuantidade(produtoUpdateRequest.getQuantidade());
            produto.setPreco(produtoUpdateRequest.getPreco());

        return produtoRepository.save(produto);
    }

    public Produto darBaixa(Long id, Integer quantidade) {
        Produto produto = buscarProdutoAtivo(id);

        if(quantidade == null || quantidade <= 0) {
            throw new QuantidadeInvalidaException("A quantidade deve ser maior que zero");
        }

        if (quantidade > produto.getQuantidade()) {
            throw new ProdutoSemEstoqueException(
                    "Estoque insuficiente. Disponível: " + produto.getQuantidade()
            );
        }

        produto.setQuantidade(produto.getQuantidade() - quantidade);

        return produtoRepository.save(produto);
    }

    public Produto removerProduto(Long id) {
        Produto produto = buscarProdutoAtivo(id);

        produto.setAtivo(false);
        produto.setDataRemocao(LocalDateTime.now());

        return produtoRepository.save(produto);
    }

    public Produto buscarProdutoPorId(Long id){
        return buscarProdutoAtivo(id);
    }

    public List<Produto> listar(String nome, String categoria) {
        String nomeNormalizado = (nome == null) ? null : nome.trim().toLowerCase();
        String categoriaNormalizada = (categoria == null) ? null : categoria.trim().toLowerCase();
        return produtoRepository.filtrarProdutos(nomeNormalizado, categoriaNormalizada);
    }

    private Produto buscarProdutoAtivo(Long id) {
        return produtoRepository.findByIdAndAtivoTrue(id)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(
                                "Produto com ID " + id + " não encontrado")
                );
    }
}