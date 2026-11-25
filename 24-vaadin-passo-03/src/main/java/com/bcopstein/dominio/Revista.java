package com.bcopstein.dominio;

public class Revista extends Item {
    private final int edicao;

    public Revista(String codigo, String titulo, int ano, int edicao) {
        super(codigo, titulo, ano);
        if (edicao <= 0) {
            throw new IllegalArgumentException("A edição deve ser positiva");
        }
        this.edicao = edicao;
    }

    public int getEdicao() {
        return edicao;
    }

    @Override
    public String getTipo() {
        return "Revista";
    }

    @Override
    public int getPrazoEmDias() {
        return 7;
    }

    @Override
    public double getMultaPorDia() {
        return 0.50;
    }

    @Override
    public String getDetalhes() {
        return "Edição nº " + edicao;
    }
}
