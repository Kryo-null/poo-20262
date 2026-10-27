package br.pucrs.poo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class App {

    public static void mainInicial(String[] args) {

        int vet[] = new int[] { 7, 4, 5, 2, 1, 9, 8, 6 };

        Arrays.sort(vet);

        for (int i : vet)
            System.out.print(i + " ");

        System.out.println("\n");

        List<String> lstr = new ArrayList<>();

        lstr.add("Patinhas");
        lstr.add("Zeh");
        lstr.add("Donald");
        lstr.add("Luis");
        lstr.add(0, "Huginho");

        for (String s : lstr)
            System.out.print(s + " ");
        System.out.println("\n\nOrdenando...");

        Collections.sort(lstr);

        for (String s : lstr)
            System.out.print(s + " ");
        System.out.println("\n");

    }

    public static void main(String[] args) {
        List<Pais> paises = new ArrayList<>();
        // = new LinkedList<>();

        Pais p1 = new Pais("ZR", "Zaire", 2300, "Frances");
        Pais p2 = new Pais("BR", "Brasil", 8500, "Portugues");

        paises.add(p1);
        paises.add(p2);
        paises.add(0, new Pais("CN", "China", 9500, "Chines"));
        paises.add(new Pais("SP", "Espanha", 500, "Espanhol"));
        paises.add(new Pais("UK", "Inglaterra", 130, "Ingles"));

        System.out.println("\n\nLista de paises...");
        for (Pais p : paises)
            System.out.println(p.toString());

        System.out.println("\n\nOrdenando...");

        // Collections.sort(paises);
        Collections.sort(paises, new ComparadorAreaDecrescente());

        // for (Pais p : paises) System.out.println(p.toString());
        // System.out.println("\n\n...");

        // if (p2.compareTo(p1) <= 0)
        // System.out.println("this é menor ou igual ao outro");
        // else
        // System.out.println("this é maior que o outro");

        // com classses comparadoras
        // Collections.sort(paises, new ComparadorCodigo());

        // Collections.sort(paises, new ComparadorLingua());
        // Collections.sort(paises, new ComparadorLinguaReversa());

        // com classes internas anonimas
        // Collections.sort(paises, Pais.getComparadorCodigo());
        // Collections.sort(paises, Pais.getComparadorCodigoRev());
        // Collections.sort(paises, Pais.getComparadorArea());

        // a partir de java 8...
        // usamos lambda expressions
        // Collections.sort(paises, (Pais a,Pais b)->a.getArea()-b.getArea());
        // Collections.sort(paises, (Pais a,Pais b)->b.getArea()-a.getArea());
        // Collections.sort(paises, (pais1, pais2) ->
        // pais1.getNome().compareTo(pais2.getNome()));

        for (Pais p : paises)
            System.out.println(p.toString());

        System.out.println("\n");

    }
}
