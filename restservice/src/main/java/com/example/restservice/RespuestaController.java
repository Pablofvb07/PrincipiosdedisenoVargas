package com.example.restservice;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {

    private final RespuestaService service;
    private final CacheManager cacheManager;

    public RespuestaController(RespuestaService service, CacheManager cacheManager) {
        this.service = service;
        this.cacheManager = cacheManager;
    }

    @GetMapping
    public Map<String,Object> getRespuesta(@RequestParam String pregunta) {
        String r = service.obtenerRespuesta(pregunta);

        boolean cached = false;
        Cache cache = cacheManager.getCache("respuestas");
        if (cache != null && cache.get(pregunta) != null) {
            cached = true;
        }

        Map<String,Object> map = new HashMap<>();
        map.put("respuesta", r);
        map.put("cached", cached);
        return map;
    }

    @PostMapping
    public Map<String,Object> postRespuesta(@RequestParam String pregunta,
                                            @RequestParam String respuesta) {
        String r = service.guardarRespuesta(pregunta, respuesta);

        Map<String,Object> map = new HashMap<>();
        map.put("respuesta", r);
        map.put("cached", true);
        return map;
    }

    @PostMapping("/clear-cache")
    public void limpiarCacheRespuestas() {
        Cache cache = cacheManager.getCache("respuestas");
        if(cache != null) cache.clear();
    }
}
