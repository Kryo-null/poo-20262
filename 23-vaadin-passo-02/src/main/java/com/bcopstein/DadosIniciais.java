package com.bcopstein;

import com.bcopstein.dominio.Biblioteca;
import com.bcopstein.dominio.Dvd;
import com.bcopstein.dominio.Livro;
import com.bcopstein.dominio.Revista;

/**
 * Monta uma biblioteca com dados de exemplo.
 * (No trabalho final, os dados virão dos arquivos CSV.)
 */
public final class DadosIniciais {

    private DadosIniciais() {
    }

    public static Biblioteca criarBiblioteca() {
        Biblioteca b = new Biblioteca();
        b.cadastrarItem(new Livro("L001", "Dom Casmurro", 1899, "Machado de Assis"));
        b.cadastrarItem(new Livro("L002", "Vidas Secas", 1938, "Graciliano Ramos"));
        b.cadastrarItem(new Livro("L003", "O Cortiço", 1890, "Aluísio Azevedo"));
        b.cadastrarItem(new Livro("L004", "Código Limpo", 2008, "Robert C. Martin"));
        b.cadastrarItem(new Livro("L005", "Java Efetivo", 2018, "Joshua Bloch"));
        b.cadastrarItem(new Revista("R001", "Pesquisa FAPESP", 2025, 350));
        b.cadastrarItem(new Revista("R002", "Superinteressante", 2025, 470));
        b.cadastrarItem(new Revista("R003", "Computação Brasil", 2024, 52));
        b.cadastrarItem(new Dvd("D001", "Central do Brasil", 1998, 110));
        b.cadastrarItem(new Dvd("D002", "Cidade de Deus", 2002, 130));
        b.cadastrarItem(new Dvd("D003", "O Auto da Compadecida", 2000, 104));
        return b;
    }
}
