package com.bcopstein;

import java.time.LocalDate;
import java.util.List;

import com.bcopstein.dominio.Biblioteca;
import com.bcopstein.dominio.Dvd;
import com.bcopstein.dominio.Item;
import com.bcopstein.dominio.Livro;
import com.bcopstein.dominio.Revista;
import com.bcopstein.dominio.Usuario;

/**
 * Monta uma biblioteca com dados de exemplo.
 * As datas dos empréstimos são calculadas a partir de hoje,
 * para que sempre existam empréstimos em dia, atrasados e devolvidos.
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

        Usuario ana = new Usuario("2026001", "Ana Lima");
        Usuario bruno = new Usuario("2026002", "Bruno Costa");
        Usuario carla = new Usuario("2026003", "Carla Souza");
        Usuario diego = new Usuario("2026004", "Diego Martins");
        b.cadastrarUsuario(ana);
        b.cadastrarUsuario(bruno);
        b.cadastrarUsuario(carla);
        b.cadastrarUsuario(diego);

        LocalDate hoje = LocalDate.now();
        // Carla: empréstimo antigo, já devolvido
        var antigo = b.registrarEmprestimo(carla, List.of(item(b, "L003"), item(b, "R003")), hoje.minusDays(30));
        b.registrarDevolucao(antigo, hoje.minusDays(20));
        // Ana: um livro, em dia
        b.registrarEmprestimo(ana, List.of(item(b, "L001")), hoje.minusDays(5));
        // Bruno: um DVD (prazo de 3 dias) emprestado há 6 dias -> atrasado
        b.registrarEmprestimo(bruno, List.of(item(b, "D002")), hoje.minusDays(6));
        // Ana: mais um livro e uma revista, em dia (fica com 3 itens: no limite)
        b.registrarEmprestimo(ana, List.of(item(b, "L004"), item(b, "R001")), hoje.minusDays(2));

        return b;
    }

    private static Item item(Biblioteca b, String codigo) {
        return b.getAcervo().stream()
                .filter(i -> i.getCodigo().equals(codigo))
                .findFirst()
                .orElseThrow();
    }
}
