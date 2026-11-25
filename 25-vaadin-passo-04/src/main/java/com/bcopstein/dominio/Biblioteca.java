package com.bcopstein.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * A lógica da biblioteca, em Java puro.
 * Esta classe não conhece o Vaadin: a interface gráfica chama seus
 * métodos, e ela responde com dados ou lança RegraDeNegocioException.
 */
public class Biblioteca {
    public static final int LIMITE_DE_ITENS = 3;

    private final List<Item> acervo = new ArrayList<>();
    private final List<Usuario> usuarios = new ArrayList<>();
    private final List<Emprestimo> emprestimos = new ArrayList<>();

    // ---------- Cadastros ----------

    public boolean existeCodigo(String codigo) {
        String procurado = codigo == null ? "" : codigo.trim().toUpperCase();
        return acervo.stream().anyMatch(i -> i.getCodigo().equals(procurado));
    }

    public void cadastrarItem(Item item) {
        if (existeCodigo(item.getCodigo())) {
            throw new RegraDeNegocioException("Já existe um item com o código " + item.getCodigo());
        }
        acervo.add(item);
    }

    public void cadastrarUsuario(Usuario usuario) {
        boolean matriculaRepetida = usuarios.stream()
                .anyMatch(u -> u.matricula().equals(usuario.matricula()));
        if (matriculaRepetida) {
            throw new RegraDeNegocioException("Já existe um usuário com a matrícula " + usuario.matricula());
        }
        usuarios.add(usuario);
    }

    public List<Item> getAcervo() {
        return List.copyOf(acervo);
    }

    public List<Usuario> getUsuarios() {
        return List.copyOf(usuarios);
    }

    public List<Emprestimo> getEmprestimos() {
        return List.copyOf(emprestimos);
    }

    // ---------- Consultas ao acervo ----------

    /**
     * Itens cujo título contém o trecho (sem diferenciar maiúsculas)
     * e que são do tipo indicado. Tipo null significa "todos os tipos".
     */
    public List<Item> buscar(String trechoDoTitulo, String tipo) {
        String trecho = trechoDoTitulo == null ? "" : trechoDoTitulo.trim().toLowerCase();
        return acervo.stream()
                .filter(i -> i.getTitulo().toLowerCase().contains(trecho))
                .filter(i -> tipo == null || i.getTipo().equals(tipo))
                .sorted(Comparator.comparing(Item::getTitulo))
                .toList();
    }

    public boolean estaDisponivel(Item item) {
        return emprestimos.stream()
                .filter(Emprestimo::isAtivo)
                .noneMatch(e -> e.getItens().contains(item));
    }

    public List<Item> getItensDisponiveis() {
        return acervo.stream()
                .filter(this::estaDisponivel)
                .sorted(Comparator.comparing(Item::getTitulo))
                .toList();
    }

    // ---------- Empréstimos ----------

    public List<Emprestimo> getEmprestimosAtivos() {
        return emprestimos.stream()
                .filter(Emprestimo::isAtivo)
                .toList();
    }

    public int quantidadeDeItensCom(Usuario usuario) {
        return getEmprestimosAtivos().stream()
                .filter(e -> e.getUsuario().equals(usuario))
                .mapToInt(e -> e.getItens().size())
                .sum();
    }

    /**
     * Registra um empréstimo de um ou mais itens.
     * A operação é validada como um todo: se qualquer regra for violada,
     * nada é registrado (tudo ou nada).
     */
    public Emprestimo registrarEmprestimo(Usuario usuario, List<Item> itens, LocalDate hoje) {
        if (usuario == null) {
            throw new RegraDeNegocioException("Selecione o usuário");
        }
        if (itens == null || itens.isEmpty()) {
            throw new RegraDeNegocioException("Inclua pelo menos um item");
        }
        if (itens.stream().distinct().count() < itens.size()) {
            throw new RegraDeNegocioException("O mesmo item foi incluído mais de uma vez");
        }
        for (Item item : itens) {
            if (!estaDisponivel(item)) {
                throw new RegraDeNegocioException("'" + item.getTitulo() + "' já está emprestado");
            }
        }
        boolean temAtraso = getEmprestimosAtivos().stream()
                .anyMatch(e -> e.getUsuario().equals(usuario) && e.estaAtrasado(hoje));
        if (temAtraso) {
            throw new RegraDeNegocioException(usuario.nome() + " tem empréstimo em atraso");
        }
        int jaEmprestados = quantidadeDeItensCom(usuario);
        if (jaEmprestados + itens.size() > LIMITE_DE_ITENS) {
            throw new RegraDeNegocioException("Limite de " + LIMITE_DE_ITENS + " itens por usuário: "
                    + usuario.nome() + " já está com " + jaEmprestados);
        }
        Emprestimo emprestimo = new Emprestimo(usuario, itens, hoje);
        emprestimos.add(emprestimo);
        return emprestimo;
    }

    /** Registra a devolução de todos os itens e devolve o valor da multa. */
    public double registrarDevolucao(Emprestimo emprestimo, LocalDate hoje) {
        if (emprestimo == null) {
            throw new RegraDeNegocioException("Selecione o empréstimo");
        }
        if (!emprestimo.isAtivo()) {
            throw new RegraDeNegocioException("Este empréstimo já foi devolvido");
        }
        double multa = emprestimo.calcularMulta(hoje);
        emprestimo.registrarDevolucao(hoje);
        return multa;
    }

    // ---------- Consultas gerenciais ----------

    public List<Emprestimo> getEmprestimosAtrasados(LocalDate hoje) {
        return emprestimos.stream()
                .filter(e -> e.estaAtrasado(hoje))
                .sorted(Comparator.comparing(Emprestimo::getDataLimite))
                .toList();
    }

    /** Empréstimos feitos entre as duas datas (inclusive), mais recentes primeiro. */
    public List<Emprestimo> getEmprestimosNoPeriodo(LocalDate inicio, LocalDate fim) {
        return emprestimos.stream()
                .filter(e -> !e.getDataEmprestimo().isBefore(inicio))
                .filter(e -> !e.getDataEmprestimo().isAfter(fim))
                .sorted(Comparator.comparing(Emprestimo::getDataEmprestimo).reversed())
                .toList();
    }

    /** Quantos itens de cada tipo foram emprestados no período. */
    public Map<String, Long> getItensEmprestadosPorTipo(LocalDate inicio, LocalDate fim) {
        return getEmprestimosNoPeriodo(inicio, fim).stream()
                .flatMap(e -> e.getItens().stream())
                .collect(Collectors.groupingBy(Item::getTipo, TreeMap::new, Collectors.counting()));
    }
}
