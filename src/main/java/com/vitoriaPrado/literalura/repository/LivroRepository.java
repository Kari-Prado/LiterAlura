package com.vitoriaPrado.literalura.repository;

import com.vitoriaPrado.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    long countByIdioma(String idioma);
}
