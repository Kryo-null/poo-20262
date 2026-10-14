package br.pucrs.poo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Exercício 3 — "Quem comprou isto também comprou…"
 *
 * O construtor monta as três estruturas (Passos 1 a 3). Os demais métodos
 * são CONSULTAS: nenhum deles pode alterar catalogo, produtosPorCliente ou
 * clientesPorProduto.
 */
public class Recomendador {

    private final Map<String, Produto> catalogo = new LinkedHashMap<>();             // código  -> produto
    private final Map<String, Set<String>> produtosPorCliente = new TreeMap<>();     // cliente -> códigos comprados
    private final Map<String, Set<String>> clientesPorProduto = new TreeMap<>();     // código  -> clientes que compraram

    public Recomendador(List<Produto> produtos, List<Pedido> pedidos) {
        for (Produto p : produtos) {
            catalogo.put(p.codigo(), p);
        }
        for (Pedido p : pedidos) {
            produtosPorCliente.computeIfAbsent(p.cliente(), k -> new TreeSet<>()).addAll(p.codigos());
            for (String codigo : p.codigos()) {
                clientesPorProduto.computeIfAbsent(codigo, k -> new TreeSet<>()).add(p.cliente());
            }
        }
    }

    // ------------------------------------------------------------------
    // Métodos de acesso — JÁ PRONTOS (usados pelo App para exibir o painel)
    // ------------------------------------------------------------------

    /** Nomes dos clientes, em ordem alfabética. */
    public Set<String> clientes() {
        return Collections.unmodifiableSet(produtosPorCliente.keySet());
    }

    /** Códigos comprados por um cliente (conjunto vazio se não existir). */
    public Set<String> comprasDe(String cliente) {
        return Collections.unmodifiableSet(produtosPorCliente.getOrDefault(cliente, Set.of()));
    }

    public int tamanhoCatalogo() {
        return catalogo.size();
    }

    // ------------------------------------------------------------------
    // Passo 1
    // ------------------------------------------------------------------

    /** Nome do produto com o código informado. */
    public String nome(String codigo) {
        Produto p = catalogo.get(codigo);
        return p == null ? codigo : p.nome();
    }

    // ------------------------------------------------------------------
    // Passo 4 — "quem comprou isto também comprou…"
    // ------------------------------------------------------------------

    /**
     * Os n produtos mais comprados junto com "codigo", como entradas
     * (código do produto -> número de clientes), em ordem decrescente de
     * contagem; empates em ordem de código.
     */
    public List<Map.Entry<String, Integer>> tambemCompraram(String codigo, int n) {
        Map<String, Integer> contagem = new HashMap<>();
        for (String cliente : clientesPorProduto.getOrDefault(codigo, Set.of())) {
            for (String outro : produtosPorCliente.get(cliente)) {
                if (!outro.equals(codigo)) {
                    contagem.merge(outro, 1, Integer::sum);
                }
            }
        }
        List<Map.Entry<String, Integer>> ranking = new ArrayList<>(contagem.entrySet());
        ranking.sort((a, b) -> !a.getValue().equals(b.getValue())
                ? b.getValue() - a.getValue() : a.getKey().compareTo(b.getKey()));
        return ranking.subList(0, Math.min(n, ranking.size()));
    }

    // ------------------------------------------------------------------
    // Passo 5 — similaridade de Jaccard
    // ------------------------------------------------------------------

    /** |interseção| / |união| das compras dos dois clientes (0 a 1). */
    public double similaridade(String c1, String c2) {
        Set<String> inter = new HashSet<>(comprasDe(c1));
        inter.retainAll(comprasDe(c2));
        Set<String> uniao = new HashSet<>(comprasDe(c1));
        uniao.addAll(comprasDe(c2));
        return uniao.isEmpty() ? 0.0 : (double) inter.size() / uniao.size();
    }

    // ------------------------------------------------------------------
    // Passo 6 — recomendações personalizadas
    // ------------------------------------------------------------------

    /** O outro cliente com maior similaridade (null se nenhum tiver similaridade > 0). */
    public String clienteMaisParecido(String cliente) {
        String melhor = null;
        double maior = 0.0;
        for (String outro : produtosPorCliente.keySet()) {
            if (!outro.equals(cliente)) {
                double s = similaridade(cliente, outro);
                if (s > maior) {
                    maior = s;
                    melhor = outro;
                }
            }
        }
        return melhor;
    }

    /** Nomes dos produtos que o cliente mais parecido comprou e este cliente ainda não. */
    public List<String> recomendarPara(String cliente) {
        List<String> nomes = new ArrayList<>();
        String parecido = clienteMaisParecido(cliente);
        if (parecido == null) return nomes;
        Set<String> novos = new TreeSet<>(comprasDe(parecido));
        novos.removeAll(comprasDe(cliente));
        for (String codigo : novos) nomes.add(nome(codigo));
        return nomes;
    }

    // ------------------------------------------------------------------
    // Passo 7 — produtos encalhados
    // ------------------------------------------------------------------

    /** Códigos dos produtos do catálogo que nunca foram vendidos. */
    public Set<String> encalhados() {
        Set<String> encalhados = new TreeSet<>(catalogo.keySet());
        encalhados.removeAll(clientesPorProduto.keySet());
        return encalhados;
    }
}
