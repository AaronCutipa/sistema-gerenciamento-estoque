package school.sptech.sistema_gerenciamento_estoque.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor // Cria construtor vazio
@AllArgsConstructor // Cria construtor cheio
@Entity
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private UUID codigo;
    @NotBlank
    private String nome;
    @NotBlank
    private String categoria;
    @NotNull
    private Integer quantidade;
    @Positive
    private Double preco;
    @NotNull
    private Boolean ativo;
    private LocalDateTime dataRemocao;

}

