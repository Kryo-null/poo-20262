package br.pucrs.poo;

 import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** GABARITO — Exercício 2. */
public class Retrospectiva {

    private static final int LIMITE_PULADA = 30;

    private final List<Reproducao> historico;
    private final Map<String, Integer> porMusica = new HashMap<>();
    private final Map<String, Integer> segundosPorArtista = new HashMap<>();
    private final Map<String, Integer> porGenero = new HashMap<>();
    private final Map<String, Integer> pulosPorMusica = new HashMap<>();
    private final Map<Integer, Integer> porHora = new TreeMap<>();
    private final Map<String, Integer> porFaixa = new LinkedHashMap<>();
    private int totalSegundos = 0;
    private int puladas = 0;

    public Retrospectiva(List<Reproducao> historico) {
        this.historico = historico;
        porFaixa.put("Madrugada", 0);
        porFaixa.put("Manhã", 0);
        porFaixa.put("Tarde", 0);
        porFaixa.put("Noite", 0);
        for (Reproducao r : historico) {
            totalSegundos += r.segundos();
            segundosPorArtista.merge(r.artista(), r.segundos(), Integer::sum);
            porHora.merge(r.hora(), 1, Integer::sum);
            String faixa = faixaDoDia(r.hora());
            porFaixa.put(faixa, porFaixa.get(faixa) + 1);
            if (r.segundos() < LIMITE_PULADA) {
                puladas++;
                pulosPorMusica.merge(r.musica(), 1, Integer::sum);
            } else {
                porMusica.merge(r.musica(), 1, Integer::sum);
                porGenero.merge(r.genero(), 1, Integer::sum);
            }
        }
    }

    private static String faixaDoDia(int hora) {
        if (hora < 6) return "Madrugada";
        if (hora < 12) return "Manhã";
        if (hora < 18) return "Tarde";
        return "Noite";
    }

    private static <K> K maiorChave(Map<K, Integer> mapa) {
        K melhor = null;
        for (Map.Entry<K, Integer> e : mapa.entrySet()) {
            if (melhor == null || e.getValue() > mapa.get(melhor)) melhor = e.getKey();
        }
        return melhor;
    }

    private static String formatarTempo(int segundos) {
        return String.format("%d min %d s", segundos / 60, segundos % 60);
    }

    public void imprimir() {
        int completas = historico.size() - puladas;
        System.out.println("========== SUA RETROSPECTIVA 2026 ==========");
        System.out.printf("Reproduções: %d (%d completas, %d puladas)%n", historico.size(), completas, puladas);
        System.out.println("Tempo total ouvido: " + formatarTempo(totalSegundos));

        System.out.println();
        System.out.println("Top 3 artistas");
        List<Map.Entry<String, Integer>> ranking = new ArrayList<>(segundosPorArtista.entrySet());
        ranking.sort((a, b) -> b.getValue() - a.getValue());
        for (int i = 0; i < Math.min(3, ranking.size()); i++) {
            Map.Entry<String, Integer> e = ranking.get(i);
            System.out.printf("  %d. %-16s %2d min %2d s%n", i + 1, e.getKey(), e.getValue() / 60, e.getValue() % 60);
        }

        System.out.println();
        String musicaDoAno = maiorChave(porMusica);
        System.out.printf("Música do ano: %s (%d reproduções)%n", musicaDoAno, porMusica.get(musicaDoAno));

        System.out.println();
        System.out.println("Gêneros");
        List<Map.Entry<String, Integer>> generos = new ArrayList<>(porGenero.entrySet());
        generos.sort((a, b) -> !a.getValue().equals(b.getValue())
                ? b.getValue() - a.getValue() : a.getKey().compareTo(b.getKey()));
        for (Map.Entry<String, Integer> e : generos) {
            System.out.printf("  %-11s %5.1f%%%n", e.getKey(), 100.0 * e.getValue() / completas);
        }

        System.out.println();
        String maisPulada = maiorChave(pulosPorMusica);
        System.out.printf("A que você mais pulou: %s (%d vezes)%n", maisPulada, pulosPorMusica.get(maisPulada));

        System.out.println();
        System.out.println("Reproduções por hora");
        int maior = porHora.get(maiorChave(porHora));
        for (Map.Entry<Integer, Integer> e : porHora.entrySet()) {
            System.out.printf("  %02dh | %s %d%n", e.getKey(), barra(e.getValue(), maior), e.getValue());
        }
        System.out.println("Horário de pico: " + maiorChave(porHora) + "h");

        System.out.println();
        System.out.println("Faixas do dia: " + porFaixa);
        String faixa = maiorChave(porFaixa);
        String perfil = switch (faixa) {
            case "Madrugada" -> "insone criativo";
            case "Manhã" -> "madrugador produtivo";
            case "Tarde" -> "trilha sonora do expediente";
            default -> "coruja noturna";
        };
        System.out.println("Seu perfil: " + perfil);
    }

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
