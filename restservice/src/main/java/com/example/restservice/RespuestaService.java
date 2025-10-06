package com.example.restservice;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RespuestaService {

    private final RespuestaRepository repo;

    public RespuestaService(RespuestaRepository repo) { this.repo = repo; }

    @Cacheable(value = "respuestas", key = "#pregunta")
    public String obtenerRespuesta(String pregunta) {
        System.out.println("Consultando BD para pregunta: " + pregunta);
        Optional<Respuesta> r = repo.findByPregunta(pregunta);
        return r.map(Respuesta::getRespuesta)
                .orElse("No hay respuesta registrada");
    }

    @CachePut(value = "respuestas", key = "#pregunta")
    public String guardarRespuesta(String pregunta, String respuesta) {
        Respuesta r = repo.findByPregunta(pregunta)
                .orElse(new Respuesta(pregunta, respuesta));
        r.setRespuesta(respuesta);
        repo.save(r);
        return respuesta;
    }
}
