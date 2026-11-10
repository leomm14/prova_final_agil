package insper.provaFinal.controller;

import insper.provaFinal.entity.Objeto;
import insper.provaFinal.service.ObjetoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ObjetoControllerTest {

    @InjectMocks
    private ObjetoController controller;

    @Mock
    private ObjetoService service;

    private Jwt criarJwt(List<String> roles, String email) {
        return Jwt.withTokenValue("token")
                .header("alg", "none")
                .claim("https://social-insper.com/roles", roles)
                .claim("https://social-insper.com/email", email)
                .build();
    }

    @Test
    void deveListarEBuscarPorId() {
        Jwt jwt = criarJwt(List.of("USER"), "user@insper.edu.br");
        Objeto obj = new Objeto();
        obj.setId(1);

        Mockito.when(service.listarTodos()).thenReturn(List.of(obj));
        Mockito.when(service.buscarPorId(1)).thenReturn(obj);

        Assertions.assertEquals(1, controller.listarTodos(jwt).size());
        Assertions.assertEquals(1, controller.buscarPorId(1, jwt).getId());
    }

    @Test
    void devePermitirCriarAtualizarEDeletarParaAdmin() {
        Jwt jwtAdmin = criarJwt(List.of("ADMIN"), "admin@insper.edu.br");
        Objeto obj = new Objeto();
        obj.setNota(10);

        Mockito.when(service.criar(Mockito.any())).thenReturn(obj);
        Mockito.when(service.atualizar(Mockito.eq(1), Mockito.any())).thenReturn(obj);

        Objeto criado = controller.criar(obj, jwtAdmin);
        Assertions.assertEquals("admin@insper.edu.br", obj.getEmailCriador());
        Assertions.assertNotNull(criado);

        Objeto atualizado = controller.atualizar(1, obj, jwtAdmin);
        Assertions.assertNotNull(atualizado);

        controller.deletar(1, jwtAdmin);
        Mockito.verify(service).deletar(1);
    }

    @Test
    void deveBloquearUsuarioComumOuSemTokenNoPost() {
        Jwt jwtUser = criarJwt(List.of("USER"), "user@insper.edu.br");
        Objeto obj = new Objeto();

        Assertions.assertThrows(ResponseStatusException.class, () -> controller.criar(obj, jwtUser));
        Assertions.assertThrows(ResponseStatusException.class, () -> controller.criar(obj, null));
    }
}