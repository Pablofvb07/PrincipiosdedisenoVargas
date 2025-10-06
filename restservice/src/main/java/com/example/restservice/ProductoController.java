package com.example.restservice;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService service;
    private final CacheManager cacheManager;

    public ProductoController(ProductoService service, CacheManager cacheManager) {
        this.service = service;
        this.cacheManager = cacheManager;
    }

    @GetMapping
    public Map<String, Object> obtenerProductos() {
        List<Producto> list = service.obtenerProductos();

        boolean cached = false;
        Cache cache = cacheManager.getCache("productos");
        if (cache != null && cache.get(list) != null) {
            cached = true; // si ya hay cache, marca true
        }

        Map<String,Object> map = new HashMap<>();
        map.put("productos", list);
        map.put("cached", cached);
        return map;
    }

    @PostMapping
    public Producto crearProducto(@RequestBody Producto p) {
        return service.guardarProducto(p);
    }

    @PostMapping("/clear-cache")
    public void limpiarCache() {
        service.limpiarCache();
    }
}

