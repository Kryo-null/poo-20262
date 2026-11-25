package com.bcopstein.ui;

import java.util.List;
import java.util.Locale;

import com.bcopstein.Sistema;
import com.bcopstein.dominio.Biblioteca;
import com.bcopstein.dominio.Item;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Passo 02 - Consulta ao acervo.
 * A tela não sabe filtrar nem ordenar: ela pede isso à Biblioteca
 * sempre que um evento de filtro acontece, e mostra o resultado no Grid.
 */
@Route("")
@PageTitle("Acervo")
public class AcervoView extends VerticalLayout {
    private final Biblioteca biblioteca = Sistema.biblioteca();

    private final TextField filtroTitulo = new TextField("Título contém");
    private final ComboBox<String> filtroTipo = new ComboBox<>("Tipo");
    private final Span totalEncontrado = new Span();
    private final Grid<Item> grid = new Grid<>();

    // Painel de detalhes do item selecionado
    private final H3 detalheTitulo = new H3("Selecione um item");
    private final Span detalheTexto = new Span();
    private final Span detalhePrazo = new Span();

    public AcervoView() {
        setSizeFull();
        configurarFiltros();
        configurarGrid();
        montarTela();
        atualizarGrid(); // carga inicial
    }

    private void configurarFiltros() {
        // LAZY: espera o usuário fazer uma pausa na digitação antes de avisar
        filtroTitulo.setValueChangeMode(ValueChangeMode.LAZY);
        filtroTitulo.setClearButtonVisible(true);
        filtroTitulo.addValueChangeListener(evento -> atualizarGrid());

        filtroTipo.setItems("Livro", "Revista", "DVD");
        filtroTipo.setPlaceholder("Todos");
        filtroTipo.setClearButtonVisible(true);
        filtroTipo.addValueChangeListener(evento -> atualizarGrid());
    }

    private void configurarGrid() {
        // Cada coluna recebe uma função que extrai o valor de um Item
        grid.addColumn(Item::getCodigo).setHeader("Código").setAutoWidth(true);
        grid.addColumn(Item::getTitulo).setHeader("Título").setAutoWidth(true).setSortable(true);
        grid.addColumn(Item::getTipo).setHeader("Tipo").setAutoWidth(true).setSortable(true);
        grid.addColumn(Item::getAno).setHeader("Ano").setAutoWidth(true).setSortable(true);
        // Polimorfismo: cada tipo de item responde com o seu prazo
        grid.addColumn(item -> item.getPrazoEmDias() + " dias").setHeader("Prazo").setAutoWidth(true);

        // Selecionar uma linha também é um evento
        grid.asSingleSelect().addValueChangeListener(evento -> mostrarDetalhes(evento.getValue()));
    }

    private void montarTela() {
        HorizontalLayout filtros = new HorizontalLayout(filtroTitulo, filtroTipo, totalEncontrado);
        filtros.setAlignItems(Alignment.BASELINE);

        VerticalLayout detalhes = new VerticalLayout(detalheTitulo, detalheTexto, detalhePrazo);
        detalhes.setWidth("320px");

        HorizontalLayout centro = new HorizontalLayout(grid, detalhes);
        centro.setSizeFull();
        centro.setFlexGrow(1, grid); // o grid ocupa todo o espaço que sobrar

        add(new H2("Biblioteca - Acervo"), filtros, centro);
    }

    private void atualizarGrid() {
        String trecho = filtroTitulo.getValue();
        String tipo = filtroTipo.getValue();
        List<Item> encontrados = biblioteca.buscar(trecho, tipo);
        grid.setItems(encontrados);
        totalEncontrado.setText(encontrados.size() + " item(ns)");
    }

    private void mostrarDetalhes(Item item) {
        if (item == null) { // a linha foi desmarcada
            detalheTitulo.setText("Selecione um item");
            detalheTexto.setText("");
            detalhePrazo.setText("");
            return;
        }
        detalheTitulo.setText(item.getTitulo() + " (" + item.getAno() + ")");
        detalheTexto.setText(item.getTipo() + " - " + item.getDetalhes());
        detalhePrazo.setText(String.format(Locale.of("pt", "BR"),
                "Prazo: %d dias - multa de R$ %.2f por dia de atraso",
                item.getPrazoEmDias(), item.getMultaPorDia()));
    }
}
