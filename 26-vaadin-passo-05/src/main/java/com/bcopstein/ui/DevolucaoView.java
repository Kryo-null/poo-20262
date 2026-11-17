package com.bcopstein.ui;

import java.time.LocalDate;

import com.bcopstein.Sistema;
import com.bcopstein.dominio.Biblioteca;
import com.bcopstein.dominio.Emprestimo;
import com.bcopstein.dominio.RegraDeNegocioException;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/** Devolução de empréstimos (a metade direita do balcão do passo 03). */
@Route(value = "devolucao", layout = LayoutPrincipal.class)
@PageTitle("Devolução")
public class DevolucaoView extends VerticalLayout {
    private final Biblioteca biblioteca = Sistema.biblioteca();

    private final Grid<Emprestimo> ativos = new Grid<>();
    private final Button devolver = new Button("Registrar devolução");

    public DevolucaoView() {
        setSizeFull();

        ativos.addColumn(Emprestimo::getNumero).setHeader("Nº").setAutoWidth(true);
        ativos.addColumn(e -> e.getUsuario().nome()).setHeader("Usuário").setAutoWidth(true);
        ativos.addColumn(Emprestimo::getDescricaoItens).setHeader("Itens").setAutoWidth(true);
        ativos.addColumn(e -> Formatos.data(e.getDataEmprestimo())).setHeader("Emprestado em");
        ativos.addColumn(e -> Formatos.data(e.getDataLimite())).setHeader("Devolver até");
        ativos.addColumn(e -> e.estaAtrasado(LocalDate.now()) ? "ATRASADO" : "em dia").setHeader("Situação");
        ativos.asSingleSelect().addValueChangeListener(evento -> devolver.setEnabled(evento.getValue() != null));

        devolver.setEnabled(false);
        devolver.addClickListener(evento -> devolver());

        add(new H2("Devolução"), ativos, devolver);
        ativos.setItems(biblioteca.getEmprestimosAtivos());
    }

    private void devolver() {
        Emprestimo selecionado = ativos.asSingleSelect().getValue();
        try {
            double multa = biblioteca.registrarDevolucao(selecionado, LocalDate.now());
            String mensagem = multa > 0
                    ? "Devolução com atraso. Multa: " + Formatos.dinheiro(multa)
                    : "Devolução registrada, sem multa.";
            Notification.show(mensagem, 5000, Notification.Position.MIDDLE)
                    .addThemeVariants(multa > 0 ? NotificationVariant.WARNING : NotificationVariant.SUCCESS);
            ativos.setItems(biblioteca.getEmprestimosAtivos());
        } catch (RegraDeNegocioException ex) {
            Notification.show(ex.getMessage(), 5000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.ERROR);
        }
    }
}
