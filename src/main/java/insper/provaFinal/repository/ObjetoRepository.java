package insper.provaFinal.repository;

import insper.provaFinal.entity.Objeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjetoRepository extends JpaRepository<Objeto, Integer> {
    List<Objeto> findByNome(String nome);
}