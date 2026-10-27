package br.pucrs.poo;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Leitura do histórico de reproduções. CLASSE JÁ PRONTA — não é necessário
 * alterá-la.
 *
 * Formato (texto UTF-8, campos separados por ';', linhas iniciadas por '#'
 * são comentários):
 *   musica;artista;genero;segundos;hora
 *   ex.: Segmentation Fault;Os Compiladores;Rock;215;8
 */
public class LeitorHistorico {

    public static List<Reproducao> ler(String arquivo) {
        List<String> linhas;
        try {
            linhas = Files.readAllLines(Path.of(arquivo), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Não foi possível ler o arquivo "
                    + Path.of(arquivo).toAbsolutePath()
                    + " — verifique se você está executando a partir da pasta do projeto.", e);
        }

        List<Reproducao> historico = new ArrayList<>();
        int numero = 0;
        for (String linha : linhas) {
            numero++;
            linha = linha.strip();
            if (linha.isEmpty() || linha.startsWith("#")) {
                continue;
            }
            String[] c = linha.split(";");
            try {
                historico.add(new Reproducao(c[0].strip(), c[1].strip(), c[2].strip(),
                        Integer.parseInt(c[3].strip()), Integer.parseInt(c[4].strip())));
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.err.println("Aviso: linha " + numero + " de " + arquivo + " ignorada: " + linha);
            }
        }
        return historico;
    }
}
