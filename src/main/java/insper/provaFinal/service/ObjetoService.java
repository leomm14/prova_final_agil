package insper.provaFinal.service;

import insper.provaFinal.entity.Objeto;
import insper.provaFinal.repository.ObjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ObjetoService {

    @Autowired
    private ObjetoRepository objetoRepository;

    public List<Objeto> listarTodos() {
        return objetoRepository.findAll();
    }

    public Objeto buscarPorId(Integer id) {
        return objetoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado"));
    }

    public Objeto criar(Objeto objeto) {
        validar(objeto);
        if (objeto.getDataCriacao() == null) {
            objeto.setDataCriacao(LocalDateTime.now());
        }
        return objetoRepository.save(objeto);
    }

    public Objeto atualizar(Integer id, Objeto dadosAtualizados) {
        Objeto existente = buscarPorId(id);
        validar(dadosAtualizados);

        if (dadosAtualizados.getNome() != null) existente.setNome(dadosAtualizados.getNome());
        if (dadosAtualizados.getTitulo() != null) existente.setTitulo(dadosAtualizados.getTitulo());
        if (dadosAtualizados.getDescricao() != null) existente.setDescricao(dadosAtualizados.getDescricao());
        if (dadosAtualizados.getComentario() != null) existente.setComentario(dadosAtualizados.getComentario());
        if (dadosAtualizados.getCategoria() != null) existente.setCategoria(dadosAtualizados.getCategoria());
        if (dadosAtualizados.getTipo() != null) existente.setTipo(dadosAtualizados.getTipo());
        if (dadosAtualizados.getStatus() != null) existente.setStatus(dadosAtualizados.getStatus());
        if (dadosAtualizados.getEmailCriador() != null) existente.setEmailCriador(dadosAtualizados.getEmailCriador());
        if (dadosAtualizados.getEmailDestino() != null) existente.setEmailDestino(dadosAtualizados.getEmailDestino());
        if (dadosAtualizados.getCpf() != null) existente.setCpf(dadosAtualizados.getCpf());
        if (dadosAtualizados.getNota() != null) existente.setNota(dadosAtualizados.getNota());
        if (dadosAtualizados.getQuantidade() != null) existente.setQuantidade(dadosAtualizados.getQuantidade());
        if (dadosAtualizados.getValor() != null) existente.setValor(dadosAtualizados.getValor());
        if (dadosAtualizados.getAtivo() != null) existente.setAtivo(dadosAtualizados.getAtivo());
        if (dadosAtualizados.getDataEvento() != null) existente.setDataEvento(dadosAtualizados.getDataEvento());

        return objetoRepository.save(existente);
    }

    public void deletar(Integer id) {
        Objeto existente = buscarPorId(id);
        objetoRepository.delete(existente);
    }

    private void validar(Objeto obj) {
        if (obj.getNota() != null && (obj.getNota() < 0 || obj.getNota() > 10)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A nota deve estar entre 0 e 10");
        }
        if (obj.getValor() != null && obj.getValor() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O valor não pode ser negativo");
        }
        if (obj.getQuantidade() != null && obj.getQuantidade() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A quantidade não pode ser negativa");
        }
    }
}