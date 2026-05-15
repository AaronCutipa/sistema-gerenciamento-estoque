package school.sptech.sistema_gerenciamento_estoque.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import school.sptech.sistema_gerenciamento_estoque.dto.ProdutoUpdateRequest;
import school.sptech.sistema_gerenciamento_estoque.exception.ProdutoCodigoDuplicadoException;
import school.sptech.sistema_gerenciamento_estoque.exception.ProdutoNaoEncontradoException;
import school.sptech.sistema_gerenciamento_estoque.exception.ProdutoSemEstoqueException;
import school.sptech.sistema_gerenciamento_estoque.model.Produto;
import school.sptech.sistema_gerenciamento_estoque.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    Produto p1;
    Produto p2;
    Produto p3;
    Produto p4;
    Produto p5;
    List<Produto> produtos;
    List<Produto> produtosVazio;

    @BeforeEach
    void inicializar() {

        p1 = new Produto(
                1L,
                "CAR-PAO-123ABC45",
                "Pão",
                "Carboidrato",
                23,
                1.50,
                true,
                null
        );

        p2 = new Produto(
                2L,
                "CAR-ARROZ-543DEF21",
                "Arroz",
                "Carboidrato",
                50,
                5.00,
                true,
                null
        );

        p3 = new Produto(
                3L,
                "LAT-LEITE-999AAA11",
                "Leite",
                "Laticinio",
                10,
                4.50,
                true,
                null
        );

        p4 = new Produto(
                4L,
                "PER-MOUSE-888BBB22",
                "Mouse Gamer",
                "Perifericos",
                5,
                120.00,
                true,
                null
        );

        p5 = new Produto(
                5L,
                "PER-TECLA-777CCC33",
                "Teclado Mecânico",
                "Perifericos",
                3,
                350.00,
                true,
                null
        );

        produtos = List.of(p1, p2, p3, p4, p5);

        produtosVazio = List.of();
    }

    @Test
    @DisplayName("Deve cadastrar com sucesso")
    void deveCadastrarComSucesso() {
        when(produtoRepository.existsByCodigo(any())).thenReturn(false);

        when(produtoRepository.save(any(Produto.class))).thenReturn(p1);

        Produto resultado = produtoService.cadastrarProduto(p1);

        verify(produtoRepository).existsByCodigo(any());

        verify(produtoRepository).save(any(Produto.class));

        assertNotNull(resultado);

        assertTrue(resultado.getAtivo());
    }

    @Test
    @DisplayName("Deve dar baixa no estoque com sucesso")
    void deveRetornarBaixaComSucesso(){
        when(produtoRepository.findByIdAndAtivoTrue(3L)).thenReturn(Optional.of(p3));
        when(produtoRepository.save(any(Produto.class))).thenReturn(p3);
        Produto resultado = produtoService.darBaixa(3L,2);
        verify(produtoRepository).findByIdAndAtivoTrue(3L);
        verify(produtoRepository).save(any(Produto.class));

        Assertions.assertEquals(8, resultado.getQuantidade());
    }

    @Test
    @DisplayName("Deve lançar erro quando baixa for maior que o estoque")
    void deveLancarErroQuandoBaixaMaiorQueEstoque(){
        p3.setQuantidade(10);

        when(produtoRepository.findByIdAndAtivoTrue(3L))
                .thenReturn(Optional.of(p3));

        Assertions.assertThrows(ProdutoSemEstoqueException.class, () -> {
            produtoService.darBaixa(3L, 12);
        });

        verify(produtoRepository).findByIdAndAtivoTrue(3L);
        verify(produtoRepository, never()).save(any());
    }


    @Test
    @DisplayName("Deve retornar todos os produtos quando não houver filtros")
    void deveListarTodosSemFiltro() {
        when(produtoRepository.filtrarProdutos(null, null)).thenReturn(produtos);

        List<Produto> resultado = produtoService.listar(null, null);

        verify(produtoRepository).filtrarProdutos(null, null);

        Assertions.assertEquals(produtos.size(), resultado.size(),
                "Deveria retornar todos os produtos");

        Assertions.assertEquals(produtos, resultado,
                "A lista retornada deveria ser igual à esperada");
    }

    @Test
    @DisplayName("Deve lançar erro ao atualizar com código duplicado")
    void deveLancarErroAoAtualizarComCodigoDuplicado() {

        String codigoDuplicado = "PER-MOUSE-123ABC45";

        ProdutoUpdateRequest request = new ProdutoUpdateRequest();

        request.setCodigo(codigoDuplicado);
        request.setNome("Novo Nome");
        request.setCategoria("Nova Categoria");
        request.setQuantidade(20);
        request.setPreco(10.0);

        when(produtoRepository.findByIdAndAtivoTrue(1L))
                .thenReturn(Optional.of(p1));

        when(produtoRepository.existsByCodigoAndIdNot(codigoDuplicado, 1L))
                .thenReturn(true);

        assertThrows(
                ProdutoCodigoDuplicadoException.class,
                () -> produtoService.atualizarProduto(1L, request)
        );

        verify(produtoRepository)
                .findByIdAndAtivoTrue(1L);

        verify(produtoRepository)
                .existsByCodigoAndIdNot(codigoDuplicado, 1L);

        verify(produtoRepository, never())
                .save(any());
    }

    @Test
    @DisplayName("Deve lançar erro ao buscar produto inexistente")
    void deveLancarErroAoBuscarPorIdInexistente() {
        when(produtoRepository.findByIdAndAtivoTrue(99L))
                .thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class,
                () -> produtoService.buscarProdutoPorId(99L));

        verify(produtoRepository).findByIdAndAtivoTrue(99L);
    }

    @Test
    @DisplayName("Deve remover produto com soft delete")
    void deveRemoverProdutoComSucesso() {
        when(produtoRepository.findByIdAndAtivoTrue(1L))
                .thenReturn(Optional.of(p1));

        when(produtoRepository.save(any(Produto.class)))
                .thenReturn(p1);

        Produto resultado = produtoService.removerProduto(1L);

        verify(produtoRepository).findByIdAndAtivoTrue(1L);
        verify(produtoRepository).save(p1);

        assertFalse(resultado.getAtivo());
        assertNotNull(resultado.getDataRemocao());
    }

    @Test
    @DisplayName("Deve lançar erro ao operar em produto removido")
    void deveLancarErroAoOperarProdutoRemovido() {
        when(produtoRepository.findByIdAndAtivoTrue(1L))
                .thenReturn(Optional.empty());

        assertThrows(ProdutoNaoEncontradoException.class,
                () -> produtoService.darBaixa(1L, 1));

        verify(produtoRepository).findByIdAndAtivoTrue(1L);
        verify(produtoRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve gerar novo código quando código já existir")
    void deveGerarNovoCodigoQuandoCodigoExistir() {

        Produto produto = new Produto();

        produto.setNome("Notebook");
        produto.setCategoria("Eletronicos");
        produto.setQuantidade(10);
        produto.setPreco(4500.0);

        when(produtoRepository.existsByCodigo(any()))
                .thenReturn(true)
                .thenReturn(false);

        when(produtoRepository.save(any(Produto.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Produto resultado = produtoService.cadastrarProduto(produto);

        verify(produtoRepository, times(2))
                .existsByCodigo(any());

        verify(produtoRepository)
                .save(any(Produto.class));

        assertNotNull(resultado.getCodigo());

        assertTrue(resultado.getAtivo());
    }

}
