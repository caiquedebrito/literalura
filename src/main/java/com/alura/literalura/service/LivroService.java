package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepositoy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LivroService {
    @Autowired
    private LivroRepositoy livroRepositoy;

    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    public void salvarLivroComAutor(Livro livro, Autor autor) {
        autorRepository.save(autor);
        livroRepositoy.save(livro);
    }
}
