package com.bcopstein.dominio;

/** Um usuário da biblioteca, identificado pela matrícula. */
public record Usuario(String matricula, String nome) {

    public Usuario {
        if (matricula == null || matricula.isBlank()) {
            throw new IllegalArgumentException("A matrícula é obrigatória");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório");
        }
    }

    @Override
    public String toString() {
        return nome + " (" + matricula + ")";
    }
}
