package br.pucrs.poo;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class App {

    public static void main(String[] args) {
         List<Pessoa> lista = Arrays.asList(
                new Pessoa(1, "Huguinho", Departamento.FINANCEIRO, 40),
                new Pessoa(4, "Zezinho", Departamento.FINANCEIRO, 32),
                new Pessoa(3, "Luizinho", Departamento.VENDAS, 57),
                new  Pessoa(9, "Patinhas",   Departamento.VENDAS, 40),
                new Pessoa(10, "Donald", Departamento.GERENCIA, 54),
                new Pessoa(2, "Margarida", Departamento.FINANCEIRO, 40),
                new Pessoa(8, "Joe Doe", Departamento.VENDAS, 34),
                new Pessoa(5, "Jane Doe", Departamento.VENDAS, 22),
                new Pessoa(6, "Sr Smith", Departamento.VENDAS, 40),
                new Pessoa(7, "Sra Smith", Departamento.GERENCIA, 39),
                 new Pessoa(11, "Trinity", Departamento.VENDAS, 34),
                 new Pessoa(14, "Morpheus", Departamento.FINANCEIRO, 22),
                 new Pessoa(16, "AgenteSmith", Departamento.VENDAS, 47),
                 new Pessoa(13, "Neo", Departamento.GERENCIA, 29)
        );

        System.out.println("\n\n");
        System.out.println("\nOrdenado pela idade");    
        lista = lista.stream()  
                //sorted( (Pessoa p1, Pessoa p2) -> p1.getIdade() - p2.getIdade())
                .sorted( Comparator.comparing( Pessoa::getIdade ))
                .toList();

                
        lista.forEach( System.out::println );



        System.out.println("\n\n");
        System.out.println("\nOrdenado pelo departamento (e ordem alfabética)");
        List<Pessoa> nova = lista.stream()  
                      .filter( p-> p.getIdade() <= 40)
                      .sorted(Comparator.comparing( Pessoa::getDpto )
                                        .thenComparing( Pessoa::getNome )
                                        .reversed()
                              )   
                      .collect(Collectors.toList());

        nova.forEach(System.out::println);



        

       //List<Pessoa> novos = new ArrayList<>(); 
       //for (Pessoa p : lista) 
        //   if (p.getIdade() < 30)
        //      novos.add(p);

       List<Pessoa> novos = lista.stream()
                                    .filter((Pessoa p) -> p.getIdade() < 30)
                                    .collect(Collectors.toList());
        
        //for (Pessoa p : novos) 
        //    System.out.println(p);


        System.out.println("\nMenores de 30 anos...");
        novos.forEach( p->System.out.printf("\t-> %s\n",p.getNome()) );
        System.out.println();
  
        
      // List<String> l1=   lista.stream()
      var l1=   lista.stream()
             .filter((Pessoa p) -> p.getIdade() < 35)
             .map( x -> x.getNome())
             .collect( Collectors.toList() );
             //.forEach( p -> System.out.println(p));

        l1.forEach( (String s) -> System.out.print(s + " "));     
        System.out.println();

        List<Double> resultado = lista.stream()
                                    .filter((Pessoa p) -> p.getIdade() < 35)
                                    .map( (Pessoa x) -> Math.pow(x.getIdade(), 3))
                                    .collect( Collectors.toList() );

          resultado.forEach( System.out::println );    
          System.out.println();   


     //List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 10, 12);
     List<Integer> numbers = List.of(1, 2, 3, 4, 5, 6, 7, 8, 10, 12);
     
     List<Integer> twoEvenSquares = 
           numbers.stream()
                  .filter(n -> //n % 2 == 0
                              {
                                  System.out.println("filtering " + n);
                                  return n % 2 == 0;
                                }
                                )
                    .map(n -> {
                                System.out.println("mapping " + n);
                                return n * n;
                              })
                    .skip(2)              
                    .limit(2)
                    .collect(Collectors.toList());
        System.out.println("\narray original");

        numbers.forEach(System.out::println);

        System.out.println("\nquadrado dos pares...");           
        twoEvenSquares.forEach(System.out::println);

        System.out.println();
        //List<String> list = Arrays.asList("The", "Quick", "BROWN", "Fox", "Jumped", "Over", "The", "LAZY", "DOG"); 
        List<String> list = List.of("The", "Quick", "BROWN", "Fox", "Jumped", "Over", "The", "LAZY", "DOG");     
        var listUper = list.stream()
                            .map( String::toUpperCase )
                            //.map( (String s) -> String.toUpperCase(s) )
                            .collect(  Collectors.toList());
        
        System.out.println("\nLista em maiúsculas...");
        listUper.forEach(System.out::println);

      long quatroMais = list.stream()
                          .filter(s -> s.length() >= 4)
                          .count();

        System.out.println("\nNúmero de palavras com 4+ caracteres: "+ quatroMais);
        System.out.println();
    }
}
