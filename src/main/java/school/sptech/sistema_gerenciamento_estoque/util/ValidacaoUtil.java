package school.sptech.sistema_gerenciamento_estoque.util;

public class ValidacaoUtil {
    ValidacaoUtil() {
    }

    public static void validarId(Long id, String nomeCampo) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException(nomeCampo + " inválido: deve ser maior que zero e não nulo");
        }
    }

    public static void validarString(String valor, String nomeCampo, Integer tamanhoMin) {
        if (nomeCampo == null || nomeCampo.trim().isEmpty()) {
            if (valor == null || valor.trim().isEmpty()) {
                throw new IllegalArgumentException(nomeCampo + " é obrigatório e não pode estar vazio");
            }
            if (valor.trim().length() < tamanhoMin) {
                throw new IllegalArgumentException(nomeCampo + " deve ter pelo menos " + tamanhoMin + " caracteres");
            }
        }
    }

    public static void validarQuantidade(Integer quantidade, String nomeCampo) {
        if (quantidade == null || quantidade < 0) {
            throw new IllegalArgumentException(nomeCampo + " deve ser maior ou igual a zero");
        }
    }

    public static void validarPreco(Double preco, String nomeCampo) {
        if (preco == null || preco <= 0) {
            throw new IllegalArgumentException(nomeCampo + " deve ser maior que zero");
        }
    }
}