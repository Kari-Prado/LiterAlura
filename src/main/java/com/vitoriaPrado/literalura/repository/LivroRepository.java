package com.vitoriaPrado.literalura.repository;

import com.vitoriaPrado.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    List<Livro> findByIdiomaIgnoreCase(String idioma);

    long countByIdiomaIgnoreCase(String idioma);

    boolean existsByTituloAndIdiomaIgnoreCaseAndAutor_Nome(String titulo, String idioma, String autorNome);
}
