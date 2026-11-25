package com.bcopstein.dominio;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Um empréstimo: um usuário leva um ou mais itens numa mesma data.
 * Cada item tem seu próprio prazo (polimorfismo), então cada um
 * pode ter uma data-limite diferente.
 * Empréstimos são criados pela Biblioteca, que valida as regras antes.
 */
public class Emprestimo {
    private static int proximoNumero = 1;

    private final int numero;
    private final Usuario usuario;
    private final List<Item> itens;
    private final LocalDate dataEmprestimo;
    private LocalDate dataDevolucao; // null enquanto o empréstimo está ativo

    Emprestimo(Usuario usuario, List<Item> itens, LocalDate dataEmprestimo) {
        this.numero = proximoNumero++;
        this.usuario = usuario;
        this.itens = List.copyOf(itens);
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = null;
    }

    public int getNumero() {
        return numero;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public List<Item> getItens() {
        return itens;
    }

    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }

    public LocalDate getDataDevolucao() {
        return dataDevolucao;
    }

    public boolean isAtivo() {
        return dataDevolucao == null;
    }

    /** Data-limite de um item: depende do prazo do seu tipo. */
    public LocalDate getDataLimite(Item item) {
        return dataEmprestimo.plusDays(item.getPrazoEmDias());
    }

    /** A data-limite mais próxima entre os itens do empréstimo. */
    public LocalDate getDataLimite() {
        return itens.stream()
                .map(this::getDataLimite)
                .min(Comparator.naturalOrder())
                .orElseThrow();
    }

    public boolean estaAtrasado(LocalDate hoje) {
        return isAtivo() && hoje.isAfter(getDataLimite());
    }

    /** Soma, item a item, dias de atraso x multa diária do tipo do item. */
    public double calcularMulta(LocalDate dataDevolucao) {
        return itens.stream()
                .mapToDouble(item -> diasDeAtraso(item, dataDevolucao) * item.getMultaPorDia())
                .sum();
    }

    private long diasDeAtraso(Item item, LocalDate data) {
        long dias = ChronoUnit.DAYS.between(getDataLimite(item), data);
        return Math.max(dias, 0);
    }

    /** Títulos dos itens separados por vírgula, para exibição. */
    public String getDescricaoItens() {
        return itens.stream()
                .map(Item::getTitulo)
                .collect(Collectors.joining(", "));
    }

    void registrarDevolucao(LocalDate data) {
        this.dataDevolucao = data;
    }
}
