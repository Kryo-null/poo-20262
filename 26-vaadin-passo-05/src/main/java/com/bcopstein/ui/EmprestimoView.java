package com.bcopstein.ui;

import java.time.LocalDate;
import java.util.ArrayList;
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
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

/**
 * Passo 05 - Empréstimo de vários itens de uma vez.
 * A "cesta" é estado da TELA: enquanto o usuário escolhe, nada é registrado.
 * Só ao confirmar a tela entrega tudo à Biblioteca, que valida o conjunto
 * inteiro e registra tudo ou nada.
 */
@Route(value = "emprestimo", layout = LayoutPrincipal.class)
@PageTitle("Empréstimo")
public class EmprestimoView extends VerticalLayout {
    private final Biblioteca biblioteca = Sistema.biblioteca();

    // Estado da tela: os itens escolhidos, ainda não emprestados
    private final List<Item> cesta = new ArrayList<>();

    private final ComboBox<Usuario> usuario = new ComboBox<>("Usuário");
    private final TextField filtro = new TextField("Procurar título");
    private final Grid<Item> disponiveis = new Grid<>();
    private final Grid<Item> gridCesta = new Grid<>();
    private final Span resumo = new Span();
    private final Button confirmar = new Button("Confirmar empréstimo", VaadinIcon.CHECK.create());
    private final Button descartar = new Button("Descartar cesta");

    public EmprestimoView() {
        setSizeFull();
        configurarComponentes();
        registrarOuvintes();
        montarTela();
        atualizarTela();
    }

    private void configurarComponentes() {
        usuario.setItems(biblioteca.getUsuarios());
        usuario.setItemLabelGenerator(Usuario::nome);
        filtro.setValueChangeMode(ValueChangeMode.LAZY);
        filtro.setClearButtonVisible(true);

        disponiveis.addColumn(Item::getTitulo).setHeader("Título").setAutoWidth(true);
        disponiveis.addColumn(Item::getTipo).setHeader("Tipo").setAutoWidth(true);
        // Coluna de componentes: cada linha ganha um botão com o SEU próprio ouvinte.
        // A lambda "lembra" de qual item é aquela linha.
        disponiveis.addComponentColumn(item -> {
            Button incluir = new Button(VaadinIcon.PLUS.create(), evento -> incluir(item));
            incluir.addThemeVariants(ButtonVariant.SMALL, ButtonVariant.TERTIARY);
            return incluir;
        }).setHeader("Incluir");

        gridCesta.addColumn(Item::getTitulo).setHeader("Título").setAutoWidth(true);
        gridCesta.addColumn(item -> item.getPrazoEmDias() + " dias").setHeader("Prazo");
        gridCesta.addComponentColumn(item -> {
            Button remover = new Button(VaadinIcon.TRASH.create(), evento -> remover(item));
            remover.addThemeVariants(ButtonVariant.SMALL, ButtonVariant.ERROR, ButtonVariant.TERTIARY);
            return remover;
        }).setHeader("Remover");

        confirmar.addThemeVariants(ButtonVariant.PRIMARY);
    }

    private void registrarOuvintes() {
        usuario.addValueChangeListener(evento -> atualizarTela());
        filtro.addValueChangeListener(evento -> atualizarTela());
        // Outro tipo de evento: duplo clique numa linha também inclui o item
        disponiveis.addItemDoubleClickListener(evento -> incluir(evento.getItem()));
        confirmar.addClickListener(evento -> pedirConfirmacao());
        descartar.addClickListener(evento -> {
            cesta.clear();
            atualizarTela();
        });
    }

    private void montarTela() {
        VerticalLayout ladoAcervo = new VerticalLayout(new H3("Itens disponíveis"), filtro, disponiveis);
        VerticalLayout ladoCesta = new VerticalLayout(new H3("Cesta"), gridCesta, resumo,
                new HorizontalLayout(confirmar, descartar));
        HorizontalLayout lados = new HorizontalLayout(ladoAcervo, ladoCesta);
        lados.setSizeFull();
        lados.setFlexGrow(1, ladoAcervo, ladoCesta);

        add(new H2("Novo empréstimo"), usuario, lados);
    }

    // ---------- Alterações da cesta (só a tela muda) ----------

    private void incluir(Item item) {
        cesta.add(item);
        atualizarTela();
    }

    private void remover(Item item) {
        cesta.remove(item);
        atualizarTela();
    }

    /** Toda mudança de estado termina aqui: a tela inteira é recalculada. */
    private void atualizarTela() {
        // Disponíveis = disponíveis na biblioteca, menos os que já estão na cesta
        List<Item> lista = biblioteca.buscar(filtro.getValue(), null).stream()
                .filter(biblioteca::estaDisponivel)
                .filter(item -> !cesta.contains(item))
                .toList();
        disponiveis.setItems(lista);
        gridCesta.setItems(cesta);
        resumo.setText(textoDoResumo());
        confirmar.setEnabled(usuario.getValue() != null && !cesta.isEmpty());
        descartar.setEnabled(!cesta.isEmpty());
    }

    private String textoDoResumo() {
        String texto = cesta.size() + " item(ns) na cesta";
        Usuario u = usuario.getValue();
        if (u != null) {
            int jaTem = biblioteca.quantidadeDeItensCom(u);
            int total = jaTem + cesta.size();
            texto += " - " + u.nome() + " ficará com " + total + " de " + Biblioteca.LIMITE_DE_ITENS;
            if (total > Biblioteca.LIMITE_DE_ITENS) {
                texto += " (acima do limite!)";
            }
        }
        return texto;
    }

    // ---------- Confirmação e registro ----------

    private void pedirConfirmacao() {
        Dialog dialogo = new Dialog();
        dialogo.setHeaderTitle("Confirmar empréstimo");
        dialogo.add(new Paragraph(usuario.getValue().nome() + " levará: "
                + String.join(", ", cesta.stream().map(Item::getTitulo).toList())));

        Button sim = new Button("Confirmar", evento -> {
            dialogo.close();
            registrar();
        });
        sim.addThemeVariants(ButtonVariant.PRIMARY);
        Button nao = new Button("Voltar", evento -> dialogo.close());

        dialogo.getFooter().add(nao, sim);
        dialogo.open();
    }

    private void registrar() {
        try {
            Emprestimo e = biblioteca.registrarEmprestimo(usuario.getValue(), cesta, LocalDate.now());
            Notification.show("Empréstimo nº " + e.getNumero() + " registrado. Primeira devolução em "
                    + Formatos.data(e.getDataLimite()), 5000, Notification.Position.BOTTOM_START)
                    .addThemeVariants(NotificationVariant.SUCCESS);
            cesta.clear();
        } catch (RegraDeNegocioException ex) {
            // Nada foi registrado: a cesta continua como estava, para o usuário corrigir
            Notification.show(ex.getMessage(), 5000, Notification.Position.MIDDLE)
                    .addThemeVariants(NotificationVariant.ERROR);
        }
        atualizarTela();
    }
}
