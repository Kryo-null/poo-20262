package br.pucrs.poo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class CadastroPessoas implements Iterable<Pessoa>{
    private List<Pessoa> lista;

    public CadastroPessoas() {
        lista = new ArrayList<>();
    }

    public void inserir(Pessoa p) {
        lista.add(p);
    }

    public void listar() {
        for (Pessoa p : lista) {
                System.out.println(p);
            }
    }

    // Abordagem 1: escrever um método que filtra a lista com base
    // em determinado critério
    public void exibePessoasMaisIdosasQue(int idade) {
        for (Pessoa p : lista) {
            if (p.getIdade() >= idade) {
                System.out.println(p);
            }
        }
    }

    // Abordagem 2: escrever métodos de busca mais genéricos
    public  void exibePessoasFaixaEtaria(int min, int max) {
        for (Pessoa p : lista) {
            if (min <= p.getIdade() && p.getIdade() <= max) {
                System.out.println(p);
            }
        }
    }

    // Abordagem 3: especificar o critério de busca através de uma
    // interface e uma implementação dedicada (padrão Strategy!)
    public  void exibePessoas(VerificaPessoa testador) {
        for (Pessoa p : lista) {
            if (testador.testar(p)) {
                System.out.println(p);
            }
        }
    }

    // Abordagem 6: usando a interface funcional Predicate<T> ao invés de VerificaPessoa
    public  void exibePessoasPredicate(Predicate<Pessoa> testador) {
        for (Pessoa p : lista) {
            if (testador.test(p)) {
                System.out.println(p);
            }
        }
    }


    // Abordagem 7: usando uma interface funcional Consumer para realizar ações diferentes sobre objetos Pessoa
    public  void processaPessoas( Predicate<Pessoa> testador,
                                       Consumer<Pessoa> consumidor) {
        for(Pessoa p: lista) {
            if(testador.test(p)) {
                consumidor.accept(p);
            }
        }
    }

    // Usando uma interface funcional Function para extrair parte da informação
    public void processaPessoasFunction(Predicate<Pessoa> testador,
                                               Function<Pessoa, String> mapeador, Consumer<String> consumidor) {
        for (Pessoa p : lista) {
            if (testador.test(p)) {
                String dados = mapeador.apply(p);
                consumidor.accept(dados);
            }
        }
    }

    // Abordagem 8: criando um método genérico que faz a mesma coisa
    public static <X, Y> void processaElementos(Iterable<X> origem, Predicate<X> testador,
                                                Function <X, Y> mapeador, Consumer<Y> consumidor) {
        for (X p : origem) {
            if (testador.test(p)) {
                Y dados = mapeador.apply(p);
                consumidor.accept(dados);
            }
        }
    }

    @Override
    public Iterator<Pessoa> iterator() {
        return lista.iterator();
    }
}
