package br.pucrs.poo;

public class Pessoa {

    private final int codigo;
    private final String nome;
    private final Departamento dpto;
    private final int idade;

    public Pessoa(int umCodigo, String umNome, Departamento umDpto, int umaIdade) {
        codigo = umCodigo;
        nome = umNome;
        dpto = umDpto;
        idade = umaIdade;
    }

    public int getCodigo() {
        return codigo;
    }

    public int getIdade() {
        return idade;
    }

    public String getNome() {
        return nome;
    }

    public Departamento getDpto() {
        return dpto;
    }

    @Override
    public String toString() {
        /*return "Pessoa{" +
                "codigo=" + codigo +
                ", nome='" + nome + '\'' +
                ", dpto=" + dpto +
                ", idade=" + idade +
                '}';
        */        
        return String.format( "Pessoa{ %3d - %-15s - %-15s - %3d }",
                              codigo, nome, dpto.toString(), idade);
    }
}
