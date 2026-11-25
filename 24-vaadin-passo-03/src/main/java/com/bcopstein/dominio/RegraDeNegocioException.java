package com.bcopstein.dominio;

/**
 * Lançada quando uma operação viola uma regra da biblioteca.
 * A mensagem é escrita para ser mostrada ao usuário.
 */
public class RegraDeNegocioException extends RuntimeException {

    public RegraDeNegocioException(String mensagem) {
        super(mensagem);
    }
}
