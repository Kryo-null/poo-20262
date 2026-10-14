package br.pucrs.poo;

/**
 * Uma reprodução de música.
 *
 * @param musica   título da música
 * @param artista  nome do artista
 * @param genero   gênero musical
 * @param segundos quanto tempo a música tocou (menos de 30 s = "pulada")
 * @param hora     hora do dia em que tocou (0 a 23)
 */
public record Reproducao(String musica, String artista, String genero,
                         int segundos, int hora) {}
