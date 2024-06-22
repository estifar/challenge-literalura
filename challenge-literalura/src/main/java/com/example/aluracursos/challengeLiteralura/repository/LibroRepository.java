package com.example.aluracursos.challengeLiteralura.repository;

import com.example.aluracursos.challengeLiteralura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libros, Long> {
    boolean existsByTitulo(String titulo);}
