package com.vitoriaPrado.literalura.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitoriaPrado.literalura.model.Autor;
import com.vitoriaPrado.literalura.model.Livro;
import com.vitoriaPrado.literalura.repository.AutorRepository;
import com.vitoriaPrado.literalura.repository.LivroRepository;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GutendexService {

    private static final String BASE_URL = "https://gutendex.com/books/?search=";

    private final LivroRepository livroRepository;
    private final AutorRepository autorRepository;
    private final ObjectMapper mapper = new ObjectMapper();

    public GutendexService(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    // ========== OPÇÃO 1 ==========
    public void buscarLivro(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            System.out.println("Título vazio.");
            return;
        }

        try {
            String url = BASE_URL + titulo.trim().replace(" ", "%20");

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.out.println("Falha ao consultar a API. Código: " + response.statusCode());
                return;
            }

            JsonNode root = mapper.readTree(response.body());
            JsonNode results = root.get("results");

            if (results == null || !results.isArray() || results.size() == 0) {
                System.out.println("Nenhum livro encontrado para o título: " + titulo);
                return;
            }

            JsonNode livroJson = results.get(0);

            String tituloLivro = safeText(livroJson.get("title"));
            String idioma = firstArrayText(livroJson.get("languages"));  // mantém só o 1º idioma
            Integer downloads = livroJson.hasNonNull("download_count") ? livroJson.get("download_count").asInt() : 0;

            // autor (mantém só o 1º autor)
            JsonNode autoresArr = livroJson.get("authors");
            if (autoresArr == null || !autoresArr.isArray() || autoresArr.size() == 0) {
                System.out.println("Livro sem autor na API. Não será salvo.");
                return;
            }

            JsonNode autorNode = autoresArr.get(0);
            String nomeAutor = safeText(autorNode.get("name"));
            Integer nasc = autorNode.hasNonNull("birth_year") ? autorNode.get("birth_year").asInt() : null;
            Integer fale = autorNode.hasNonNull("death_year") ? autorNode.get("death_year").asInt() : null;

            // reusar autor se já existir
            Optional<Autor> autorExistente = autorRepository.findFirstByNomeAndAnoNascimentoAndAnoFalecimento(
                    nomeAutor, nasc, fale
            );
            Autor autor = autorExistente.orElseGet(() -> autorRepository.save(new Autor(nomeAutor, nasc, fale)));

            // evita duplicar livro (título + idioma + autor)
            boolean jaExisteLivro = livroRepository.existsByTituloAndIdiomaIgnoreCaseAndAutor_Nome(
                    tituloLivro, idioma, autor.getNome()
            );
            if (jaExisteLivro) {
                System.out.println("Livro já cadastrado: " + tituloLivro + " (" + idioma + ") - " + autor.getNome());
                return;
            }

            Livro livro = new Livro(tituloLivro, idioma, downloads, autor);
            livroRepository.save(livro);

            System.out.println("Livro salvo com sucesso:");
            System.out.println(livro);

        } catch (Exception e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
        }
    }

    // ========== OPÇÃO 2 ==========
    public List<Livro> listarLivros() {
        return new ArrayList<>(livroRepository.findAll());
    }

    // ========== OPÇÃO 3 ==========
    public List<Autor> listarAutores() {
        return new ArrayList<>(autorRepository.findAll());
    }

    // ========== OPÇÃO 4 ==========
    public List<Autor> listarAutoresVivos(int ano) {
        if (ano <= 0) return List.of();
        return autorRepository.autoresVivosNoAno(ano);
    }

    // ========== OPÇÃO 5 ==========
    public List<Livro> listarLivrosPorIdioma(String idioma) {
        if (idioma == null || idioma.isBlank()) return List.of();
        return livroRepository.findByIdiomaIgnoreCase(idioma.trim().toLowerCase());
    }

    // ========== OPÇÃO 6 ==========
    public long contarLivrosPorIdioma(String idioma) {
        if (idioma == null || idioma.isBlank()) return 0;
        return livroRepository.countByIdiomaIgnoreCase(idioma.trim().toLowerCase());
    }

    private String safeText(JsonNode node) {
        return (node != null && !node.isNull()) ? node.asText() : "";
    }

    private String firstArrayText(JsonNode arrayNode) {
        if (arrayNode != null && arrayNode.isArray() && arrayNode.size() > 0 && !arrayNode.get(0).isNull()) {
            return arrayNode.get(0).asText();
        }
        return "desconhecido";
    }
              }
