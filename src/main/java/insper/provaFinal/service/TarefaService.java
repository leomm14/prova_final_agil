package insper.provaFinal.service;

import insper.provaFinal.entity.Tarefa;
import insper.provaFinal.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TarefaService {

    @Autowired
    private TarefaRepository objetoRepository;

    public List<Tarefa> listarTodos() {
        return objetoRepository.findAll();
    }

    public Tarefa buscarPorId(Integer id) {
        return objetoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado"));
    }

    public Tarefa criar(Tarefa objeto) {
        return objetoRepository.save(objeto);
    }

    public void deletar(Integer id) {
        Tarefa existente = buscarPorId(id);
        objetoRepository.delete(existente);
    }
}