package br.pucrs.poo;

import java.util.Comparator;

public class ComparadorLingua implements Comparator<Pais> {
    @Override
    public int compare(Pais um, Pais outro) {
        return um.getLingua().compareTo(outro.getLingua());
    }
}
