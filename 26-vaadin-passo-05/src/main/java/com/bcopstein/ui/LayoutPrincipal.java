package com.bcopstein.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;

/**
 * A "moldura" comum a todas as telas: barra superior e menu lateral.
 * Cada tela diz, na anotação @Route, que quer ser exibida dentro deste layout.
 * Clicar num item do menu é um evento de navegação: o Vaadin cria a tela
 * de destino e a coloca na área de conteúdo.
 */
public class LayoutPrincipal extends AppLayout {

    public LayoutPrincipal() {
        H1 titulo = new H1("Biblioteca");
        titulo.getStyle().set("font-size", "var(--lumo-font-size-l)").set("margin", "0");

        SideNav menu = new SideNav();
        menu.addItem(new SideNavItem("Acervo", AcervoView.class, VaadinIcon.BOOK.create()));
        menu.addItem(new SideNavItem("Novo item", CadastroItemView.class, VaadinIcon.PLUS.create()));
        menu.addItem(new SideNavItem("Empréstimo", EmprestimoView.class, VaadinIcon.CART.create()));
        menu.addItem(new SideNavItem("Devolução", DevolucaoView.class, VaadinIcon.EXCHANGE.create()));
        menu.addItem(new SideNavItem("Relatórios", RelatoriosView.class, VaadinIcon.CHART.create()));

        addToNavbar(new DrawerToggle(), titulo);
        addToDrawer(menu);
    }
}
