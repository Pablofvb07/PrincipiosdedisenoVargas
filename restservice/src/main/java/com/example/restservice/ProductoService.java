package com.example.restservice;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repo;

    public ProductoService(ProductoRepository repo) {
        this.repo = repo;
    }

    @Cacheable(value = "productos")
    public List<Producto> obtenerProductos() {
        System.out.println("Consultando BD para productos...");
        return repo.findAll();
    }

    @CacheEvict(value = "productos", allEntries = true)
    public Producto guardarProducto(Producto p) {
        return repo.save(p);
    }

    @CacheEvict(value = "productos", allEntries = true)
    public void limpiarCache() { }
}
