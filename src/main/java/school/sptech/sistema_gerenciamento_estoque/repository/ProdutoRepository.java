package school.sptech.sistema_gerenciamento_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import school.sptech.sistema_gerenciamento_estoque.model.Produto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Boolean existsByCodigo(UUID codigo);
    Optional<Produto> findByIdAndAtivoTrue(Long id);
    List<Produto> findAllByAtivoTrue();
    List<Produto> findByNomeContainingAndAtivoTrue(String nome);
    List<Produto> findByCategoriaContainingAndAtivoTrue(String categoria);
    List<Produto> findByNomeContainingAndCategoriaContainingAndAtivoTrue(String nome, String categoria);
}
