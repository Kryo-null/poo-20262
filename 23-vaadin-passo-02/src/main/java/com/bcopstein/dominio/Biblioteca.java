package com.bcopstein.dominio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A lógica da biblioteca, em Java puro.
 * Esta classe não conhece o Vaadin: a interface gráfica chama seus
 * métodos, e ela responde com dados ou lança RegraDeNegocioException.
 * (Neste passo, só o acervo. Empréstimos chegam no passo 03.)
 */
public class Biblioteca {
    private final List<Item> acervo = new ArrayList<>();

    public void cadastrarItem(Item item) {
        boolean codigoRepetido = acervo.stream()
                .anyMatch(i -> i.getCodigo().equals(item.getCodigo()));
        if (codigoRepetido) {
            throw new RegraDeNegocioException("Já existe um item com o código " + item.getCodigo());
        }
        acervo.add(item);
    }

    public List<Item> getAcervo() {
        return List.copyOf(acervo);
    }

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
}
