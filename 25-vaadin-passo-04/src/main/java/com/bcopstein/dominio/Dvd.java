package com.bcopstein.dominio;

public class Dvd extends Item {
    private final int duracaoEmMinutos;

    public Dvd(String codigo, String titulo, int ano, int duracaoEmMinutos) {
        super(codigo, titulo, ano);
        if (duracaoEmMinutos <= 0) {
            throw new IllegalArgumentException("A duração deve ser positiva");
        }
        this.duracaoEmMinutos = duracaoEmMinutos;
    }

    public int getDuracaoEmMinutos() {
        return duracaoEmMinutos;
    }

    @Override
    public String getTipo() {
        return "DVD";
    }

    @Override
    public int getPrazoEmDias() {
        return 3;
    }

    @Override
    public double getMultaPorDia() {
        return 2.00;
    }

    @Override
    public String getDetalhes() {
        return "Duração: " + duracaoEmMinutos + " min";
    }
}
