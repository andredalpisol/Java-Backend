package com.soulcode.Servicos.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFuncionario;
    @Column(nullable = false, length = 100)
    private String nome;
    @Column(nullable = false, length = 100, unique = true)
    private String email;
    @Column(nullable = true)
    private String foto;

    @ManyToOne
    @JoinColumn(name = "idCargo")
    private Cargo cargo;

    @JsonIgnore
    @OneToMany(mappedBy = "funcionario") //UM FUNCIONARIO PARA VARIOS CHAMADOS
    private List<Chamados> chamados = new ArrayList<>();


    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Integer getIdFuncionario() {
        return idFuncionario;
    }

    public void setIdFuncionario(Integer idFuncionario) {
        this.idFuncionario = idFuncionario;
    }

    public List<Chamados> getChamados() {
        return chamados;
    }

    public void setChamados(List<Chamados> chamados) {
        this.chamados = chamados;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}

