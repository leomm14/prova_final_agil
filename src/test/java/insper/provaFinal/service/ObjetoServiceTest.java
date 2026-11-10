package insper.provaFinal.service;

import insper.provaFinal.entity.Objeto;
import insper.provaFinal.repository.ObjetoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ObjetoServiceTest {

    @InjectMocks
    private ObjetoService objetoService;

    @Mock
    private ObjetoRepository objetoRepository;

    private Objeto criarObjetoValido() {
        Objeto obj = new Objeto();
        obj.setId(1);
        obj.setNome("Teste");
        obj.setTitulo("Titulo");
        obj.setDescricao("Desc");
        obj.setComentario("Bom");
        obj.setCategoria("Cat");
        obj.setTipo("Tipo");
        obj.setStatus("ATIVO");
        obj.setEmailCriador("admin@insper.edu.br");
        obj.setEmailDestino("user@insper.edu.br");
        obj.setCpf("12345678900");
        obj.setNota(8);
        obj.setQuantidade(5);
        obj.setValor(100.0);
        obj.setAtivo(true);
        obj.setDataEvento(LocalDateTime.now());
        return obj;
    }

    @Test
    void deveListarTodos() {
        Mockito.when(objetoRepository.findAll()).thenReturn(List.of(criarObjetoValido()));
        List<Objeto> lista = objetoService.listarTodos();
        Assertions.assertEquals(1, lista.size());
    }

    @Test
    void deveBuscarPorIdComSucesso() {
        Objeto obj = criarObjetoValido();
        Mockito.when(objetoRepository.findById(1)).thenReturn(Optional.of(obj));
        Objeto retorno = objetoService.buscarPorId(1);
        Assertions.assertEquals("Teste", retorno.getNome());
    }

    @Test
    void deveLancarErroAoBuscarPorIdInexistente() {
        Mockito.when(objetoRepository.findById(99)).thenReturn(Optional.empty());
        Assertions.assertThrows(ResponseStatusException.class, () -> objetoService.buscarPorId(99));
    }

    @Test
    void deveCriarObjetoComSucesso() {
        Objeto obj = criarObjetoValido();
        obj.setDataCriacao(null);
        Mockito.when(objetoRepository.save(Mockito.any())).thenReturn(obj);
        Objeto salvo = objetoService.criar(obj);
        Assertions.assertNotNull(salvo.getDataCriacao());
        Assertions.assertEquals(8, salvo.getNota());
    }

    @Test
    void deveLancarErroQuandoNotaForInvalida() {
        Objeto obj = criarObjetoValido();
        obj.setNota(15);
        Assertions.assertThrows(ResponseStatusException.class, () -> objetoService.criar(obj));

        obj.setNota(-1);
        Assertions.assertThrows(ResponseStatusException.class, () -> objetoService.criar(obj));
    }

    @Test
    void deveLancarErroQuandoValorOuQuantidadeNegativos() {
        Objeto obj = criarObjetoValido();
        obj.setValor(-10.0);
        Assertions.assertThrows(ResponseStatusException.class, () -> objetoService.criar(obj));

        obj.setValor(10.0);
        obj.setQuantidade(-2);
        Assertions.assertThrows(ResponseStatusException.class, () -> objetoService.criar(obj));
    }

    @Test
    void deveAtualizarObjetoComSucesso() {
        Objeto existente = criarObjetoValido();
        Objeto novosDados = criarObjetoValido();
        novosDados.setNome("Atualizado");

        Mockito.when(objetoRepository.findById(1)).thenReturn(Optional.of(existente));
        Mockito.when(objetoRepository.save(Mockito.any())).thenReturn(existente);

        Objeto atualizado = objetoService.atualizar(1, novosDados);
        Assertions.assertEquals("Atualizado", atualizado.getNome());
    }

    @Test
    void deveDeletarObjetoComSucesso() {
        Objeto existente = criarObjetoValido();
        Mockito.when(objetoRepository.findById(1)).thenReturn(Optional.of(existente));

        objetoService.deletar(1);
        Mockito.verify(objetoRepository).delete(existente);
    }
}