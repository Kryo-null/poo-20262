package com.bcopstein.ui;

import java.time.LocalDate;
import java.util.List;

import com.bcopstein.Sistema;
import com.bcopstein.dominio.Biblioteca;
import com.bcopstein.dominio.Emprestimo;
import com.bcopstein.dominio.Item;
import com.bcopstein.dominio.RegraDeNegocioException;
import com.bcopstein.dominio.Usuario;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Balcão de atendimento (passo 03), agora em sua própria rota.
 * Os tratadores de eventos seguem sempre o mesmo roteiro:
 *   1. ler os valores dos componentes;
 *   2. chamar a lógica (Biblioteca);
 *   3. mostrar o resultado, ou a mensagem da exceção, e atualizar a tela.
 */
@Route(value = "balcao", layout = LayoutPrincipal.class)
@PageTitle("Balcão")
public class BalcaoView extends VerticalLayout {
    private final Biblioteca biblioteca = Sistema.biblioteca();

    // Lado esquerdo: emprestar
    private final ComboBox<Usuario> usuario = new ComboBox<>("Usuário");
    private final Span situacaoUsuario = new Span();
    private final Grid<Item> disponiveis = new Grid<>();
    private final Button emprestar = new Button("Emprestar item selecionado");

    // Lado direito: devolver
    private final Grid<Emprestimo> ativos = new Grid<>();
    private final Button devolver = new Button("Registrar devolução");

    public BalcaoView() {
        setSizeFull();
        configurarEmprestimo();
        configurarDevolucao();
        montarTela();
        atualizarTela();
    }

    private void configurarEmprestimo() {
        usuario.setItems(biblioteca.getUsuarios());
        usuario.setItemLabelGenerator(Usuario::nome);
        usuario.setWidthFull();
        usuario.addValueChangeListener(evento -> {
            mostrarSituacao(evento.getValue());
            atualizarBotoes();
        });

        disponiveis.addColumn(Item::getTitulo).setHeader("Título").setAutoWidth(true);
        disponiveis.addColumn(Item::getTipo).setHeader("Tipo").setAutoWidth(true);
        disponiveis.addColumn(item -> item.getPrazoEmDias() + " dias").setHeader("Prazo");
        disponiveis.asSingleSelect().addValueChangeListener(evento -> atualizarBotoes());

        emprestar.addThemeVariants(ButtonVariant.PRIMARY);
        emprestar.addClickListener(evento -> emprestar());
    }

    private void configurarDevolucao() {
        ativos.addColumn(Emprestimo::getNumero).setHeader("Nº").setAutoWidth(true);
        ativos.addColumn(e -> e.getUsuario().nome()).setHeader("Usuário").setAutoWidth(true);
        ativos.addColumn(Emprestimo::getDescricaoItens).setHeader("Itens").setAutoWidth(true);
        ativos.addColumn(e -> Formatos.data(e.getDataLimite())).setHeader("Devolver até").setAutoWidth(true);
        ativos.addColumn(e -> e.estaAtrasado(LocalDate.now()) ? "ATRASADO" : "em dia").setHeader("Situação");
        ativos.asSingleSelect().addValueChangeListener(evento -> atualizarBotoes());

        devolver.addClickListener(evento -> devolver());
    }

    private void montarTela() {
        VerticalLayout ladoEmprestar = new VerticalLayout(
                new H3("Emprestar"), usuario, situacaoUsuario, disponiveis, emprestar);
        VerticalLayout ladoDevolver = new VerticalLayout(
                new H3("Empréstimos ativos"), ativos, devolver);

        HorizontalLayout lados = new HorizontalLayout(ladoEmprestar, ladoDevolver);
        lados.setSizeFull();
        lados.setFlexGrow(1, ladoEmprestar);
        lados.setFlexGrow(2, ladoDevolver);

        add(new H2("Biblioteca - Balcão de atendimento"), lados);
    }

    // ---------- Tratadores dos cliques ----------

    private void emprestar() {
        Usuario quem = usuario.getValue();
        Item item = disponiveis.asSingleSelect().getValue();
        try {
            Emprestimo e = biblioteca.registrarEmprestimo(quem, List.of(item), LocalDate.now());
            sucesso("Empréstimo nº " + e.getNumero() + " registrado. Devolver até "
                    + Formatos.data(e.getDataLimite()));
            atualizarTela();
        } catch (RegraDeNegocioException ex) {
            erro(ex.getMessage()); // a mensagem vem da lógica, a tela só exibe
        }
    }

    private void devolver() {
        Emprestimo selecionado = ativos.asSingleSelect().getValue();
        try {
            double multa = biblioteca.registrarDevolucao(selecionado, LocalDate.now());
            if (multa > 0) {
                erro("Devolução com atraso. Multa: " + Formatos.dinheiro(multa));
            } else {
                sucesso("Devolução registrada, sem multa.");
            }
            atualizarTela();
        } catch (RegraDeNegocioException ex) {
            erro(ex.getMessage());
        }
    }

    // ---------- Atualização da tela ----------

    /** Depois de qualquer mudança na Biblioteca, a tela busca os dados de novo. */
    private void atualizarTela() {
        disponiveis.setItems(biblioteca.getItensDisponiveis());
        ativos.setItems(biblioteca.getEmprestimosAtivos());
        mostrarSituacao(usuario.getValue());
        atualizarBotoes();
    }

    private void mostrarSituacao(Usuario u) {
        if (u == null) {
            situacaoUsuario.setText("Selecione o usuário");
        } else {
            situacaoUsuario.setText(u.nome() + " está com " + biblioteca.quantidadeDeItensCom(u)
                    + " de " + Biblioteca.LIMITE_DE_ITENS + " itens");
        }
    }

    /** Botões só ficam habilitados quando há o que fazer. As regras continuam na Biblioteca. */
    private void atualizarBotoes() {
        emprestar.setEnabled(usuario.getValue() != null && disponiveis.asSingleSelect().getValue() != null);
        devolver.setEnabled(ativos.asSingleSelect().getValue() != null);
    }

    private void sucesso(String mensagem) {
        Notification.show(mensagem, 4000, Notification.Position.BOTTOM_START)
                .addThemeVariants(NotificationVariant.SUCCESS);
    }

    private void erro(String mensagem) {
        Notification.show(mensagem, 5000, Notification.Position.MIDDLE)
                .addThemeVariants(NotificationVariant.ERROR);
    }
}
