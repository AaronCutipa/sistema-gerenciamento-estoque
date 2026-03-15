package school.sptech.sistema_gerenciamento_estoque.model;

import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        String code = CodigoUnico.generateVerificationCode();
        System.out.println(code);

        String codigo = UUID.randomUUID().toString();
        System.out.println("Código Único: " + codigo);

    }
}
