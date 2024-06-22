package com.example.aluracursos.challengeLiteralura.model;

import java.util.List;
import java.util.stream.Collectors;

public class Idiomas {

    public static String obtenerNombreCompletoIdioma(String codigo) {
        return switch (codigo) {
            case "es" -> "Español";
            case "en" -> "Inglés";
            case "fr" -> "Francés";
            case "pt" -> "Portugués";
            default -> codigo;
        };
    }

    public static String obtenerNombresCompletosIdiomas(List<String> codigos) {
        return codigos.stream()
                .map(Idiomas::obtenerNombreCompletoIdioma)
                .collect(Collectors.joining(", "));
    }
}
