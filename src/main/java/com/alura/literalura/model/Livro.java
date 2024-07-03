package com.alura.literalura.model;

import jakarta.persistence.*;

@Table(name = "livros")
@Entity(name = "Livro")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;
    private Integer totalDownload;
    private String idioma;

    @ManyToOne
    private Autor autor;

    public Livro(DadosLivro dados) {
        this.titulo = dados.titulo();
        this.totalDownload = dados.totalDownload();
        this.idioma = dados.idiomas().get(0);
    }

    public Livro() {

    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTotalDownload() {
        return totalDownload;
    }

    public void setTotalDownload(Integer totalDownload) {
        this.totalDownload = totalDownload;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return String.format("""
                 --------- LIVRO ----------
                 Título: %s
                 Autor: %s
                 Idioma: %s
                 Total de Download: %s
                 --------------------------
                """, this.titulo, this.autor.getNome(), this.idioma, this.totalDownload);
    }

    public boolean equals(Object livro) {
        Livro l = (Livro) livro;
        return this.titulo.equals(l.titulo);
    }
}
