# Passo 05: empréstimo de vários itens e relatórios

O balcão do passo 03 se divide em duas telas. **Empréstimo** monta uma "cesta" com vários itens antes de confirmar, e **Devolução** registra as devoluções. A tela **Relatórios** mostra consultas por período.

É o mesmo tipo de operação de uma venda com vários ingressos no trabalho final.

## Conceitos

- **Estado da tela × estado do domínio:** a `cesta` é uma `List<Item>` da tela. Enquanto o usuário escolhe, nada é registrado na `Biblioteca`.
- **Tudo ou nada:** ao confirmar, a tela entrega a cesta inteira para `registrarEmprestimo`. Se uma regra falhar, nada é registrado e a cesta continua como estava, para o usuário corrigir.
- **Um botão por linha:** em `addComponentColumn`, cada linha tem o seu ouvinte, e a lambda "lembra" qual item é aquela linha.
- **Outros eventos:** duplo clique na linha (`addItemDoubleClickListener`).
- **Resumo ao vivo:** toda mudança termina em `atualizarTela()`, que recalcula listas, resumo e botões.
- **`Dialog` de confirmação:** os botões do diálogo têm seus próprios ouvintes.
- **Relatórios:** dois `DatePicker`, em que um restringe o outro (`setMin`). As consultas usam streams (`groupingBy`, `counting`) na `Biblioteca`.

## Executar

```bash
mvn spring-boot:run
```

Rotas: `/`, `/novo-item`, `/emprestimo`, `/devolucao`, `/relatorios`

## Experimente

1. Escolha a Ana, inclua um item e confirme. Leia a mensagem e veja que a cesta continua lá.
2. Troque para o Diego com a mesma cesta e confirme.
3. Impeça que a cesta passe de 3 itens já na tela. Isso dispensa a validação na `Biblioteca`? Por quê?
4. Acrescente ao relatório o total de multas pagas no período.
