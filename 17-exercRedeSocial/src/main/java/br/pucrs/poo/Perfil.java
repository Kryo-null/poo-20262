package br.pucrs.poo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Um perfil da rede social.
 *
 * Exercício 1 — "Quem não me segue de volta?"
 * Siga o roteiro da lista: cada método abaixo está marcado com o passo
 * em que deve ser implementado. Os corpos atuais são apenas provisórios,
 * para que o projeto compile desde o início.
 */
public class Perfil {

    private final String handle;   // ex.: "@ana" — único na rede
    private final String nome;
    private final Set<Perfil> seguindo   = new HashSet<>();
    private final Set<Perfil> seguidores = new HashSet<>();

    public Perfil(String handle, String nome) {
        this.handle = handle;
        this.nome = nome;
    }

    public String getHandle() {
        return handle;
    }

    public String getNome() {
        return nome;
    }

    // ------------------------------------------------------------------
    // Passo 1 — equals, hashCode e toString
    // ------------------------------------------------------------------
    // Rode o App ANTES de implementar estes métodos e observe o tamanho
    // do conjunto de teste. Depois, descomente e complete-os.
    //
    // ATENÇÃO: use SOMENTE o handle. Nunca use os conjuntos seguindo/seguidores
    // aqui (StackOverflowError!).

    // @Override
    // public boolean equals(Object o) {
    //     // TODO (Passo 1)
    // }

    // @Override
    // public int hashCode() {
    //     // TODO (Passo 1)
    // }

    // @Override
    // public String toString() {
    //     // TODO (Passo 1): devolva o handle
    // }

    // ------------------------------------------------------------------
    // Passo 2 — seguir e deixar de seguir
    // ------------------------------------------------------------------

    /**
     * Faz este perfil seguir o outro, atualizando os DOIS lados da relação.
     * @return true se a relação foi criada; false se já existia
     *         ou se o perfil tentou seguir a si mesmo.
     */
    public boolean seguir(Perfil outro) {
        // TODO (Passo 2): use o retorno de seguindo.add(...)
        return false;
    }

    /**
     * Desfaz a relação, atualizando os DOIS lados.
     * @return true somente se a relação existia.
     */
    public boolean deixarDeSeguir(Perfil outro) {
        // TODO (Passo 2): use o retorno de seguindo.remove(...)
        return false;
    }

    // ------------------------------------------------------------------
    // Passo 3 — proteja os conjuntos internos
    // ------------------------------------------------------------------

    public Set<Perfil> getSeguindo() {
        // TODO (Passo 3): devolva uma visão somente leitura ou uma cópia
        return seguindo;
    }

    public Set<Perfil> getSeguidores() {
        // TODO (Passo 3): devolva uma visão somente leitura ou uma cópia
        return seguidores;
    }

    // ------------------------------------------------------------------
    // Passo 4 — as três perguntas clássicas
    // Todos devolvem um NOVO conjunto: copie antes de usar
    // retainAll/removeAll!
    // ------------------------------------------------------------------

    /** Quem eu sigo e também me segue (seguindo ∩ seguidores). */
    public Set<Perfil> mutuos() {
        // TODO (Passo 4)
        return new HashSet<>();
    }

    /** Quem eu sigo e não me segue (seguindo − seguidores). */
    public Set<Perfil> naoMeSegueDeVolta() {
        // TODO (Passo 4)
        return new HashSet<>();
    }

    /** Quem me segue e eu não sigo (seguidores − seguindo). */
    public Set<Perfil> fas() {
        // TODO (Passo 4)
        return new HashSet<>();
    }

    // ------------------------------------------------------------------
    // Passo 5 — seguidos em comum
    // ------------------------------------------------------------------

    /** Perfis seguidos tanto por este perfil quanto pelo outro. */
    public Set<Perfil> seguidosEmComum(Perfil outro) {
        // TODO (Passo 5): lembre que você pode acessar outro.seguindo
        return new HashSet<>();
    }

    // ------------------------------------------------------------------
    // Passo 6 — sugestões para seguir
    // ------------------------------------------------------------------

    /** Pessoas seguidas por quem eu sigo, menos eu mesmo e quem já sigo. */
    public Set<Perfil> sugestoes() {
        // TODO (Passo 6): união -> remover a si mesmo -> remover quem já sigo
        return new HashSet<>();
    }

    // ------------------------------------------------------------------
    // Passo 7 — saída em ordem alfabética
    // ------------------------------------------------------------------
    // TODO (Passo 7): faça a classe implementar Comparable<Perfil>,
    // escreva o compareTo (comparando handles) e troque, nos métodos dos
    // Passos 4 a 6, "new HashSet<>" por "new TreeSet<>".
    //
    // public int compareTo(Perfil outro) { ... }
}
