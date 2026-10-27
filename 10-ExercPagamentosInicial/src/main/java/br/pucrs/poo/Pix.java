package br.pucrs.poo;

import java.time.LocalDate;

public class Pix extends Pagamento implements Estornavel {

    public Pix(String idTransacao, double valor, LocalDate data) {
        super(idTransacao, valor, data);
    }

    // TODO: Pix não tem taxa de processamento — retorne 0.0
    @Override
    public double calcularTaxa() {
        return 0.0;
    }

    // TODO: imprima uma mensagem confirmando que o Pix foi recebido/confirmado
    @Override
    public void enviarNotificacao() {
    }

    // TODO: Pix só pode ser estornado em até 90 dias após a data do pagamento.
    // Compare getData() com LocalDate.now() usando ChronoUnit.DAYS ou Period.
    @Override
    public boolean estornar() {
        return false;
    }
}
