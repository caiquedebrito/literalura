package com.alura.literalura.repository;

import com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepositoy extends JpaRepository<Livro, Long> {

    List<Livro> findByIdioma(String idioma);

    boolean existsByTitulo(String titulo);
}
