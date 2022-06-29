package com.soulcode.Servicos.Enum;

public enum StatusChamado {

        RECEBIDO("Recebido"),
        ATRIBUIDO("Atribuído"),
        CONCLUIDO("Concluído"),
        ARQUIVADO("Arquivado");

        private String fase;

        StatusChamado(String fase){
            this.fase = fase;
        }

        public String getFase() {
            return fase;
        }

    }

