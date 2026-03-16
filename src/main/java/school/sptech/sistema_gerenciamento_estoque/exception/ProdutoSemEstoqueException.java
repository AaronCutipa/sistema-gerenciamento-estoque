package school.sptech.sistema_gerenciamento_estoque.exception;

public class ProdutoSemEstoqueException extends RuntimeException {
    public ProdutoSemEstoqueException(String message) {
        super(message);
    }
}
