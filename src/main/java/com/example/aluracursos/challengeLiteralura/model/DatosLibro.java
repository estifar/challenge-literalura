package com.example.aluracursos.challengeLiteralura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("results") List<Resultado> resultados
) {
    public String titulo() {
        return resultados.isEmpty() ? null : resultados.get(0).titulo();
    }

    public String nombreAutor() {
        return resultados.isEmpty() ? null : resultados.get(0).autores().isEmpty() ? null : resultados.get(0).autores().get(0).nombreAutor();
    }

    public String idioma() {
        return resultados.isEmpty() ? null : resultados.get(0).idiomas().isEmpty() ? null : resultados.get(0).idiomas().get(0);
    }

    public Integer nacimiento() {
        return resultados.isEmpty() ? null : resultados.get(0).autores().isEmpty() ? null : resultados.get(0).autores().get(0).nacimiento();
    }

    public Integer muerte() {
        return resultados.isEmpty() ? null : resultados.get(0).autores().isEmpty() ? null : resultados.get(0).autores().get(0).muerte();
    }

    public Integer totalDescargas() {
        return resultados.isEmpty() ? null : resultados.get(0).totalDescargas();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Resultado(
            @JsonAlias("title") String titulo,
            @JsonAlias("authors") List<Autor> autores,
            @JsonAlias("languages") List<String> idiomas,
            @JsonAlias("download_count") Integer totalDescargas
    ) {
        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Autor(
                @JsonAlias("name") String nombreAutor,
                @JsonAlias("birth_year") Integer nacimiento,
                @JsonAlias("death_year") Integer muerte
        ) {
        }
    }
}
