package com.bcopstein.ui;

import java.util.List;

import com.bcopstein.Sistema;
import com.bcopstein.dominio.Biblioteca;
import com.bcopstein.dominio.Item;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Consulta ao acervo (a mesma do passo 02, agora dentro do LayoutPrincipal
 * e mostrando também se o item está disponível).
 */
@Route(value = "", layout = LayoutPrincipal.class)
@PageTitle("Acervo")
public class AcervoView extends VerticalLayout {
    private final Biblioteca biblioteca = Sistema.biblioteca();

    private final TextField filtroTitulo = new TextField("Título contém");
    private final ComboBox<String> filtroTipo = new ComboBox<>("Tipo");
    private final Span totalEncontrado = new Span();
    private final Grid<Item> grid = new Grid<>();

    private final H3 detalheTitulo = new H3("Selecione um item");
    private final Span detalheTexto = new Span();
    private final Span detalhePrazo = new Span();

    public AcervoView() {
        setSizeFull();
        configurarFiltros();
        configurarGrid();
        montarTela();
        atualizarGrid();
    }

    private void configurarFiltros() {
        filtroTitulo.setValueChangeMode(ValueChangeMode.LAZY);
        filtroTitulo.setClearButtonVisible(true);
        filtroTitulo.addValueChangeListener(evento -> atualizarGrid());

        filtroTipo.setItems("Livro", "Revista", "DVD");
        filtroTipo.setPlaceholder("Todos");
        filtroTipo.setClearButtonVisible(true);
        filtroTipo.addValueChangeListener(evento -> atualizarGrid());
    }

    private void configurarGrid() {
        grid.addColumn(Item::getCodigo).setHeader("Código").setAutoWidth(true);
        grid.addColumn(Item::getTitulo).setHeader("Título").setAutoWidth(true).setSortable(true);
        grid.addColumn(Item::getTipo).setHeader("Tipo").setAutoWidth(true).setSortable(true);
        grid.addColumn(Item::getAno).setHeader("Ano").setAutoWidth(true).setSortable(true);
        grid.addColumn(item -> biblioteca.estaDisponivel(item) ? "Disponível" : "Emprestado")
                .setHeader("Situação").setAutoWidth(true);
        grid.asSingleSelect().addValueChangeListener(evento -> mostrarDetalhes(evento.getValue()));
    }

    private void montarTela() {
        // Um botão que leva a outra tela: navegação disparada por um clique
        Button novo = new Button("Novo item", VaadinIcon.PLUS.create(),
                evento -> getUI().ifPresent(ui -> ui.navigate(CadastroItemView.class)));

        HorizontalLayout filtros = new HorizontalLayout(filtroTitulo, filtroTipo, totalEncontrado, novo);
        filtros.setAlignItems(Alignment.BASELINE);

        VerticalLayout detalhes = new VerticalLayout(detalheTitulo, detalheTexto, detalhePrazo);
        detalhes.setWidth("320px");

        HorizontalLayout centro = new HorizontalLayout(grid, detalhes);
        centro.setSizeFull();
        centro.setFlexGrow(1, grid);

        add(filtros, centro);
    }

    private void atualizarGrid() {
        String trecho = filtroTitulo.getValue();
        String tipo = filtroTipo.getValue();
        List<Item> encontrados = biblioteca.buscar(trecho, tipo);
        grid.setItems(encontrados);
        totalEncontrado.setText(encontrados.size() + " item(ns)");
    }

    private void mostrarDetalhes(Item item) {
        if (item == null) {
            detalheTitulo.setText("Selecione um item");
            detalheTexto.setText("");
            detalhePrazo.setText("");
            return;
        }
        detalheTitulo.setText(item.getTitulo() + " (" + item.getAno() + ")");
        detalheTexto.setText(item.getTipo() + " - " + item.getDetalhes());
        detalhePrazo.setText("Prazo: " + item.getPrazoEmDias() + " dias - multa de "
                + Formatos.dinheiro(item.getMultaPorDia()) + " por dia de atraso");
    }
}
