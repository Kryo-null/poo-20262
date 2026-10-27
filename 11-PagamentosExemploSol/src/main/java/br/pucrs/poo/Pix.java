package br.pucrs.poo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Pix extends Pagamento implements Estornavel {

    private static final long PRAZO_ESTORNO_DIAS = 90;

    public Pix(String idTransacao, double valor, LocalDate data) {
        super(idTransacao, valor, data);
    }

    // Pix não tem taxa de processamento.
    @Override
    public double calcularTaxa() {
        return 0.0;
    }

    @Override
    public void enviarNotificacao() {
        System.out.println("[Pix] Pagamento " + getIdTransacao() + " confirmado instantaneamente.");
    }

    // Só pode ser estornado em até 90 dias após a data do pagamento.
    @Override
    public boolean estornar() {
        long diasDecorridos = ChronoUnit.DAYS.between(getData(), LocalDate.now());
        return diasDecorridos <= PRAZO_ESTORNO_DIAS;
    }
}
