package insper.provaFinal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Objeto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String titulo;
    private String descricao;
    private String comentario;
    private String categoria;
    private String tipo;
    private String status = "ATIVO";

    private String emailCriador;
    private String emailDestino;
    private String cpf;

    private Integer nota;
    private Integer quantidade;
    private Double valor;

    private Boolean ativo = true;

    private LocalDateTime dataCriacao = LocalDateTime.now();
    private LocalDateTime dataEvento;
}