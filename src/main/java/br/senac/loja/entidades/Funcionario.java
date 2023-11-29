package br.senac.loja.entidades;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Funcionario {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false, unique = true)
    @NotBlank
    private String nome;

    @Column(nullable = false)
    private String cargo;
    @Column(nullable = false)
    private String descricao;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargo() {return cargo;}

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
