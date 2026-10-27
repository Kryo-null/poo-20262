package br.pucrs.poo;

import java.util.List;

/**
 * Um pedido do marketplace.
 * "codigos" é uma List: o mesmo produto pode aparecer mais de uma vez
 * (por exemplo, duas unidades do mesmo item).
 */
public record Pedido(int numero, String cliente, List<String> codigos) {}
