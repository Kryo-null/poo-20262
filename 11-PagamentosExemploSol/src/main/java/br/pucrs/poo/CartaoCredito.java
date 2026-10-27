package br.pucrs.poo;

import java.time.LocalDate;

public class CartaoCredito extends Pagamento implements Estornavel {

    private static final double PERCENTUAL_TAXA = 0.035;
    private static final double TAXA_POR_PARCELA_EXTRA = 0.50;

    private final int parcelas;

    public CartaoCredito(String idTransacao, double valor, LocalDate data, int parcelas) {
        super(idTransacao, valor, data);
        this.parcelas = parcelas;
    }

    public int getParcelas() {
        return parcelas;
    }

    // 3,5% do valor, mais R$ 0,50 por parcela além da 1ª.
    @Override
    public double calcularTaxa() {
        double taxaPercentual = getValor() * PERCENTUAL_TAXA;
        int parcelasExtras = Math.max(0, parcelas - 1);
        double taxaParcelamento = parcelasExtras * TAXA_POR_PARCELA_EXTRA;
        return taxaPercentual + taxaParcelamento;
    }

    @Override
    public void enviarNotificacao() {
        System.out.println("[Cartão de Crédito] Pagamento " + getIdTransacao()
                + " aprovado em " + parcelas + "x.");
    }

    // Cartão de crédito sempre pode ser estornado.
    @Override
    public boolean estornar() {
        return true;
    }
}
