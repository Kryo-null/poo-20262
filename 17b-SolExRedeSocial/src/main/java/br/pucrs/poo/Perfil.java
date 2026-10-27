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
public class Perfil implements Comparable<Perfil> {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Perfil)) return false;
        Perfil outro = (Perfil) o;
        return handle.equals(outro.handle);
    }

    @Override
    public int hashCode() {
        return handle.hashCode();
    }

    @Override
    public String toString() {
        return handle;
    }

    // ------------------------------------------------------------------
    // Passo 2 — seguir e deixar de seguir
    // ------------------------------------------------------------------

    /**
     * Faz este perfil seguir o outro, atualizando os DOIS lados da relação.
     * @return true se a relação foi criada; false se já existia
     *         ou se o perfil tentou seguir a si mesmo.
     */
    public boolean seguir(Perfil outro) {
        if (outro.equals(this)) return false;
        boolean novo = seguindo.add(outro);
        if (novo) outro.seguidores.add(this);
        return novo;
    }

    /**
     * Desfaz a relação, atualizando os DOIS lados.
     * @return true somente se a relação existia.
     */
    public boolean deixarDeSeguir(Perfil outro) {
        boolean existia = seguindo.remove(outro);
        if (existia) outro.seguidores.remove(this);
        return existia;
    }

    // ------------------------------------------------------------------
    // Passo 3 — proteja os conjuntos internos
    // ------------------------------------------------------------------

    public Set<Perfil> getSeguindo() {
        return Collections.unmodifiableSet(seguindo);
    }

    public Set<Perfil> getSeguidores() {
        return Collections.unmodifiableSet(seguidores);
    }

    // ------------------------------------------------------------------
    // Passo 4 — as três perguntas clássicas
    // Todos devolvem um NOVO conjunto: copie antes de usar
    // retainAll/removeAll!
    // ------------------------------------------------------------------

    /** Quem eu sigo e também me segue (seguindo ∩ seguidores). */
    public Set<Perfil> mutuos() {
        Set<Perfil> r = new TreeSet<>(seguindo);
        r.retainAll(seguidores);
        return r;
    }

    /** Quem eu sigo e não me segue (seguindo − seguidores). */
    public Set<Perfil> naoMeSegueDeVolta() {
        Set<Perfil> r = new TreeSet<>(seguindo);
        r.removeAll(seguidores);
        return r;
    }

    /** Quem me segue e eu não sigo (seguidores − seguindo). */
    public Set<Perfil> fas() {
        Set<Perfil> r = new TreeSet<>(seguidores);
        r.removeAll(seguindo);
        return r;
    }

    // ------------------------------------------------------------------
    // Passo 5 — seguidos em comum
    // ------------------------------------------------------------------

    /** Perfis seguidos tanto por este perfil quanto pelo outro. */
    public Set<Perfil> seguidosEmComum(Perfil outro) {
        Set<Perfil> r = new TreeSet<>(seguindo);
        r.retainAll(outro.seguindo);
        return r;
    }

    // ------------------------------------------------------------------
    // Passo 6 — sugestões para seguir
    // ------------------------------------------------------------------

    /** Pessoas seguidas por quem eu sigo, menos eu mesmo e quem já sigo. */
    public Set<Perfil> sugestoes() {
        Set<Perfil> r = new TreeSet<>();
        for (Perfil p : seguindo) r.addAll(p.seguindo);
        r.remove(this);
        r.removeAll(seguindo);
        return r;
    }

    // ------------------------------------------------------------------
    // Passo 7 — saída em ordem alfabética
    // ------------------------------------------------------------------
    @Override
    public int compareTo(Perfil outro) {
        return handle.compareTo(outro.handle);
    }
}
