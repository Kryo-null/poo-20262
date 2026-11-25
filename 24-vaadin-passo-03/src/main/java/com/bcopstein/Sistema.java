package com.bcopstein;

import com.bcopstein.dominio.Biblioteca;

/**
 * Guarda a única Biblioteca da aplicação.
 *
 * Por que não criar a Biblioteca dentro da tela? Porque o Vaadin cria
 * um NOVO objeto de tela a cada vez que a página é aberta ou recarregada.
 * Se a Biblioteca morasse na tela, os dados se perderiam a cada F5.
 *
 * Atenção: como é um atributo static, todos os navegadores conectados
 * compartilham a mesma Biblioteca. Para o nosso uso local, é o desejado.
 */
public final class Sistema {
    private static final Biblioteca BIBLIOTECA = DadosIniciais.criarBiblioteca();

    private Sistema() {
    }

    public static Biblioteca biblioteca() {
        return BIBLIOTECA;
    }
}
