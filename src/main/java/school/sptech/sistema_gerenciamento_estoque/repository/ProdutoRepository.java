package school.sptech.sistema_gerenciamento_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import school.sptech.sistema_gerenciamento_estoque.model.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    boolean existsByCodigo(String codigo);
    Optional<Produto> findByIdAndAtivoTrue(Long id);
    boolean existsByCodigoAndIdNot(String codigo, Long id);

    @Query("""
    SELECT p FROM Produto p
    WHERE p.ativo = true
    AND (:nome IS NULL OR LOWER(p.nome) LIKE LOWER(CONCAT('%', :nome, '%')))
    AND (:categoria IS NULL OR LOWER(p.categoria) LIKE LOWER(CONCAT('%', :categoria, '%')))
    """)
    List<Produto> filtrarProdutos(@Param("nome") String nome, @Param("categoria")String categoria);
}
