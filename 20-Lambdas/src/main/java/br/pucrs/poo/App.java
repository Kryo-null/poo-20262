package br.pucrs.poo;

import java.time.LocalDate;

public class App {

	public static void main(String[] args) {
		CadastroPessoas cad = new CadastroPessoas();

		cad.inserir(new Pessoa("Fred", LocalDate.of(1990, 6, 20), Genero.MASC, "fred@example.com"));
		cad.inserir(new Pessoa("Jane", LocalDate.of(1990, 7, 15), Genero.FEM, "jane@example.com"));
		cad.inserir(new Pessoa("George", LocalDate.of(1991, 8, 13), Genero.MASC, "george@example.com"));
		cad.inserir(new Pessoa("Bob", LocalDate.of(2000, 9, 12), Genero.MASC, "bob@example.com"));
		cad.inserir(new Pessoa("Lucy", LocalDate.of(2006, 10, 12), Genero.FEM, "lucy@example.com"));
		cad.inserir(new Pessoa("Carl", LocalDate.of(1970, 5, 9), Genero.MASC, "carl@example.com"));
		cad.inserir(new Pessoa("Sr Smith", LocalDate.of(1996, 11, 9), Genero.MASC, "carl@example.com"));
		cad.inserir(new Pessoa("Jane Doe", LocalDate.of(1950, 10, 9), Genero.FEM, "carl@example.com"));
		cad.inserir(new Pessoa("Huginho", LocalDate.of(2010, 1, 9), Genero.MASC, "carl@example.com"));
		cad.inserir(new Pessoa("Zezinho", LocalDate.of(2015, 1, 19), Genero.MASC, "ze@example.com"));
		cad.inserir(new Pessoa("Luizinho", LocalDate.of(1950, 2, 4), Genero.MASC, "luizl@example.com"));
		cad.inserir(new Pessoa("Patinhas", LocalDate.of(2041, 10, 19), Genero.MASC, "tio@example.com"));

		System.out.println("Cadastro de pessoas:");
		cad.listar();

		System.out.println();
		System.out.println("Pessoas com mais de 25 anos:");
		cad.exibePessoasMaisIdosasQue(25);

		/* 
        System.out.println();
		System.out.println("Pessoas com idade entre 15 e 25 anos:");
		cad.exibePessoasFaixaEtaria(15, 25);

		System.out.println();
		System.out.println("Pessoas aptas para serviço militar (usando classe externa):");
		cad.exibePessoas(new PessoaAptaServicoMilitar());

		System.out.println();
		System.out.println("Pessoas aptas para serviço militar (usando classe interna):");
		cad.exibePessoas(new VerificaPessoa() {
			@Override
			public boolean testar(Pessoa p) {
				return p.getGenero() == Genero.MASC && p.getIdade() >= 18 && p.getIdade() <= 25;
			}
		});

		System.out.println();
		System.out.println("Pessoas aptas para serviço militar (usando expressão lambda):");
		cad.exibePessoas((Pessoa p) -> p.getGenero() == Genero.MASC
				&& p.getIdade() >= 18
				&& p.getIdade() <= 25);

		System.out.println();
		System.out.println("Pessoas aptas para serviço militar (usando expressão lambda e interface Predicate):");
		cad.exibePessoasPredicate((Pessoa p) -> p.getGenero() == Genero.MASC
				&& p.getIdade() >= 18
				&& p.getIdade() <= 25);

		System.out.println();
		System.out.println("Pessoas aptas para serviço militar (usando Predicate e Consumer):");
		cad.processaPessoas(p -> p.getGenero() == Genero.MASC
				&& p.getIdade() >= 18
				&& p.getIdade() <= 25, p -> System.out.println(p));

		System.out.println();
		System.out.println("Pessoas aptas para serviço militar (usando Predicate e Consumer como ref. método):");
		// ...ou usando referência de método
		cad.processaPessoas(p -> p.getGenero() == Genero.MASC
															&& p.getIdade() >= 18
															&& p.getIdade() <= 25, 
												System.out::println);

		System.out.println();
		System.out.println("Nome das pessoas aptas para serviço militar (usando Function):");
		// Usando Function para recuperar o email das pessoas aptas para serviço militar
		cad.processaPessoasFunction(
				p -> p.getGenero() == Genero.MASC
						&& p.getIdade() >= 18
						&& p.getIdade() <= 25,
				p -> p.getNome(),
				nome -> System.out.println(nome)
		);
        */
	}
}