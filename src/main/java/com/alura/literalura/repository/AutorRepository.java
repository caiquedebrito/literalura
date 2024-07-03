package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("""
        select a from Autor a
        where a.anoNascimento <= :ano and a.anoFalecimento >= :ano
    """)
    List<Autor> buscarAtoresVivos(Integer ano);

    Autor findByNome(String nome);
}
