package school.sptech.sistema_gerenciamento_estoque.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import school.sptech.sistema_gerenciamento_estoque.dto.*;
import school.sptech.sistema_gerenciamento_estoque.model.Produto;
import school.sptech.sistema_gerenciamento_estoque.service.ProdutoService;

import java.util.List;

@Validated
@RestController
@ControllerAdvice
@RequestMapping("/produtos")
@Tag(name="Produtos", description="Operações básicas de produtoss")
public class ProdutoController {

    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @Operation(summary = "Cadastra novo produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto cadastrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content())
    })
    @PostMapping
    public ResponseEntity <ProdutoResponse> postProduto(
            @RequestBody @Valid ProdutoRequest produtoRequest
    ) {
        Produto produto = ProdutoMapper.toEntity(produtoRequest);
        Produto produtoSalvo = produtoService.cadastrarProduto(produto);
        ProdutoResponse response = ProdutoMapper.toResponseDTO(produtoSalvo);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Atualiza o produto pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "409", description = "Código duplicado", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Produto não existe", content = @Content()),
            @ApiResponse(responseCode = "400", description = "Erro na requisição", content = @Content())
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponse> atualizarProduto(
            @PathVariable Long id,
            @RequestBody @Valid ProdutoUpdateRequest produtoUpdateRequest
    ) {
        Produto produtoAtualizado = produtoService.atualizarProduto(id, produtoUpdateRequest);
        ProdutoResponse response = ProdutoMapper.toResponseDTO(produtoAtualizado);
        return ResponseEntity.ok(response);

    }

    @Operation(summary = "Dar baixa no estoque", description = "Reduz a quantidade do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Estoque atualizado"),
            @ApiResponse(responseCode = "400", description = "Quantidade inválida (menor ou igual a zero)", content = @Content()),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content()),
            @ApiResponse(responseCode = "422", description = "Estoque insuficiente", content = @Content())
    })
    @PutMapping("/{id}/baixa")
    public ResponseEntity<ProdutoResponse> darBaixa(
            @PathVariable Long id,
            @RequestBody @Valid BaixaEstoqueRequest quantidadeRequest
    ) {
        Produto baixaFeita = produtoService.darBaixa(id, quantidadeRequest.getQuantidade());
        ProdutoResponse response = ProdutoMapper.toResponseDTO(baixaFeita);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Remove produto (soft delete)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Produto removido (retorna os dados do produto removido)"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content())
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<ProdutoResponse> removerProduto(
            @PathVariable Long id
    ) {
        Produto produtoDeletado = produtoService.removerProduto(id);
        ProdutoResponse response = ProdutoMapper.toResponseDTO(produtoDeletado);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar produto por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado ou removido", content = @Content())
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProdutoResponse> buscarProdutoPorId(
            @PathVariable @NotNull @Positive Long id
    ) {
       Produto produtoEncontrado = produtoService.buscarProdutoPorId(id);
       ProdutoResponse response = ProdutoMapper.toResponseDTO(produtoEncontrado);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Listar produtos", description = "Retorna todos os produtos cadastrados, com filtros opcionais")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public ResponseEntity<List<ProdutoResponse>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String categoria
    ) {
        List<Produto> produtos = produtoService.listar(nome, categoria);
        List<ProdutoResponse> response = produtos.stream()
                .map(ProdutoMapper::toResponseDTO)
                .toList();
        return ResponseEntity.ok(response);
    }
}
