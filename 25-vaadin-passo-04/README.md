# Passo 04: várias telas e cadastro com validação

A aplicação passa a ter um menu lateral e três telas: Acervo, Novo item e Balcão. A tela nova cadastra itens do acervo.

## Conceitos

- **`LayoutPrincipal extends AppLayout`:** a moldura comum, com barra superior e menu (`SideNav`).
- **Rotas:** `@Route(value = "novo-item", layout = LayoutPrincipal.class)`. Cada tela tem seu endereço.
- **Navegação por evento:** o botão "Novo item" do acervo chama `ui.navigate(CadastroItemView.class)`.
- **Campos dependentes:** o tipo escolhido decide quais campos aparecem (`setVisible`).
- **Validação em dois níveis:**
  - Durante o preenchimento, o código é conferido a cada mudança (`setInvalid` e `setErrorMessage`).
  - Ao salvar, os construtores e a `Biblioteca` validam de verdade, e a tela mostra a mensagem da exceção.
- **Da tela para o domínio:** `criarItem()` escolhe qual subclasse instanciar a partir do formulário.

## Executar

```bash
mvn spring-boot:run
```

Rotas: `/`, `/novo-item`, `/balcao`

## Experimente

1. Digite o código `L001` e observe o campo.
2. Tente salvar um DVD sem duração e um livro com ano 3000. De onde vêm as mensagens?
3. Cadastre um item e volte ao acervo pelo menu. Ele está lá? Por quê?
4. Depois de salvar, faça a aplicação navegar automaticamente para o acervo.
