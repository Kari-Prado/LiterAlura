package com.vitoriaPrado.literalura.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonAlias("name")
    private String nome;

    @JsonAlias("birth_year")
    private Integer anoNascimento;

    @JsonAlias("death_year")
    private Integer anoFalecimento;

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public Integer getAnoNascimento() { return anoNascimento; }
    public void setAnoNascimento(Integer anoNascimento) { this.anoNascimento = anoNascimento; }

    public Integer getAnoFalecimento() { return anoFalecimento; }
    public void setAnoFalecimento(Integer anoFalecimento) { this.anoFalecimento = anoFalecimento; }

    @Override
    public String toString() {
        return "Autor: " + nome + " (" + anoNascimento + " - " + anoFalecimento + ")";
    }
}
