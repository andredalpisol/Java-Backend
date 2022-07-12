package com.soulcode.Servicos.Models;

import com.soulcode.Servicos.Enum.StatusPagamento;
import org.springframework.format.annotation.NumberFormat;

import javax.persistence.*;

@Entity
public class Pagamento {

    @Id
    private Integer idPagamento;

    @Column (nullable = false)
    @NumberFormat (pattern = "#.##0,00")
    private double valor;

    @Column (nullable = false)
    private String formadePagamento;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusPagamento statusPagamento;

    public Integer getIdPagamento() {
        return idPagamento;
    }

    public void setIdPagamento(Integer idPagamento) {
        this.idPagamento = idPagamento;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getFormadePagamento() {
        return formadePagamento;
    }

    public void setFormadePagamento(String formadePagamento) {
        this.formadePagamento = formadePagamento;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(StatusPagamento statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
}
