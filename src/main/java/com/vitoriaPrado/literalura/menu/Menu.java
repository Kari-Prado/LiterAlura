package com.vitoriaPrado.literalura.menu;

import com.vitoriaPrado.literalura.model.Autor;
import com.vitoriaPrado.literalura.model.Livro;
import com.vitoriaPrado.literalura.repository.AutorRepository;
import com.vitoriaPrado.literalura.repository.LivroRepository;
import com.vitoriaPrado.literalura.service.GutendexService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Menu {

    private final GutendexService gutendexService;
    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final Scanner scanner = new Scanner(System.in);

    public Menu(GutendexService gutendexService, LivroRepository livroRepository, AutorRepository autorRepository) {
        this.gutendexService = gutendexService;
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void iniciar() {
        int opcao = -1;

        while (opcao != 0) {
            exibirMenu();
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrada inválida. Digite um número.");
                continue;
            }

            switch (opcao) {
                case 1 -> buscarLivroPorTitulo();
                case 2 -> listarLivros();
                case 3 -> listarAutores();
                case 4 -> listarAutoresVivos();
                case 5 -> contarLivrosPorIdioma();
                case 0 -> System.out.println("👋 Saindo do programa...");
                default -> System.out.println("⚠ Opção inválida, tente novamente.");
            }
        }
    }

    private void exibirMenu() {
        System.out.println("\n==== MENU LITERALURA ====");
        System.out.println("1 - Buscar livro por título");
        System.out.println("2 - Listar todos os livros");
        System.out.println("3 - Listar autores");
        System.out.println("4 - Listar autores vivos em um determinado ano");
        System.out.println("5 - Exibir quantidade de livros por idioma");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void buscarLivroPorTitulo() {
        System.out.print("Digite o título do livro: ");
        String titulo = scanner.nextLine();

        Livro livro = gutendexService.buscarLivroPorTitulo(titulo);
        if (livro != null) {
            livroRepository.save(livro);
            autorRepository.save(livro.getAutor());
            System.out.println("✅ Livro salvo com sucesso: " + livro);
        } else {
            System.out.println("⚠ Nenhum livro encontrado com esse título.");
        }
    }

    private void listarLivros() {
        List<Livro> livros = livroRepository.findAll();
        if (livros.isEmpty()) {
            System.out.println("⚠ Nenhum livro encontrado no banco de dados.");
        } else {
            livros.forEach(System.out::println);
        }
    }

    private void listarAutores() {
        List<Autor> autores = autorRepository.findAll();
        if (autores.isEmpty()) {
            System.out.println("⚠ Nenhum autor encontrado no banco de dados.");
        } else {
            autores.forEach(System.out::println);
        }
    }

    private void listarAutoresVivos() {
        System.out.print("Digite o ano: ");
        try {
            int ano = Integer.parseInt(scanner.nextLine());
            List<Autor> autoresVivos = autorRepository.findByAnoNascimentoBeforeAndAnoFalecimentoAfter(ano, ano);
            if (autoresVivos.isEmpty()) {
                System.out.println("⚠ Nenhum autor vivo encontrado nesse ano.");
            } else {
                autoresVivos.forEach(System.out::println);
            }
        } catch (NumberFormatException e) {
            System.out.println("⚠ Ano inválido.");
        }
    }

    private void contarLivrosPorIdioma() {
        System.out.print("Digite o idioma (ex: en, fr, pt): ");
        String idioma = scanner.nextLine();

        long quantidade = livroRepository.countByIdioma(idioma);
        System.out.println("📚 Quantidade de livros em " + idioma + ": " + quantidade);
    }
            }
