package br.pucrs.poo;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Exercício 1 — "Quem não me segue de volta?"
 * Ponto de entrada: carrega a rede dos arquivos e exibe o painel de um perfil.
 * Você só precisa alterar esta classe no Passo 7.
 */
public class App {

    // Comece com os dados de exemplo (a saída esperada está na lista).
    // Quando tudo estiver funcionando, troque para true: ~240 perfis e ~6.500 conexões.
    private static final boolean USAR_DADOS_COMPLETOS = false;

    public static void main(String[] args) {
        String pasta = USAR_DADOS_COMPLETOS ? "dados/completo" : "dados/exemplo";

        // ---------- Passo 1: experimento ----------
        Set<Perfil> teste = new HashSet<>();
        teste.add(new Perfil("@ana", "Ana"));
        teste.add(new Perfil("@ana", "Ana Clara"));
        System.out.println("Perfis no conjunto de teste: " + teste.size());

        // ---------- Carga da rede (leitura já pronta) ----------
        Map<String, Perfil> rede = LeitorRede.lerPerfis(pasta + "/perfis.csv");
        System.out.println("Perfis carregados: " + rede.size());
        LeitorRede.lerConexoes(pasta + "/conexoes.csv", rede);

        Perfil ana = rede.get("@ana");
        Perfil bruno = rede.get("@bruno");
        Perfil davi = rede.get("@davi");

        // ---------- Passo 2: testes de seguir ----------
        System.out.println("ana segue a si mesma? " + ana.seguir(ana));
        System.out.println("ana segue bruno de novo? " + ana.seguir(bruno));

        // ---------- Painel ----------
        // Para testar outros perfis, troque os handles abaixo (ex.: "@bruno").
        exibirPainel(ana, davi);
    }

    private static void exibirPainel(Perfil p, Perfil outro) {
        System.out.println();
        System.out.println("=== Painel de " + p.getHandle() + " ===");
        // Passo 7: troque p.getSeguindo() por new TreeSet<>(p.getSeguindo())
        //          (e o mesmo para getSeguidores) para exibir em ordem alfabética.
        System.out.println("Seguindo:               " + p.getSeguindo());
        System.out.println("Seguidores:             " + p.getSeguidores());
        System.out.println("Mútuos:                 " + p.mutuos());
        System.out.println("Não me segue de volta:  " + p.naoMeSegueDeVolta());
        System.out.println("Fãs:                    " + p.fas());
        System.out.println("Em comum com " + outro.getHandle() + ":     " + p.seguidosEmComum(outro));
        System.out.println("Sugestões para seguir:  " + p.sugestoes());
    }
}
