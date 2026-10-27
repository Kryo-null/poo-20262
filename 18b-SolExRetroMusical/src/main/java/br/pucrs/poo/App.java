package br.pucrs.poo;

import java.util.List;
import java.util.Locale;

/**
 * Exercício 2 — "Sua retrospectiva musical".
 * Ponto de entrada. Não é necessário alterar esta classe.
 */
public class App {

    // Comece com os dados de exemplo (a saída esperada está na lista).
    // Quando tudo estiver funcionando, troque para true: um ano inteiro, ~5.200 reproduções.
    private static final boolean USAR_DADOS_COMPLETOS = false;

    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("pt-BR"));
        String pasta = USAR_DADOS_COMPLETOS ? "dados/completo" : "dados/exemplo";

        List<Reproducao> historico = LeitorHistorico.ler(pasta + "/historico.csv");

        Retrospectiva retro = new Retrospectiva(historico);
        retro.imprimir();
    }
}
