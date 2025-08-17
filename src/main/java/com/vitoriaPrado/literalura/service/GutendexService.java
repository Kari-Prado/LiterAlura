package com.vitoriaPrado.literalura.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vitoriaPrado.literalura.model.Autor;
import com.vitoriaPrado.literalura.model.Livro;
import org.springframework.stereotype.Service;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;

@Service
public class GutendexService {

    private static final String BASE_URL = "https://gutendex.com/books/?search=";

    public Livro buscarLivro(String titulo) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + titulo.replace(" ", "%20")))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.body());
            JsonNode results = root.path("results");

            if (results.isArray() && results.size() > 0) {
                JsonNode first = results.get(0);

                Livro livro = new Livro();
                livro.setTitulo(first.path("title").asText());
                livro.setDownloads(first.path("download_count").asInt());

                JsonNode idiomas = first.path("languages");
                if (idiomas.isArray() && idiomas.size() > 0) {
                    livro.setIdioma(idiomas.get(0).asText());
                }

                JsonNode autores = first.path("authors");
                if (autores.isArray() && autores.size() > 0) {
                    JsonNode a = autores.get(0);
                    Autor autor = new Autor();
                    autor.setNome(a.path("name").asText());
                    autor.setAnoNascimento(a.path("birth_year").isInt() ? a.path("birth_year").asInt() : null);
                    autor.setAnoFalecimento(a.path("death_year").isInt() ? a.path("death_year").asInt() : null);
                    livro.setAutor(autor);
                }

                return livro;
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar livro: " + e.getMessage());
        }
        return null;
    }
                                            }
