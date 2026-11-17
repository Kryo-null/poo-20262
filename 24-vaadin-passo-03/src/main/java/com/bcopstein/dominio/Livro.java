package com.bcopstein.dominio;

public class Livro extends Item {
    private final String autor;

    public Livro(String codigo, String titulo, int ano, String autor) {
        super(codigo, titulo, ano);
        if (autor == null || autor.isBlank()) {
            throw new IllegalArgumentException("O autor é obrigatório para livros");
        }
        this.autor = autor.trim();
    }

    public String getAutor() {
        return autor;
    }

    @Override
    public String getTipo() {
        return "Livro";
    }

    @Override
    public int getPrazoEmDias() {
        return 14;
    }

    @Override
    public double getMultaPorDia() {
        return 1.00;
    }

    @Override
    public String getDetalhes() {
        return "Autor: " + autor;
    }
}
