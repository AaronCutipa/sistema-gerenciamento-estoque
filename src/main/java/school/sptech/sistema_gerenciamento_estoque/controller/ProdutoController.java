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

import java.util.ArrayList;
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

    @GetMapping("/listar")
    public ResponseEntity <List<ProdutoResponse>> getProdutos(){
        List<Produto> produtos = produtoService.listarProdutos();

        List<ProdutoResponse> produtosResponse = produtos.stream()
                .map(ProdutoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(produtosResponse);
    }

    @PutMapping("/{id}/atualizar")
    public ResponseEntity<ProdutoResponse> atualizarProduto(@PathVariable Long id, @RequestBody @Valid ProdutoRequest produtoRequest) {

        Produto produtoAtualizado = produtoService.atualizarProduto(id, produtoRequest);

        ProdutoResponse response = ProdutoMapper.toResponseDTO(produtoAtualizado);

        return ResponseEntity.ok(response);

    }

    @PutMapping("/{id}/baixa")
    public ResponseEntity<ProdutoResponse> darBaixa(@PathVariable Long id) {
        Produto baixaFeita = produtoService.darBaixa(id);

        ProdutoResponse response = ProdutoMapper.toResponseDTO(baixaFeita);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}/remover")
    public ResponseEntity<ProdutoResponse> removerProduto(@PathVariable Long id) {
        Produto produtoDeletado = produtoService.removerProduto(id);

        ProdutoResponse response = ProdutoMapper.toResponseDTO(produtoDeletado);

        return ResponseEntity.ok(response);
    }
}
