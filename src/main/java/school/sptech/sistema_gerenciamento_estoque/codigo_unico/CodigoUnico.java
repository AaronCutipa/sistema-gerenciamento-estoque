package school.sptech.sistema_gerenciamento_estoque.codigo_unico;

import java.util.UUID;

public class CodigoUnico {
    private CodigoUnico() {}

    public static String gerarCodigoUnico(String nome, String categoria){
        String nomeFormatado = nome
                .replaceAll("[^a-zA-Z0-9]", "")
                .toUpperCase();
        String categoriaFormatada = categoria
                .replaceAll("[^a-zA-Z0-9]", "")
                .toUpperCase();

        categoriaFormatada = categoriaFormatada.substring(
                0,
                Math.min(3, categoriaFormatada.length())
        );

        nomeFormatado = nomeFormatado.substring(
                0,
                Math.min(5, nomeFormatado.length())
        );

        String uuid = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 8)
                .toUpperCase();

        return categoriaFormatada + "-" + nomeFormatado + "-" + uuid;
    }
}
