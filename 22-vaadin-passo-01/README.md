# Passo 01: primeira tela orientada a eventos

Formulário de sugestão de aquisição para a biblioteca, ainda sem classes de domínio. O foco está em componentes, layouts e nos dois eventos mais usados: **clique** e **mudança de valor**.

## Conceitos

- A tela é uma classe Java anotada com `@Route("")` que herda de `VerticalLayout`.
- Componentes são objetos: `TextField`, `ComboBox<String>`, `Checkbox`, `Button`, `Span`.
- A tela é uma árvore de componentes, montada com `VerticalLayout` e `HorizontalLayout`.
- `addClickListener` e `addValueChangeListener` são o mesmo mecanismo do passo 00.
- O objeto de evento traz `getValue()`, `getOldValue()` e `isFromClient()`.
- O estado da tela muda em resposta a eventos: o botão **Enviar** só é habilitado quando há título.
- `ValueChangeMode.EAGER` gera um evento a cada tecla digitada.

## Executar

```bash
mvn spring-boot:run
```

Acesse http://localhost:8080

## Experimente

1. Digite no título e observe o contador e o botão **Enviar**.
2. Troque o tipo e leia a mensagem que aparece embaixo dos botões. Depois clique em **Limpar**: por que agora aparece "pelo programa"?
3. Troque `EAGER` por `LAZY` e depois por `ON_CHANGE`. O que muda?
4. Faça o botão **Enviar** exigir também o autor.
