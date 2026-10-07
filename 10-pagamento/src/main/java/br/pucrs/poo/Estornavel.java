package br.pucrs.poo;
/**
 * Contrato para formas de pagamento que podem ser estornadas (devolução de valor).
 * Nem toda forma de pagamento suporta estorno — por isso isto é uma interface,
 * e não um método da classe abstrata Pagamento. (Dada pronta, não precisa alterar.)
 */
public interface Estornavel {
    boolean estornar();
}
