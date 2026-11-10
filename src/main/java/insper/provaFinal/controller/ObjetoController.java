package insper.provaFinal.controller;

import insper.provaFinal.entity.Objeto;
import insper.provaFinal.service.ObjetoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/objetos")
public class ObjetoController {

    @Autowired
    private ObjetoService objetoService;

    @GetMapping
    public List<Objeto> listarTodos(@AuthenticationPrincipal Jwt jwt) {
        return objetoService.listarTodos();
    }

    @GetMapping("/{id}")
    public Objeto buscarPorId(@PathVariable Integer id, @AuthenticationPrincipal Jwt jwt) {
        return objetoService.buscarPorId(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Objeto criar(@RequestBody Objeto objeto, @AuthenticationPrincipal Jwt jwt) {
        verificarAdmin(jwt);

        if (jwt != null && objeto.getEmailCriador() == null) {
            String emailToken = jwt.getClaimAsString("https://social-insper.com/email");
            if (emailToken == null) emailToken = jwt.getClaimAsString("email");
            if (emailToken != null) objeto.setEmailCriador(emailToken);
        }

        return objetoService.criar(objeto);
    }

    @PutMapping("/{id}")
    public Objeto atualizar(@PathVariable Integer id, @RequestBody Objeto objeto, @AuthenticationPrincipal Jwt jwt) {
        verificarAdmin(jwt);
        return objetoService.atualizar(id, objeto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id, @AuthenticationPrincipal Jwt jwt) {
        verificarAdmin(jwt);
        objetoService.deletar(id);
    }

    private void verificarAdmin(Jwt jwt) {
        if (jwt == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token ausente");
        }
        List<String> roles = jwt.getClaimAsStringList("https://social-insper.com/roles");
        if (roles == null) {
            roles = jwt.getClaimAsStringList("roles");
        }
        if (roles == null || !roles.contains("ADMIN")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Acesso restrito a administradores (ADMIN)");
        }
    }
}