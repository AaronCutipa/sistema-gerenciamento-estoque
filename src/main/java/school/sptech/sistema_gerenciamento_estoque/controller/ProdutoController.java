package school.sptech.sistema_gerenciamento_estoque.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import school.sptech.sistema_gerenciamento_estoque.dto.ProdutoMapper;
import school.sptech.sistema_gerenciamento_estoque.dto.ProdutoRequest;
import school.sptech.sistema_gerenciamento_estoque.dto.ProdutoResponse;
import school.sptech.sistema_gerenciamento_estoque.model.Produto;
import school.sptech.sistema_gerenciamento_estoque.service.ProdutoService;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @PostMapping
    public ResponseEntity <ProdutoResponse> postProduto(@RequestBody @Valid ProdutoRequest produtoRequest) {
        Produto produto = ProdutoMapper.toEntity(produtoRequest);

        Produto produtoSalvo = produtoService.cadastrarProduto(produto);

        ProdutoResponse response = ProdutoMapper.toResponseDTO(produtoSalvo);

        return ResponseEntity.status(201).body(response);
    }


}
