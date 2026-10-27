# Exercício Prático — Sistema de Pagamentos do Marketplace

**Unidade:** 2 (Orientação a Objetos Avançada) — reforça 2.4 (Herança), 2.4.4 (Classe abstrata),
2.4.5 (Interface) e 2.5 (Polimorfismo). Usa `Comparable`/`Comparator` (java.lang / java.util).

**Domínio:** este exercício dá continuidade ao domínio unificado da disciplina
(`Pagamento` / `Pix` / `CartaoCredito` / `Boleto` / `Notificavel`), já usado nos materiais de
Classes Abstratas, Polimorfismo e Interfaces.

## Contexto

Um marketplace processa pagamentos de três formas: **Pix**, **Cartão de Crédito** e **Boleto**.
Cada forma de pagamento calcula sua taxa de processamento de um jeito diferente, mas todas
compartilham dados básicos (id da transação, valor, data) — por isso `Pagamento` é uma
**classe abstrata**.

Além disso:
- Toda forma de pagamento deve poder **notificar o cliente** sobre o status do pagamento
  (interface `Notificavel`).
- Apenas **Pix** e **Cartão de Crédito** podem ser **estornados** — um Boleto, depois de
  compensado, não tem como ser desfeito. Por isso `Estornavel` é uma **interface separada**,
  implementada só por quem precisa dela (é exatamente a diferença entre "é um tipo de" e
  "é capaz de" discutida em aula).
- O sistema precisa **ordenar** listas de pagamentos de mais de uma forma: pela ordem natural
  (valor total) e, sob demanda, por data.

## O que você deve implementar

Os arquivos já vêm com a estrutura de classes/interfaces pronta. Complete todos os trechos
marcados com `// TODO`:

1. **`Pagamento.java`**
   - `valorTotal()`: valor + `calcularTaxa()`.
   - `compareTo(Pagamento outro)`: ordem natural pelo **valor total**, do menor para o maior.

2. **`Pix.java`**
   - `calcularTaxa()`: Pix não tem taxa (0,0).
   - `enviarNotificacao()`: imprime uma mensagem confirmando o Pix.
   - `estornar()`: só pode ser estornado em até **90 dias** após a data do pagamento.

3. **`CartaoCredito.java`**
   - `calcularTaxa()`: 3,5% do valor + R$ 0,50 por parcela além da 1ª.
   - `enviarNotificacao()`: imprime uma mensagem com o número de parcelas.
   - `estornar()`: cartão sempre pode ser estornado (retorna `true`).

4. **`Boleto.java`**
   - `calcularTaxa()`: taxa fixa de R$ 3,49.
   - `enviarNotificacao()`: imprime uma mensagem confirmando a geração do boleto.
   - **Não implementa** `Estornavel` — isso é proposital, não é esquecimento!

5. **`Main.java`**
   - TODO 4: notificar todos os pagamentos da lista de forma **polimórfica**.
   - TODO 5: ordenar a lista pela ordem natural (`compareTo`) e imprimir.
   - TODO 6: criar um `Comparator<Pagamento>` que ordene por **data** e ordenar a lista com ele.
   - TODO 7: percorrer a lista e, para cada pagamento que também for `Estornavel`, chamar
     `estornar()` e imprimir o resultado (use `instanceof` + cast).

## Requisitos que o código final deve satisfazer

- `Pagamento` continua **abstrata** — não deve ser possível instanciá-la diretamente.
- As três subclasses devem poder ser tratadas de forma uniforme como `List<Pagamento>`
  (polimorfismo) em pelo menos dois pontos do `Main` (notificação e ordenação).
- `Estornavel` deve ser implementada **somente** por `Pix` e `CartaoCredito`.
- A lista deve ser ordenada corretamente pelas duas estratégias pedidas (natural e por data).

## Desafio extra (opcional)

Crie uma segunda interface, `Rastreavel`, com um método `String codigoRastreio()`, implementada
apenas por `Boleto` (simulando um código de linha digitável). Adicione um método utilitário em
`Main` que recebe um `Object` e imprime o código de rastreio **somente** se o objeto implementar
`Rastreavel` — um bom pretexto para revisar `instanceof` antes da próxima aula.
