package br.pucrs.poo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class App {
    public static void main(String[] args) {
        List<Pagamento> pagamentos = new ArrayList<>();
        pagamentos.add(new Pix("TX001", 150.00, LocalDate.of(2026, 3, 10)));
        pagamentos.add(new CartaoCredito("TX002", 899.90, LocalDate.of(2026, 3, 12), 6));
        pagamentos.add(new Boleto("TX003", 320.00, LocalDate.of(2026, 3, 5)));

        System.out.println("\n=== Todos os pagamentos (ordem de cadastro) ===");
        for (Pagamento p : pagamentos) {
            System.out.println(p);
        }

        // TODO 4 (resolvido): notifica todos os pagamentos de forma polimórfica —
        // o laço não sabe (nem precisa saber) se é Pix, CartaoCredito ou Boleto.
        System.out.println("\n=== Notificações ===");
        for (Pagamento p : pagamentos) {
            p.enviarNotificacao();
        }

        // TODO 5 (resolvido): ordena pela ordem natural (valor total), usando o
        // compareTo() implementado em Pagamento.
        Collections.sort(pagamentos);
        System.out.println("\n=== Ordenados por valor total (ordem natural) ===");
        for (Pagamento p : pagamentos) {
            System.out.println(p);
        }

        // TODO 6 (resolvido): Comparator externo por data (mais antigo primeiro).
        Comparator<Pagamento> porData = Comparator.comparing(Pagamento::getData);
        pagamentos.sort(porData);
        System.out.println("\n=== Ordenados por data ===");
        for (Pagamento p : pagamentos) {
            System.out.println(p.getData() + " -> " + p);
        }

        // TODO 7 (resolvido): estorna apenas quem implementa Estornavel — Boleto
        // fica de fora naturalmente, sem precisar de um "if (p instanceof Boleto) continue".
        // Obs.: "p instanceof Estornavel estornavel" é pattern matching (Java 16+).
        // Em versões anteriores, use: if (p instanceof Estornavel) { Estornavel estornavel = (Estornavel) p; ... }
        System.out.println("\n=== Tentativas de estorno ===");
        for (Pagamento p : pagamentos) {
            if (p instanceof Estornavel estornavel) {
                boolean sucesso = estornavel.estornar();
                System.out.println(p.getIdTransacao() + ": " + (sucesso ? "estornado" : "fora do prazo"));
            } else {
                System.out.println(p.getIdTransacao() + ": não é estornável (" + p.getClass().getSimpleName() + ")");
            }
        }
    }
}
