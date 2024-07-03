package com.alura.literalura.principal;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DadosLivro;
import com.alura.literalura.model.Livro;
import com.alura.literalura.model.Resultado;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepositoy;
import com.alura.literalura.service.ConsumoApi;
import com.alura.literalura.service.ConverteDados;
import com.alura.literalura.service.IConverteDados;

import java.util.*;

public class Principal {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumo = new ConsumoApi();
    private IConverteDados conversor = new ConverteDados();
    private String API_BASE_URL = "https://gutendex.com/books/?search=";

    private LivroRepositoy livroRepositoy;
    private AutorRepository autorRepository;

    public Principal(LivroRepositoy livroRepositoy, AutorRepository autorRepository) {
        this.livroRepositoy = livroRepositoy;
        this.autorRepository = autorRepository;
    }

    public void exibeMenu() {
        while (true) {
            var menu = """
                    *********** BEM VINDO AO LITERALURA *************

                        1 - buscar livro por título
                        2 - listar livros
                        3 - listar autores
                        4 - listar autores vivos em um determinado ano
                        5 - listar livros por idioma
                        
                        9 - sair
                        
                    Escolha uma opção válida:
                    """;

            System.out.println(menu);

            try {
                var opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        buscarLivroPorTitulo();
                        break;
                    case 2:
                        listarLivros();
                        break;
                    case 3:
                        listarAutores();
                        break;
                    case 4:
                        listarAutoresVivoNoAno();
                        break;
                    case 5:
                        listarLivrosPorIdioma();
                        break;
                    case 9:
                        System.out.println("Saindo...");
                        System.exit(0);
                    default:
                        System.out.println("Opção inválida!");
                        break;
                }
            } catch(InputMismatchException e) {
                System.out.println("Opção inválida!");
                break;
            }
        }
    }

    public void buscarLivroPorTitulo() {
        System.out.println("Digite o título do livro: ");
        var titulo = scanner.nextLine();

        System.out.println("Buscando livro por titulo...");

        var json = consumo.obterDados(API_BASE_URL + titulo.trim().replace(" ", "+"));

        if (json.contains("\"count\":0")) {
            System.out.println("Livro não encontrado!");
            return;
        }

        var dados = conversor.obterDados(json, Resultado.class);

        var dadosLivro = dados.livro().get(0);
        var dadosAutor = dadosLivro.autores().get(0);

        if (livroRepositoy.existsByTitulo(dadosLivro.titulo())) {
            return;
        }

        Autor autor = autorRepository.findByNome(dadosAutor.nome());
        if (autor == null) {
            autor = new Autor(dadosAutor);
            autorRepository.save(autor);
        }

        var livro = new Livro(dadosLivro);
        livro.setAutor(autor);
        livroRepositoy.save(livro);
    }

    private void listarLivros() {
        System.out.println("Listando livros...");
        var livros = livroRepositoy.findAll();
        livros.forEach(System.out::println);
    }

    private void listarAutores() {
        System.out.println("Listando autores...");
        var autores = autorRepository.findAll();
        autores.forEach(System.out::println);
    }

    private void listarAutoresVivoNoAno() {
        System.out.println("Digite o ano para pesquisar:");
        var ano = scanner.nextInt();

        var autoresVivos = autorRepository.buscarAtoresVivos(ano);
        autoresVivos.forEach(System.out::println);
    }

    private void listarLivrosPorIdioma() {
        System.out.println("""
                Insira o idioma para realizar a busca:
                
                pt - português
                es - espanhol
                en - inglês
                fr - francês
                """);
        var idioma = scanner.nextLine();

        var livros = livroRepositoy.findByIdioma(idioma);
        livros.forEach(System.out::println);
        System.out.println(livros.size() + " livro(s) encontrado(s)");
    }
}
