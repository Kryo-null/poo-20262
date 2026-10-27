package br.pucrs.poo;

import java.time.LocalDate;

// Boleto NÃO implementa Estornavel — depois de compensado, não há como estornar.
// Isso é proposital: mostra que interfaces são contratos opcionais por subclasse,
// diferente de um método herdado da classe abstrata (que toda subclasse carrega).
public class Boleto extends Pagamento {

    private static final double TAXA_FIXA = 3.49;

    public Boleto(String idTransacao, double valor, LocalDate data) {
        super(idTransacao, valor, data);
    }

    // Taxa fixa, independente do valor.
    @Override
    public double calcularTaxa() {
        return TAXA_FIXA;
    }

    @Override
    public void enviarNotificacao() {
        System.out.println("[Boleto] Boleto " + getIdTransacao() + " gerado. Vencimento em 3 dias úteis.");
    }
}
