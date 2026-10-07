package br.pucrs.poo;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

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
        System.out.println("PIX realizado com sucesso!" + "\n" + valorTotal());
    }

    // TODO: Pix só pode ser estornado em até 90 dias após a data do pagamento.
    // Compare getData() com LocalDate.now() usando ChronoUnit.DAYS ou Period.
    @Override
    public boolean estornar() {
        if(ChronoUnit.DAYS.between(LocalDate.now(), getData()) <= 90){
            return true;
        } else return false;
    }
}
