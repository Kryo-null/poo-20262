package br.pucrs.poo;

import java.util.Comparator;

public class ComparadorAreaDecrescente implements Comparator<Pais> {

    @Override
    public int compare(Pais p, Pais q) {
        // return (p.getArea() - q.getArea()) * (-1) ;

        // ou invertendo os parametros...
        return q.getArea() - p.getArea();
    }

}
