package insper.provaFinal.controller;

import insper.provaFinal.entity.Tarefa;
import insper.provaFinal.service.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/tarefa")
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @GetMapping
    public List<Tarefa> listarTodos(@AuthenticationPrincipal Jwt jwt) {
        return tarefaService.listarTodos();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tarefa criar(@RequestBody Tarefa tarefa, @AuthenticationPrincipal Jwt jwt) {
        return tarefaService.criar(tarefa);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Integer id, @AuthenticationPrincipal Jwt jwt) {
        tarefaService.deletar(id);
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