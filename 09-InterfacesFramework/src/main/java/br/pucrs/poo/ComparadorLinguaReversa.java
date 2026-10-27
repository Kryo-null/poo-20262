package br.pucrs.poo;

import java.util.Comparator;

public class ComparadorLinguaReversa implements Comparator<Pais> {
    @Override
    public int compare(Pais um, Pais outro) {
        return outro.getLingua().compareTo(um.getLingua());

    }
}
