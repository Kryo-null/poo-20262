package br.pucrs.poo;

import java.time.LocalDate;

/**
 * Classe abstrata que representa um pagamento genérico no marketplace.
 * Reúne o que toda forma de pagamento tem em comum (dados + notificação + ordenação);
 * cada subclasse decide como calcular sua própria taxa.
 */
public abstract class Pagamento implements Notificavel, Comparable<Pagamento> {

    private final String idTransacao;
    private final double valor;
    private final LocalDate data;

    public Pagamento(String idTransacao, double valor, LocalDate data) {
        this.idTransacao = idTransacao;
        this.valor = valor;
        this.data = data;
    }

    public String getIdTransacao() {
        return idTransacao;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    /**
     * Cada forma de pagamento calcula a taxa de processamento de um jeito diferente.
     * Implementado em cada subclasse (Pix, CartaoCredito, Boleto).
     */
    public abstract double calcularTaxa();

    // Valor total pago = valor da compra + taxa de processamento.
    public double valorTotal() {
        return valor + calcularTaxa();
    }

    // Ordem natural: pelo valor total, do menor para o maior.
    @Override
    public int compareTo(Pagamento outro) {
        return Double.compare(this.valorTotal(), outro.valorTotal());
    }

    @Override
    public String toString() {
        return String.format("%s [%s] valor: R$ %.2f | taxa: R$ %.2f | total: R$ %.2f",
                getClass().getSimpleName(), idTransacao, valor, calcularTaxa(), valorTotal());
    }
}
