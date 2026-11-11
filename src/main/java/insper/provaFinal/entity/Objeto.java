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

    private Integer nota;
    private Double valor;

    private Boolean ativo = true;

    private LocalDateTime dataCriacao = LocalDateTime.now();
    private LocalDateTime dataEvento;
}