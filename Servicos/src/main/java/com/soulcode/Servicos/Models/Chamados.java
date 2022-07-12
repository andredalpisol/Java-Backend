package com.soulcode.Servicos.Models;

import com.soulcode.Servicos.Enum.StatusChamado;
import com.soulcode.Servicos.Models.Cliente;
import com.soulcode.Servicos.Models.Funcionario;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Chamados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idChamado;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = true)
    private String descricao;

    @Column(nullable = false, columnDefinition = "date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date data;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusChamado status;

    @ManyToOne // PODEMOS TER VARIOS CHAMADOS PARA UM SÓ FUNCIONARIO / MUITOS CHAMADOS PARA SÓ UM FUNCIONARIO
    @JoinColumn(name = "idFuncionario")
    private Funcionario funcionario;

    @ManyToOne // PODEMOS TER VARIOS CHAMADOS PARA UM SÓ CLIENTE / MUITOS CHAMADOS PARA SÓ UM CLIENTE
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "idPagamento", unique = true)
    private Pagamento pagamento;

    public void setIdChamado(Integer idChamado) {
        this.idChamado = idChamado;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }

    public Integer getIdChamado() {
        return idChamado;
    }

    public void setId(Integer id) {
        this.idChamado = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }


}
