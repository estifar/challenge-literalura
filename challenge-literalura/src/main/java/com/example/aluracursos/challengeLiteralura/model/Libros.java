package com.example.aluracursos.challengeLiteralura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(unique = true)
    private String titulo;
    private String nombreAutor;
    private String idioma;
    private Integer nacimiento;
    private Integer muerte;
    private Integer totalDescargas;

    public Libros() {
    }

    public Libros(DatosLibro datosLibro) {
        this.titulo = datosLibro.titulo();
        this.nombreAutor = datosLibro.nombreAutor();
        this.idioma = datosLibro.idioma();
        this.nacimiento = datosLibro.nacimiento();
        this.muerte = datosLibro.muerte();
        this.totalDescargas = datosLibro.totalDescargas();
    }

    @Override
    public String toString() {
        return "Id=" + Id +
                ", titulo='" + titulo + '\'' +
                ", nombreAutor='" + nombreAutor + '\'' +
                ", idioma='" + idioma + '\'' +
                ", nacimiento=" + nacimiento +
                ", muerte=" + muerte +
                ", totalDescargas=" + totalDescargas;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getNombreAutor() {
        return nombreAutor;
    }

    public void setNombreAutor(String nombreAutor) {
        this.nombreAutor = nombreAutor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getMuerte() {
        return muerte;
    }

    public void setMuerte(Integer muerte) {
        this.muerte = muerte;
    }

    public Integer getTotalDescargas() {
        return totalDescargas;
    }

    public void setTotalDescargas(Integer totalDescargas) {
        this.totalDescargas = totalDescargas;
    }
}
