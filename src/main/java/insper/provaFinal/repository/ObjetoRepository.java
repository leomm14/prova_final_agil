package insper.provaFinal.repository;

import insper.provaFinal.entity.Objeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ObjetoRepository extends JpaRepository<Objeto, Integer> {
    List<Objeto> findByEmailCriador(String emailCriador);
    List<Objeto> findByEmailDestino(String emailDestino);
    List<Objeto> findByStatus(String status);
    List<Objeto> findByCategoria(String categoria);
    List<Objeto> findByCpf(String cpf);
}