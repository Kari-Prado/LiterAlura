package com.vitoriaPrado.literalura.menu;

import com.vitoriaPrado.literalura.model.Autor;
import com.vitoriaPrado.literalura.model.Livro;
import com.vitoriaPrado.literalura.service.GutendexService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

@Component
public class Menu implements CommandLineRunner {

    private final GutendexService service;

    public Menu(GutendexService service) {
        this.service = service;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== LITERALURA ===");
            System.out.println("1 - Buscar livro por título");
            System.out.println("2 - Listar todos os livros");
            System.out.println("3 - Listar autores");
            System.out.println("4 - Listar autores vivos em determinado ano");
            System.out.println("5 - Listar livros por idioma (en, es, fr, pt)");
            System.out.println("6 - Estatística: quantidade de livros por idioma");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
                continue;
            }

            switch (opcao) {
                case 1 -> {
                    System.out.print("Digite o título do livro: ");
                    String titulo = scanner.nextLine().trim();
                    service.buscarLivro(titulo); // já imprime resultado/erros internamente
                }
                case 2 -> {
                    List<Livro> livros = service.listarLivros();
                    if (livros.isEmpty()) {
                        System.out.println("Nenhum livro cadastrado.");
                    } else {
                        for (Livro l : livros) {
                            System.out.println(l);
                        }
                    }
                }
                case 3 -> {
                    List<Autor> autores = service.listarAutores();
                    if (autores.isEmpty()) {
                        System.out.println("Nenhum autor cadastrado.");
                    } else {
                        for (Autor a : autores) {
                            System.out.println(a);
                        }
                    }
                }
                case 4 -> {
                    try {
                        System.out.print("Digite o ano (ex: 1920): ");
                        int ano = Integer.parseInt(scanner.nextLine().trim());
                        List<Autor> vivos = service.listarAutoresVivos(ano);
                        if (vivos.isEmpty()) {
                            System.out.println("Nenhum autor encontrado vivo no ano " + ano);
                        } else {
                            for (Autor a : vivos) {
                                System.out.println(a);
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ano inválido.");
                    }
                }
                case 5 -> {
                    System.out.print("Digite o idioma (en, es, fr, pt): ");
                    String idioma = scanner.nextLine().trim().toLowerCase();
                    List<Livro> livros = service.listarLivrosPorIdioma(idioma);
                    if (livros.isEmpty()) {
                        System.out.println("Nenhum livro encontrado para o idioma: " + idioma);
                    } else {
                        for (Livro l : livros) {
                            System.out.println(l);
                        }
                    }
                }
                case 6 -> {
                    System.out.print("Digite o idioma (en, es, fr, pt): ");
                    String idioma = scanner.nextLine().trim().toLowerCase();
                    long total = service.contarLivrosPorIdioma(idioma);
                    System.out.println("Quantidade de livros no idioma '" + idioma + "': " + total);
                }
                case 0 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        }
    }
                      }
                  
