package school.sptech.sistema_gerenciamento_estoque.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int status;
    private String erro;
    private String mensagem;
    private String path;
    private LocalDateTime timestamp;
}
