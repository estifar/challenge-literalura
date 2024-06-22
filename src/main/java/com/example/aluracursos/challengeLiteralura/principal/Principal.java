package com.example.aluracursos.challengeLiteralura.principal;

import com.example.aluracursos.challengeLiteralura.model.DatosLibro;
import com.example.aluracursos.challengeLiteralura.model.Idiomas;
import com.example.aluracursos.challengeLiteralura.model.Libros;
import com.example.aluracursos.challengeLiteralura.repository.LibroRepository;
import com.example.aluracursos.challengeLiteralura.service.ConsumoAPI;
import com.example.aluracursos.challengeLiteralura.service.ConvertirDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private Scanner teclado = new Scanner(System.in);

    @Autowired
    private ConsumoAPI consumoApi;

    @Autowired
    private ConvertirDatos conversor;

    private final String URL_BASE = "https://gutendex.com/books";
    private List<DatosLibro> datosLibros = new ArrayList<>();
    private LibroRepository repositorio;

    @Autowired
    public Principal(LibroRepository repositorio) {
        this.repositorio = repositorio;
    }

    public void muestraElMenu() {
        int opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros
                    2 - Mostrar los libros guardados
                    3 - Mostrar los autores guardados
                    4 - Mostrar autores vivos en determinado año
                    5 - Mostrar libros por idioma
                    6 - Borrar lista de libros guardada
                    
                    0 - salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    hacerBusquedaDeLibro();
                    break;
                case 2:
                    System.out.println("Lista de libros guardados en la base de datos:\n");
                    mostrarLibrosGuardados();
                    break;
                case 3:
                    System.out.println("Lista de autores guardados en la base de datos:\n");
                    mostrarAutoresGuardados();
                    break;
                case 4:
                    System.out.println("Elija un año en el cual desea buscar:");
                    mostrarAutoresVivos();
                    break;
                case 5:
                    System.out.println(""" 
                            Ingrese un idioma de la lista:
                            en - Inglés
                            es - Español
                            pt - Portugués
                            fr - Francés
                            """);
                    mostrarPorIdioma();
                    break;
                case 6:
                    borrarLista();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion...");
                    break;
                default:
                    System.out.println("Opcion invalida");
            }
        }
    }

    private DatosLibro getDatosLibro() {
        System.out.println("Ingresa el nombre del libro que deseas buscar:");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "/?search=" + nombreLibro.replace(" ", "%20"));
        DatosLibro datos = conversor.obtenerDatos(json, DatosLibro.class);

        if (datos != null && datos.resultados() != null && !datos.resultados().isEmpty()) {
            System.out.println("Resultados encontrados: " + datos.resultados().size());
            mostrarLibros(datos.resultados());
        } else {
            System.out.println("No se encontraron resultados.");
        }
        return datos;
    }

    private void mostrarLibros(List<DatosLibro.Resultado> libros) {
        for (DatosLibro.Resultado libro : libros) {
            System.out.println("\nTítulo: " + libro.titulo());
            for (DatosLibro.Resultado.Autor autor : libro.autores()) {
                System.out.println("Nombre del Autor: " + autor.nombreAutor());
                if (autor.nacimiento() != null) {
                    System.out.println("Nacimiento: " + autor.nacimiento());
                }
                if (autor.muerte() != null) {
                    System.out.println("Muerte: " + autor.muerte());
                }
            }
            System.out.println("Idiomas: " + Idiomas.obtenerNombresCompletosIdiomas(libro.idiomas()));
            System.out.println("Número de descargas: " + libro.totalDescargas());
            System.out.println("--------------------------------------");
        }
    }

    private void hacerBusquedaDeLibro() {
        DatosLibro datos = getDatosLibro();
        if (datos != null && !datos.resultados().isEmpty()) {
            String titulo = datos.resultados().get(0).titulo();
            if (repositorio.existsByTitulo(titulo)) {
                System.out.println("El libro buscado ya se encuentra en la base de datos.");
            } else {
                Libros libros = new Libros(datos);
                repositorio.save(libros);
                System.out.println("Libro guardado exitosamente.");
            }
        }
    }

    private void mostrarLibrosGuardados() {
        List<Libros> libros = repositorio.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros guardados en la base de datos.");
        } else {
            for (Libros libro : libros) {
                System.out.println("\nTítulo: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getNombreAutor());
                System.out.println("Idioma: " + Idiomas.obtenerNombreCompletoIdioma(libro.getIdioma()));
                System.out.println("Número de descargas: " + libro.getTotalDescargas());
                System.out.println("--------------------------------------");
            }
        }
    }

    private void mostrarAutoresGuardados() {
        List<Libros> autores = repositorio.findAll();
        if (autores.isEmpty()) {
            System.out.println("No hay autores guardados en la base de datos.");
        } else {
            for (Libros autor : autores) {
                System.out.println("\nAutor: " + autor.getNombreAutor());
                System.out.println("Fecha de nacimiento: " + autor.getNacimiento());
                System.out.println("Fecha de la muerte: " + autor.getMuerte());
                System.out.println("Libros: " + autor.getTitulo());
                System.out.println("--------------------------------------");
            }
        }
    }

    private void mostrarAutoresVivos() {
        int añoIngresado = -1;
        añoIngresado = teclado.nextInt();
        teclado.nextLine();
        List<Libros> autoresVivos = repositorio.findAll();
        if (autoresVivos.isEmpty()) {
            System.out.println("No hay autores vivos en el año seleccionado.");
        } else {
            System.out.println("Los autoresvivos en el año " + añoIngresado + " son:");
            System.out.println("______________________________________");
            for (Libros autorVivo : autoresVivos) {
                if (añoIngresado >= autorVivo.getNacimiento() && añoIngresado <= autorVivo.getMuerte()){
                    System.out.println("Autor: " + autorVivo.getNombreAutor());
                }
            }
            System.out.println("______________________________________");
        }
    }

    private void mostrarPorIdioma() {
        String idiomaBuscado = teclado.nextLine();
        String conversionIdioma = idiomaBuscado;
        List<Libros> libros = repositorio.findAll();
        if (idiomaBuscado.equals("en")){
            conversionIdioma = "Inglés";
        } else if (idiomaBuscado.equals("es")) {
            conversionIdioma = "Español";
        } else if (idiomaBuscado.equals("pt")) {
            conversionIdioma = "Portugués";
        } else if (idiomaBuscado.equals("fr")) {
            conversionIdioma = "Francés";
        }
        System.out.println("Libros en el idioma " + conversionIdioma + ":\n");
        for (Libros libro : libros) {
            if (libro.getIdioma().equals(idiomaBuscado)) {
                System.out.println("Título: " + libro.getTitulo());
                System.out.println("Autor: " + libro.getNombreAutor());
                System.out.println("--------------------------------------");
            }
        }
    }

    private void borrarLista() {
        datosLibros.clear();
        repositorio.deleteAll();
        System.out.println("Lista de libros guardados ha sido borrada.");
    }
}
