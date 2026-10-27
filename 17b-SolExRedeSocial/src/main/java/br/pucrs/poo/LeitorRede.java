package br.pucrs.poo;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Leitura dos arquivos de dados da rede social. CLASSE JÁ PRONTA — não é
 * necessário alterá-la.
 *
 * Formato dos arquivos (texto UTF-8, campos separados por ';', linhas
 * iniciadas por '#' são comentários):
 *   perfis.csv    ->  handle;nome          ex.: @ana;Ana
 *   conexoes.csv  ->  seguidor;seguido     ex.: @ana;@bruno  (a @ana segue o @bruno)
 */
public class LeitorRede {

    /**
     * Lê o arquivo de perfis e devolve um mapa handle -> Perfil,
     * na mesma ordem do arquivo.
     */
    public static Map<String, Perfil> lerPerfis(String arquivo) {
        Map<String, Perfil> perfis = new LinkedHashMap<>();
        for (String[] campos : lerRegistros(arquivo, 2)) {
            perfis.put(campos[0], new Perfil(campos[0], campos[1]));
        }
        return perfis;
    }

    /**
     * Lê o arquivo de conexões e chama seguir(...) para cada linha.
     * Mostra um resumo: quantas linhas foram lidas e quantas conexões
     * foram efetivamente criadas (seguir devolveu true).
     */
    public static void lerConexoes(String arquivo, Map<String, Perfil> perfis) {
        int lidas = 0, criadas = 0, desconhecidos = 0;
        for (String[] campos : lerRegistros(arquivo, 2)) {
            lidas++;
            Perfil seguidor = perfis.get(campos[0]);
            Perfil seguido = perfis.get(campos[1]);
            if (seguidor == null || seguido == null) {
                desconhecidos++;
            } else if (seguidor.seguir(seguido)) {
                criadas++;
            }
        }
        System.out.printf("Conexões lidas: %d | criadas: %d | ignoradas: %d%n",
                lidas, criadas, lidas - criadas);
        if (desconhecidos > 0) {
            System.out.println("  (" + desconhecidos + " linha(s) citam perfis que não existem)");
        }
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
