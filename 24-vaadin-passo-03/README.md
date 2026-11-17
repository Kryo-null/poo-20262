# Passo 03: balcão de atendimento (emprestar e devolver)

O domínio ganha `Usuario`, `Emprestimo` e as regras de empréstimo. A tela de balcão empresta um item por vez e registra devoluções com multa.

## Conceitos

- **O roteiro de todo tratador:** ler os componentes, chamar a lógica, e então mostrar o resultado ou a mensagem da exceção e atualizar a tela.
- **Exceções como comunicação:** a `Biblioteca` lança `RegraDeNegocioException` com uma mensagem pronta para o usuário. A tela só faz `catch` e mostra uma `Notification`.
- **Habilitar botões não é validar:** os botões só ficam ativos quando há usuário e item selecionados, mas quem verifica limite, atraso e disponibilidade é a `Biblioteca`.
- **Atualizar a tela depois de mudar os dados:** `atualizarTela()` busca de novo as listas e repassa aos grids.
- **Polimorfismo na multa:** cada item tem prazo e multa diária próprios, e `Emprestimo.calcularMulta` soma item a item.

## Regras da biblioteca

| Tipo | Prazo | Multa por dia |
|---|---|---|
| Livro | 14 dias | R$ 1,00 |
| Revista | 7 dias | R$ 0,50 |
| DVD | 3 dias | R$ 2,00 |

Cada usuário pode ter até 3 itens emprestados e não pode pegar novos itens enquanto tiver um empréstimo em atraso.

## Executar

```bash
mvn spring-boot:run
```

## Experimente

1. Tente emprestar algo para o Bruno, que tem um DVD atrasado. Depois devolva o DVD e tente de novo.
2. A Ana já está com 3 itens. O que acontece ao tentar emprestar mais um?
3. Mude a mensagem de erro do limite. Em que arquivo você precisou mexer?
4. Mostre a multa prevista de cada empréstimo ativo numa nova coluna.
