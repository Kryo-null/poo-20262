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
        // TODO (Passo 1): preencha o catálogo (código -> produto)

        // TODO (Passo 2): preencha produtosPorCliente
        //                 (versão explícita com get/null primeiro; depois computeIfAbsent)

        // TODO (Passo 3): preencha clientesPorProduto (índice invertido)
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
        // TODO (Passo 1): busque no catálogo
        return codigo;
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
        // TODO (Passo 4): conte em um Map<String, Integer>, ordene as entradas
        //                 e devolva as n primeiras
        return new ArrayList<>();
    }

    // ------------------------------------------------------------------
    // Passo 5 — similaridade de Jaccard
    // ------------------------------------------------------------------

    /** |interseção| / |união| das compras dos dois clientes (0 a 1). */
    public double similaridade(String c1, String c2) {
        // TODO (Passo 5): trabalhe com CÓPIAS dos conjuntos!
        return 0.0;
    }

    // ------------------------------------------------------------------
    // Passo 6 — recomendações personalizadas
    // ------------------------------------------------------------------

    /** O outro cliente com maior similaridade (null se nenhum tiver similaridade > 0). */
    public String clienteMaisParecido(String cliente) {
        // TODO (Passo 6)
        return null;
    }

    /** Nomes dos produtos que o cliente mais parecido comprou e este cliente ainda não. */
    public List<String> recomendarPara(String cliente) {
        // TODO (Passo 6): diferença de conjuntos; devolva NOMES, não códigos
        return new ArrayList<>();
    }

    // ------------------------------------------------------------------
    // Passo 7 — produtos encalhados
    // ------------------------------------------------------------------

    /** Códigos dos produtos do catálogo que nunca foram vendidos. */
    public Set<String> encalhados() {
        // TODO (Passo 7): CUIDADO — catalogo.keySet() é uma visão do mapa, copie antes!
        return new TreeSet<>();
    }
}
