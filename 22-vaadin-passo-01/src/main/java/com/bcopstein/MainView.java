package com.bcopstein;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

/**
 * Passo 01 - Primeira tela orientada a eventos.
 * Formulário de sugestão de aquisição para a biblioteca.
 * Os componentes são atributos porque os tratadores de eventos precisam deles.
 */
@Route("")
public class MainView extends VerticalLayout {
    private static final int TAMANHO_MAXIMO = 60;

    // Componentes do formulário
    private final TextField titulo = new TextField("Título");
    private final TextField autor = new TextField("Autor");
    private final ComboBox<String> tipo = new ComboBox<>("Tipo");
    private final Checkbox urgente = new Checkbox("Aquisição urgente");
    private final Span contador = new Span("0/" + TAMANHO_MAXIMO);
    private final Button enviar = new Button("Enviar sugestão");
    private final Button limpar = new Button("Limpar");

    // Painel onde as sugestões enviadas vão aparecendo
    private final VerticalLayout sugestoes = new VerticalLayout();
    private final Span ultimoEvento = new Span("Nenhum evento ainda.");

    public MainView() {
        configurarComponentes();
        registrarOuvintes();
        montarTela();
    }

    private void configurarComponentes() {
        titulo.setMaxLength(TAMANHO_MAXIMO);
        titulo.setWidthFull();
        // EAGER: um evento de mudança de valor a cada tecla digitada
        titulo.setValueChangeMode(ValueChangeMode.EAGER);
        autor.setWidthFull();
        tipo.setItems("Livro", "Revista", "DVD");
        tipo.setValue("Livro");
        enviar.addThemeVariants(ButtonVariant.PRIMARY);
        enviar.setEnabled(false); // só habilita quando houver título
        sugestoes.setPadding(false);
        sugestoes.setSpacing(false);
    }

    private void registrarOuvintes() {
        // Evento de mudança de valor: o objeto de evento traz o valor novo
        titulo.addValueChangeListener(evento -> {
            String texto = evento.getValue();
            contador.setText(texto.length() + "/" + TAMANHO_MAXIMO);
            enviar.setEnabled(!texto.isBlank());
        });

        // O objeto de evento também traz o valor antigo e quem causou a mudança
        tipo.addValueChangeListener(evento -> ultimoEvento.setText(
                "Tipo: de " + evento.getOldValue() + " para " + evento.getValue()
                + (evento.isFromClient() ? " (pelo usuário)" : " (pelo programa)")));

        urgente.addValueChangeListener(evento -> ultimoEvento.setText(
                "Urgente: " + (evento.getValue() ? "marcado" : "desmarcado")));

        // Eventos de clique: cada lambda chama um método da própria tela
        enviar.addClickListener(evento -> enviarSugestao());
        limpar.addClickListener(evento -> limparFormulario());
    }

    private void montarTela() {
        HorizontalLayout botoes = new HorizontalLayout(enviar, limpar);
        VerticalLayout formulario = new VerticalLayout(
                new H3("Nova sugestão"), titulo, contador, autor, tipo, urgente, botoes, ultimoEvento);
        formulario.setWidth("400px");

        VerticalLayout painel = new VerticalLayout(new H3("Sugestões enviadas"), sugestoes);

        add(new H2("Biblioteca - Sugestão de aquisição"), new HorizontalLayout(formulario, painel));
    }

    private void enviarSugestao() {
        String descricao = tipo.getValue() + ": " + titulo.getValue();
        if (!autor.isEmpty()) {
            descricao += " (" + autor.getValue() + ")";
        }
        if (urgente.getValue()) {
            descricao += " - URGENTE";
        }
        sugestoes.add(new Span(descricao));
        Notification.show("Sugestão enviada!");
        limparFormulario();
    }

    private void limparFormulario() {
        titulo.clear(); // dispara o ouvinte do título: contador e botão se atualizam
        autor.clear();
        urgente.clear();
        tipo.setValue("Livro");
        titulo.focus();
    }
}
