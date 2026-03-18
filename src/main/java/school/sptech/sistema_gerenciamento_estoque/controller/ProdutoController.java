package school.sptech.sistema_gerenciamento_estoque.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import school.sptech.sistema_gerenciamento_estoque.dto.BaixaEstoqueRequest;
import school.sptech.sistema_gerenciamento_estoque.dto.ProdutoMapper;
import school.sptech.sistema_gerenciamento_estoque.dto.ProdutoRequest;
import school.sptech.sistema_gerenciamento_estoque.dto.ProdutoResponse;
import school.sptech.sistema_gerenciamento_estoque.model.Produto;
import school.sptech.sistema_gerenciamento_estoque.service.ProdutoService;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Cadastra o produto para o estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto cadastrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @PostMapping
    public ResponseEntity <ProdutoResponse> postProduto(@RequestBody @Valid ProdutoRequest produtoRequest) {
        Produto produto = ProdutoMapper.toEntity(produtoRequest);

        Produto produtoSalvo = produtoService.cadastrarProduto(produto);

        ProdutoResponse response = ProdutoMapper.toResponseDTO(produtoSalvo);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Lista todos os produtos do estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @GetMapping("/listar")
    public ResponseEntity <List<ProdutoResponse>> getProdutos(){
        List<Produto> produtos = produtoService.listarProdutos();

        List<ProdutoResponse> produtosResponse = produtos.stream()
                .map(ProdutoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(produtosResponse);
    }

    @Operation(summary = "Atualiza o produto pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Código duplicado"),
            @ApiResponse(responseCode = "404", description = "Produto não existe"),
            @ApiResponse(responseCode = "400", description = "Erro na requisição")
    })
    @PutMapping("/{id}/atualizar")
    public ResponseEntity<ProdutoResponse> atualizarProduto(@PathVariable Long id, @RequestBody @Valid ProdutoRequest produtoRequest) {

        Produto produtoAtualizado = produtoService.atualizarProduto(id, produtoRequest);

        ProdutoResponse response = ProdutoMapper.toResponseDTO(produtoAtualizado);

        return ResponseEntity.ok(response);

    }

    @PutMapping("/{id}/baixa")
    public ResponseEntity<ProdutoResponse> darBaixa(
            @PathVariable Long id,
            @RequestBody @Valid BaixaEstoqueRequest quantidadeRequest
    ) {
        Produto baixaFeita = produtoService.darBaixa(id, quantidadeRequest.getQuantidade());

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
