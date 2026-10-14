package br.pucrs.poo;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Leitura dos arquivos do marketplace. CLASSE JÁ PRONTA — não é necessário
 * alterá-la.
 *
 * Formato (texto UTF-8, campos separados por ';', linhas iniciadas por '#'
 * são comentários):
 *   produtos.csv  ->  codigo;nome;preco               ex.: P01;Teclado mecânico;349.90
 *   pedidos.csv   ->  numero;cliente;codigos          ex.: 1001;Ana;P01,P02,P07
 *                     (os códigos do pedido são separados por vírgula)
 */
public class LeitorMarketplace {

    public static List<Produto> lerProdutos(String arquivo) {
        List<Produto> produtos = new ArrayList<>();
        for (String[] c : lerRegistros(arquivo, 3)) {
            try {
                produtos.add(new Produto(c[0], c[1], Double.parseDouble(c[2])));
            } catch (NumberFormatException e) {
                System.err.println("Aviso: preço inválido ignorado em " + arquivo + ": " + String.join(";", c));
            }
        }
        return produtos;
    }

    public static List<Pedido> lerPedidos(String arquivo) {
        List<Pedido> pedidos = new ArrayList<>();
        for (String[] c : lerRegistros(arquivo, 3)) {
            try {
                List<String> codigos = new ArrayList<>();
                for (String codigo : c[2].split(",")) {
                    if (!codigo.isBlank()) {
                        codigos.add(codigo.strip());
                    }
                }
                pedidos.add(new Pedido(Integer.parseInt(c[0]), c[1], List.copyOf(codigos)));
            } catch (NumberFormatException e) {
                System.err.println("Aviso: número de pedido inválido em " + arquivo + ": " + String.join(";", c));
            }
        }
        return pedidos;
    }

    /** Lê as linhas úteis do arquivo, já separadas em campos. */
    private static List<String[]> lerRegistros(String arquivo, int camposEsperados) {
        List<String> linhas;
        try {
            linhas = Files.readAllLines(Path.of(arquivo), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Não foi possível ler o arquivo "
                    + Path.of(arquivo).toAbsolutePath()
                    + " — verifique se você está executando a partir da pasta do projeto.", e);
        }
        List<String[]> registros = new ArrayList<>();
        int numero = 0;
        for (String linha : linhas) {
            numero++;
            linha = linha.strip();
            if (linha.isEmpty() || linha.startsWith("#")) {
                continue;
            }
            String[] campos = linha.split(";");
            if (campos.length != camposEsperados) {
                System.err.println("Aviso: linha " + numero + " de " + arquivo + " ignorada: " + linha);
                continue;
            }
            for (int i = 0; i < campos.length; i++) {
                campos[i] = campos[i].strip();
            }
            registros.add(campos);
        }
        return registros;
    }
}
