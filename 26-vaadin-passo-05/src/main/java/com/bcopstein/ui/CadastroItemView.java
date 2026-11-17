package com.bcopstein.ui;

import com.bcopstein.Sistema;
import com.bcopstein.dominio.Biblioteca;
import com.bcopstein.dominio.Dvd;
import com.bcopstein.dominio.Item;
import com.bcopstein.dominio.Livro;
import com.bcopstein.dominio.RegraDeNegocioException;
import com.bcopstein.dominio.Revista;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Passo 04 - Cadastro de um novo item do acervo.
 * Dois níveis de validação:
 *  - durante o preenchimento: eventos de campo dão retorno imediato;
 *  - ao salvar: o domínio valida de verdade (construtores e Biblioteca)
 *    e a tela mostra a mensagem da exceção.
 */
@Route(value = "novo-item", layout = LayoutPrincipal.class)
@PageTitle("Novo item")
public class CadastroItemView extends VerticalLayout {
    private final Biblioteca biblioteca = Sistema.biblioteca();

    // Campos comuns a todos os itens
    private final ComboBox<String> tipo = new ComboBox<>("Tipo");
    private final TextField codigo = new TextField("Código");
    private final TextField titulo = new TextField("Título");
    private final IntegerField ano = new IntegerField("Ano");
    // Campos específicos: só um deles aparece, conforme o tipo
    private final TextField autor = new TextField("Autor");
    private final IntegerField edicao = new IntegerField("Edição");
    private final IntegerField duracao = new IntegerField("Duração (min)");

    private final Button salvar = new Button("Salvar");
    private final Button limpar = new Button("Limpar");

    public CadastroItemView() {
        configurarCampos();
        registrarOuvintes();
        montarTela();
        tipo.setValue("Livro"); // dispara o ouvinte do tipo e ajusta os campos visíveis
    }

    private void configurarCampos() {
        tipo.setItems("Livro", "Revista", "DVD");
        tipo.setRequiredIndicatorVisible(true);
        codigo.setRequiredIndicatorVisible(true);
        titulo.setRequiredIndicatorVisible(true);
        ano.setRequiredIndicatorVisible(true);
        ano.setStepButtonsVisible(true);
        salvar.addThemeVariants(ButtonVariant.PRIMARY);
    }

    private void registrarOuvintes() {
        // O tipo escolhido decide quais campos aparecem
        tipo.addValueChangeListener(evento -> mostrarCamposDoTipo(evento.getValue()));

        // Retorno imediato: assim que o código muda, a tela pergunta à Biblioteca
        codigo.addValueChangeListener(evento -> {
            boolean repetido = biblioteca.existeCodigo(evento.getValue());
            codigo.setInvalid(repetido);
            codigo.setErrorMessage(repetido ? "Código já cadastrado" : null);
        });

        salvar.addClickListener(evento -> salvar());
        limpar.addClickListener(evento -> limpar());
    }

    private void montarTela() {
        FormLayout formulario = new FormLayout(tipo, codigo, titulo, ano, autor, edicao, duracao);
        formulario.setMaxWidth("600px");
        add(new H2("Novo item do acervo"), formulario, new HorizontalLayout(salvar, limpar));
    }

    private void mostrarCamposDoTipo(String tipoEscolhido) {
        autor.setVisible("Livro".equals(tipoEscolhido));
        edicao.setVisible("Revista".equals(tipoEscolhido));
        duracao.setVisible("DVD".equals(tipoEscolhido));
    }

    private void salvar() {
        try {
            Item novo = criarItem();          // pode lançar IllegalArgumentException
            biblioteca.cadastrarItem(novo);   // pode lançar RegraDeNegocioException
            Notification.show("'" + novo.getTitulo() + "' cadastrado com o código " + novo.getCodigo())
                    .addThemeVariants(NotificationVariant.SUCCESS);
            limpar();
        } catch (IllegalArgumentException | RegraDeNegocioException ex) {
            Notification.show(ex.getMessage(), 5000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.ERROR);
        }
    }

    /**
     * Traduz o formulário num objeto do domínio.
     * A tela escolhe QUAL classe instanciar; quem valida os dados é o construtor.
     */
    private Item criarItem() {
        if (tipo.isEmpty()) {
            throw new IllegalArgumentException("Escolha o tipo do item");
        }
        if (ano.getValue() == null) {
            throw new IllegalArgumentException("Informe o ano");
        }
        return switch (tipo.getValue()) {
            case "Livro" -> new Livro(codigo.getValue(), titulo.getValue(), ano.getValue(), autor.getValue());
            case "Revista" -> new Revista(codigo.getValue(), titulo.getValue(), ano.getValue(),
                    valorOuZero(edicao));
            default -> new Dvd(codigo.getValue(), titulo.getValue(), ano.getValue(), valorOuZero(duracao));
        };
    }

    private int valorOuZero(IntegerField campo) {
        return campo.getValue() == null ? 0 : campo.getValue();
    }

    private void limpar() {
        codigo.clear();
        titulo.clear();
        ano.clear();
        autor.clear();
        edicao.clear();
        duracao.clear();
        codigo.focus();
    }
}
