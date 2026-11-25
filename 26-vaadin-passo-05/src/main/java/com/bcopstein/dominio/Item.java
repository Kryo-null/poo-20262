package com.bcopstein.dominio;

import java.time.Year;

/**
 * Um item do acervo da biblioteca.
 * Cada tipo concreto define seu próprio prazo de empréstimo e sua multa:
 * é aqui que está o polimorfismo do domínio.
 */
public abstract class Item {
    private final String codigo;
    private final String titulo;
    private final int ano;

    protected Item(String codigo, String titulo, int ano) {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("O código é obrigatório");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título é obrigatório");
        }
        if (ano < 1450 || ano > Year.now().getValue()) {
            throw new IllegalArgumentException("Ano inválido: " + ano);
        }
        this.codigo = codigo.trim().toUpperCase();
        this.titulo = titulo.trim();
        this.ano = ano;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAno() {
        return ano;
    }

    /** Nome do tipo do item, para exibição ("Livro", "Revista", "DVD"). */
    public abstract String getTipo();

    /** Quantos dias o item pode ficar emprestado. */
    public abstract int getPrazoEmDias();

    /** Valor da multa por dia de atraso, em reais. */
    public abstract double getMultaPorDia();

    /** Informações específicas do tipo (autor, edição, duração...). */
    public abstract String getDetalhes();

    @Override
    public String toString() {
        return titulo;
    }
}
