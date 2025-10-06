package com.example.restservice;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Optional<Respuesta> findByPregunta(String pregunta);
}
