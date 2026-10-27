package br.pucrs.poo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Exercício 2 — "Sua retrospectiva musical".
 *
 * O construtor percorre o histórico UMA vez e preenche os mapas.
 * O método imprimir() apenas lê os mapas e monta o relatório.
 *
 * Regra de negócio: reprodução com menos de 30 segundos é "pulada".
 * Ela conta no tempo total ouvido (e no tempo do artista), mas NÃO conta
 * como reprodução da música nem do gênero.
 */
public class Retrospectiva {

    private static final int LIMITE_PULADA = 30;

    private final List<Reproducao> historico;

    // ------------------------------------------------------------------
    // Passo 1 — decida chave e valor de cada mapa.
    // Os dois primeiros já estão declarados; declare os demais você mesmo.
    // ------------------------------------------------------------------
    private final Map<String, Integer> porMusica = new HashMap<>();          // música  -> nº de reproduções completas
    private final Map<String, Integer> segundosPorArtista = new HashMap<>(); // artista -> soma de segundos

    // TODO (Passo 5): mapa de reproduções completas por gênero
    // TODO (Passo 6): mapa de pulos por música
    // TODO (Passo 7): mapa de reproduções por hora (deve manter as horas em ordem!)
    // TODO (Passo 8): mapa de reproduções por faixa do dia (deve manter a ordem de inserção!)

    private int totalSegundos = 0;
    private int puladas = 0;

    public Retrospectiva(List<Reproducao> historico) {
        this.historico = historico;

        // TODO (Passo 8): antes do laço, insira as faixas com valor 0, nesta ordem:
        //                 "Madrugada", "Manhã", "Tarde", "Noite"

        for (Reproducao r : historico) {
            // TODO (Passo 3): some r.segundos() ao total e ao artista (todas as reproduções)

            // TODO (Passo 7): conte a reprodução na hora r.hora()
            // TODO (Passo 8): descubra a faixa do dia de r.hora() e atualize a contagem

            if (r.segundos() < LIMITE_PULADA) {
                // TODO (Passo 2): conte mais uma pulada
                // TODO (Passo 6): conte o pulo da música
            } else {
                // TODO (Passo 2): conte a reprodução da música (getOrDefault ou merge)
                // TODO (Passo 5): conte a reprodução do gênero
            }
        }
    }

    public void imprimir() {
        System.out.println("========== SUA RETROSPECTIVA 2026 ==========");

        // TODO (Passo 2): reproduções totais, completas e puladas
        // TODO (Passo 3): tempo total em "X min Y s" (use / 60 e % 60)

        System.out.println();
        System.out.println("Top 3 artistas");
        // TODO (Passo 4): copie segundosPorArtista.entrySet() para uma lista,
        //                 ordene por valor (decrescente) e mostre as 3 primeiras

        System.out.println();
        // TODO (Passo 5): música do ano (maior valor em porMusica)

        System.out.println();
        System.out.println("Gêneros");
        // TODO (Passo 5): percentual de cada gênero (use 100.0 * qtd / total!)
        //                 em ordem decrescente; empates em ordem alfabética

        System.out.println();
        // TODO (Passo 6): a música que você mais pulou

        System.out.println();
        System.out.println("Reproduções por hora");
        // TODO (Passo 7): histograma e horário de pico. Use o método pronto barra(valor, maior)
        //                 — ele já completa a barra com espaços até a largura certa.

        System.out.println();
        // TODO (Passo 8): faixas do dia e "Seu perfil: ..."
    }

    // Dica: métodos auxiliares deixam o imprimir() mais legível. Por exemplo:
    // private String formatarTempo(int segundos) { ... }
    // private String maiorChave(Map<String, Integer> mapa) { ... }

    /**
     * JÁ PRONTO — desenha uma barra do histograma. Com poucos dados, cada '#'
     * vale 1 reprodução; com muitos dados, a barra é reduzida para caber em
     * 40 caracteres. "maior" é o maior valor do histograma.
     */
    private static String barra(int valor, int maior) {
        int largura = Math.min(maior, 40);
        int tamanho = (int) Math.round((double) valor * largura / maior);
        return String.format("%-" + Math.max(5, largura) + "s", "#".repeat(tamanho));
    }
}
