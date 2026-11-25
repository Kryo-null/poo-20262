package com.bcopstein.ui;

import java.time.LocalDate;
import java.util.Locale;
import java.util.Map;

import com.bcopstein.Sistema;
import com.bcopstein.dominio.Biblioteca;
import com.bcopstein.dominio.Emprestimo;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Consultas gerenciais. Os cálculos são feitos com streams na Biblioteca;
 * a tela só escolhe o período e mostra os resultados.
 */
@Route(value = "relatorios", layout = LayoutPrincipal.class)
@PageTitle("Relatórios")
public class RelatoriosView extends VerticalLayout {
    private final Biblioteca biblioteca = Sistema.biblioteca();

    private final DatePicker inicio = new DatePicker("De");
    private final DatePicker fim = new DatePicker("Até");
    private final Grid<Emprestimo> noPeriodo = new Grid<>();
    private final Grid<Map.Entry<String, Long>> porTipo = new Grid<>();
    private final Grid<Emprestimo> atrasados = new Grid<>();

    public RelatoriosView() {
        setSizeFull();
        configurarPeriodo();
        configurarGrids();

        HorizontalLayout tabelas = new HorizontalLayout(
                new VerticalLayout(new H3("Empréstimos no período"), noPeriodo),
                new VerticalLayout(new H3("Itens por tipo"), porTipo));
        tabelas.setWidthFull();

        add(new H2("Relatórios"), new HorizontalLayout(inicio, fim), tabelas,
                new H3("Atrasados hoje"), atrasados);
        atualizar();
    }

    private void configurarPeriodo() {
        inicio.setLocale(Locale.of("pt", "BR")); // datas no formato dd/mm/aaaa
        fim.setLocale(Locale.of("pt", "BR"));
        LocalDate hoje = LocalDate.now();
        inicio.setValue(hoje.minusDays(30));
        fim.setValue(hoje);
        fim.setMin(inicio.getValue());

        // Um campo restringe o outro: o fim nunca pode ser antes do início
        inicio.addValueChangeListener(evento -> {
            fim.setMin(evento.getValue());
            atualizar();
        });
        fim.addValueChangeListener(evento -> atualizar());
    }

    private void configurarGrids() {
        noPeriodo.addColumn(Emprestimo::getNumero).setHeader("Nº").setAutoWidth(true);
        noPeriodo.addColumn(e -> Formatos.data(e.getDataEmprestimo())).setHeader("Data").setAutoWidth(true);
        noPeriodo.addColumn(e -> e.getUsuario().nome()).setHeader("Usuário").setAutoWidth(true);
        noPeriodo.addColumn(Emprestimo::getDescricaoItens).setHeader("Itens");
        noPeriodo.setHeight("280px");

        porTipo.addColumn(Map.Entry::getKey).setHeader("Tipo");
        porTipo.addColumn(Map.Entry::getValue).setHeader("Quantidade");
        porTipo.setHeight("280px");
        porTipo.setWidth("280px");

        atrasados.addColumn(e -> e.getUsuario().nome()).setHeader("Usuário").setAutoWidth(true);
        atrasados.addColumn(Emprestimo::getDescricaoItens).setHeader("Itens").setAutoWidth(true);
        atrasados.addColumn(e -> Formatos.data(e.getDataLimite())).setHeader("Venceu em");
        atrasados.addColumn(e -> Formatos.dinheiro(e.calcularMulta(LocalDate.now()))).setHeader("Multa até hoje");
    }

    private void atualizar() {
        LocalDate de = inicio.getValue();
        LocalDate ate = fim.getValue();
        if (de == null || ate == null || ate.isBefore(de)) {
            noPeriodo.setItems();
            porTipo.setItems();
        } else {
            noPeriodo.setItems(biblioteca.getEmprestimosNoPeriodo(de, ate));
            porTipo.setItems(biblioteca.getItensEmprestadosPorTipo(de, ate).entrySet());
        }
        atrasados.setItems(biblioteca.getEmprestimosAtrasados(LocalDate.now()));
    }
}
