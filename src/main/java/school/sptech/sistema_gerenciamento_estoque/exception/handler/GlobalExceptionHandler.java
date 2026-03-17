package school.sptech.sistema_gerenciamento_estoque.exception.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import school.sptech.sistema_gerenciamento_estoque.exception.ProdutoCodigoDuplicadoException;
import school.sptech.sistema_gerenciamento_estoque.exception.ProdutoNaoEncontradoException;
import school.sptech.sistema_gerenciamento_estoque.exception.ProdutoSemEstoqueException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProdutoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleProdutoNaoEncontrado(
            ProdutoNaoEncontradoException ex,
            HttpServletRequest request) {

        ErrorResponse erro = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Produto não encontrado",
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(ProdutoCodigoDuplicadoException.class)
    public ResponseEntity<ErrorResponse> handleCodigoDuplicado(
            ProdutoCodigoDuplicadoException ex,
            HttpServletRequest request) {

        ErrorResponse erro = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Produto já existe",
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
    }

    @ExceptionHandler(ProdutoSemEstoqueException.class)
    public ResponseEntity<ErrorResponse> handleSemEstoque(
            ProdutoSemEstoqueException ex,
            HttpServletRequest request) {

        ErrorResponse erro = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Produto sem estoque",
                ex.getMessage(),
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleErroGeral(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse erro = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Erro interno",
                "Ocorreu um erro inesperado",
                request.getRequestURI(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
    }

}
