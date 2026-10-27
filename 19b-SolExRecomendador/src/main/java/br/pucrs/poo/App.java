package br.pucrs.poo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Exercício 3 — "Quem comprou isto também comprou…"
 * Ponto de entrada: carrega os dados e exibe o painel do marketplace.
 * Não é necessário alterar esta classe.
 */
public class App {

    // Comece com os dados de exemplo (a saída esperada está na lista).
    // Quando tudo estiver funcionando, troque para true:
    // ~50 produtos, 320 clientes e ~1.500 pedidos.
    private static final boolean USAR_DADOS_COMPLETOS = false;

    // Quantos clientes exibir nas listagens (os dados completos têm centenas)
    private static final int MAX_CLIENTES_NO_PAINEL = 10;

    public static void main(String[] args) {
        Locale.setDefault(Locale.forLanguageTag("pt-BR"));

        String pasta, produtoConsulta, clienteA, clienteB, clienteC;
        if (USAR_DADOS_COMPLETOS) {
            pasta = "dados/completo";
            produtoConsulta = "P001";
            clienteA = "Ana Almeida"; clienteB = "Paula Nunes"; clienteC = "Carla Lima";
        } else {
            pasta = "dados/exemplo";
            produtoConsulta = "P01";
            clienteA = "Ana"; clienteB = "Diego"; clienteC = "Carla";
        }

        int largura = USAR_DADOS_COMPLETOS ? 18 : 7;   // largura da coluna de nomes

        List<Produto> produtos = LeitorMarketplace.lerProdutos(pasta + "/produtos.csv");
        List<Pedido> pedidos = LeitorMarketplace.lerPedidos(pasta + "/pedidos.csv");
        Recomendador rec = new Recomendador(produtos, pedidos);

        System.out.println("Compras por cliente");
        int exibidos = 0;
        for (String cliente : rec.clientes()) {
            if (exibidos++ == MAX_CLIENTES_NO_PAINEL) {
                System.out.println("  ... e mais " + (rec.clientes().size() - MAX_CLIENTES_NO_PAINEL) + " clientes");
                break;
            }
            System.out.printf("  %-" + largura + "s%s%n", cliente, rec.comprasDe(cliente));
        }

        System.out.println();
        System.out.println("Quem comprou \"" + rec.nome(produtoConsulta) + "\" também comprou:");
        for (Map.Entry<String, Integer> e : rec.tambemCompraram(produtoConsulta, 3)) {
            System.out.printf("  %-22s (%d clientes)%n", rec.nome(e.getKey()), e.getValue());
        }

        System.out.println();
        System.out.printf("Similaridade %s x %s: %.2f%n", clienteA, clienteB, rec.similaridade(clienteA, clienteB));
        System.out.printf("Similaridade %s x %s: %.2f%n", clienteA, clienteC, rec.similaridade(clienteA, clienteC));

        System.out.println();
        System.out.println("Recomendações");
        exibidos = 0;
        for (String cliente : rec.clientes()) {
            if (exibidos++ == MAX_CLIENTES_NO_PAINEL) {
                System.out.println("  ...");
                break;
            }
            String parecido = rec.clienteMaisParecido(cliente);
            double sim = parecido == null ? 0.0 : rec.similaridade(cliente, parecido);
            List<String> recomendados = rec.recomendarPara(cliente);
            System.out.printf("  %-" + largura + "s(mais parecido: %-" + (largura - 1) + "s %.2f) -> %s%n",
                    cliente, parecido + ",", sim,
                    recomendados.isEmpty() ? "nenhuma novidade" : recomendados);
        }

        List<String> nomesEncalhados = new ArrayList<>();
        for (String codigo : rec.encalhados()) {
            nomesEncalhados.add(rec.nome(codigo));
        }
        System.out.println();
        System.out.println("Produtos sem nenhuma venda: " + nomesEncalhados);
        System.out.println("Catálogo continua com " + rec.tamanhoCatalogo() + " produtos");
    }
}
