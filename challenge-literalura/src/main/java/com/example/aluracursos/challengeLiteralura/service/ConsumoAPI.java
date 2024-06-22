package com.example.aluracursos.challengeLiteralura.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ConsumoAPI {

    private static final Logger LOGGER = Logger.getLogger(ConsumoAPI.class.getName());

    public String obtenerDatos(String url) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                return response.body();
            } else {
                LOGGER.log(Level.SEVERE, "Error en la respuesta de la API: " + response.statusCode());
                return null;
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Error al consumir la API", e);
            Thread.currentThread().interrupt();
            return null;
        }
    }
}
