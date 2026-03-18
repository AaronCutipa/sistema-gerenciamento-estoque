package school.sptech.sistema_gerenciamento_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.sistema_gerenciamento_estoque.model.Produto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    boolean existsByCodigo(UUID codigo);
    Optional<Produto> findByIdAndAtivoTrue(Long id);
    boolean existsByCodigoAndIdNot(UUID codigo, Long id);
    List<Produto> findAllByAtivoTrue();

    @Query("""
    SELECT p FROM Produto p
    WHERE p.ativo = true
    AND (:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
    AND (:categoria IS NULL OR LOWER(p.categoria) LIKE LOWER(CONCAT('%', :categoria, '%')))
    """)
    List<Produto> filtrarProdutos(@Param("nome") String nome, @Param("categoria")String categoria);

}
