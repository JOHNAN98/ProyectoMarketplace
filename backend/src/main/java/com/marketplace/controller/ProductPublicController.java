package com.marketplace.controller;

import com.marketplace.model.Product;
import com.marketplace.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductPublicController {

    private final ProductRepository productRepository;

    // GET /api/v1/products : Catálogo público paginado
    @GetMapping
    public ResponseEntity<Page<Product>> getPublicProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productRepository.findByStatus(Product.Status.APROBADO, pageable));
    }

    // GET /api/v1/products/{slug} : Detalle de producto por SLUG
    @GetMapping("/{slug}")
    public ResponseEntity<Product> getProductBySlug(@PathVariable String slug) {
        return productRepository.findBySlug(slug)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/inventory/check/{productId} : Verificar disponibilidad de stock
    @GetMapping("/check-stock/{productId}")
    public ResponseEntity<Integer> checkStock(@PathVariable UUID productId) {
        return productRepository.findById(productId)
                .map(p -> ResponseEntity.ok(p.getStock()))
                .orElse(ResponseEntity.notFound().build());
    }
}