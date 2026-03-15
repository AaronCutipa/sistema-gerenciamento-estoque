package school.sptech.sistema_gerenciamento_estoque.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.sistema_gerenciamento_estoque.exception.ProdutoCodigoDuplicadoException;
import school.sptech.sistema_gerenciamento_estoque.model.Produto;
import school.sptech.sistema_gerenciamento_estoque.repository.ProdutoRepository;

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

}
