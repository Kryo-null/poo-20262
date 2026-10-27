package br.pucrs.poo;

public class Pais implements Comparable<Pais> {

    private String cod;
    private String nome;
    private int area;
    private String lingua;

    public Pais(String umCod, String umNome, int umaArea, String umaLingua) {
        this.cod = umCod;
        this.nome = umNome;
        this.area = umaArea;
        this.lingua = umaLingua;

    }

    public String getCod() {
        return cod;
    }

    public String getNome() {
        return nome;
    }

    public int getArea() {
        return area;
    }

    public String getLingua() {
        return lingua;
    }

    @Override
    public String toString() {
        return "Pais{" +
                "cod='" + cod + '\'' +
                ", nome='" + nome + '\'' +
                ", area=" + area +
                ", lingua='" + lingua + '\'' +
                '}';
    }

    // implementacao da interface Comparable<T>
    @Override
    public int compareTo(Pais outro) {
        // if (this.area == outro.area) {
        // return 0;
        // } else if (this.area < outro.area) {
        // return -1;
        // } else {
        // return 1;
        // }

        // como preciso retornar um negativo ou positivo qualquer
        // não preciso fazer os if's
        // return this.getArea() - outro.getArea();

        // como estamos dentro da classe Pais, não precisamos dos
        // getter's e podemos acessar diretamente os atributos
        // return this.area - outro.area;

        return (this.nome).compareTo(outro.nome);

        // return this.lingua.compareTo(outro.lingua);
    }

    // // classes internas anonomas para comparacao

    // public static Comparator<Pais> getComparadorCodigo() {

    // return new Comparator<Pais>() {
    // @Override
    // public int compare(Pais o1, Pais o2) {
    // return o1.getCod().compareTo(o2.getCod());
    // }
    // };

    // }

    // public static Comparator<Pais> getComparadorCodigoRev() {

    // return new Comparator<Pais>() {
    // @Override
    // public int compare(Pais o1, Pais o2) {
    // return o2.getCod().compareTo(o1.getCod());
    // }
    // };

    // }

    // public static Comparator<Pais> getComparadorArea() {

    // // eca... criando uma 'classe interna anonima'
    // // ==> new <nomeInterface>() { <métodos_da_interface> }
    // return new Comparator<Pais>() {
    // @Override
    // public int compare(Pais o1, Pais o2) {
    // return o1.getArea() - o2.getArea();
    // }
    // };
    // }

}
