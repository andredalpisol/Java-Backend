package com.soulcode.Servicos.Enum;

public enum StatusPagamento {

    QUITADO("Quitado"),
    LANCADO("Lancado");
    private String statusPagamento;

    StatusPagamento (String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }
}
