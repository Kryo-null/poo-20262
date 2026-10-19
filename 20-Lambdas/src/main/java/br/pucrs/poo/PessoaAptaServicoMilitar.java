package br.pucrs.poo;

public class PessoaAptaServicoMilitar implements VerificaPessoa {
    public boolean testar(Pessoa p) {
        return p.getGenero() == Genero.MASC && p.getIdade() >= 18 && p.getIdade() <= 25;
    }
}
