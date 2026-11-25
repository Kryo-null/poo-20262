# Passo 02: consulta ao acervo (interface × lógica)

Entram as classes de domínio, em Java puro, no pacote `dominio`: `Item` (abstrata), `Livro`, `Revista`, `Dvd` e `Biblioteca`. A tela fica no pacote `ui` e apenas chama a lógica.

## Conceitos

- **Separação em camadas:** `ui` depende de `dominio`, e nunca o contrário. A `Biblioteca` não importa nada do Vaadin.
- **`Grid<Item>`:** cada coluna recebe uma função que extrai um valor do item (`Item::getTitulo`).
- **Polimorfismo na tela:** a coluna "Prazo" chama `getPrazoEmDias()`, e cada tipo responde com o seu valor.
- **Vários eventos, um tratador:** os dois filtros chamam o mesmo `atualizarGrid()`, que pede `biblioteca.buscar(...)`, onde estão os streams.
- **Seleção é um evento:** `grid.asSingleSelect().addValueChangeListener(...)` preenche o painel de detalhes.
- **`Sistema`:** guarda a única `Biblioteca` da aplicação. O Vaadin cria uma tela nova a cada F5, mas os dados não podem se perder.

## Executar

```bash
mvn spring-boot:run
```

## Experimente

1. Filtre por "Livro" e digite "cor" no título.
2. Clique numa linha e depois clique de novo nela. O que chega no tratador quando a linha é desmarcada?
3. Acrescente uma coluna "Multa/dia" ao grid.
4. Faça a busca encontrar também pelo autor dos livros. Em que classe essa mudança deve ser feita?
