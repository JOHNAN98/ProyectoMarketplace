package com.marketplace.controller;

import com.marketplace.model.Product;
import com.marketplace.model.Seller;
import com.marketplace.repository.ProductRepository;
import com.marketplace.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/seller/products")
@RequiredArgsConstructor
public class SellerProductController {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    // GET /api/v1/seller/products : Consultar el catálogo del vendedor autenticado
    @GetMapping
    public ResponseEntity<Page<Product>> getSellerProducts(
            @RequestParam UUID sellerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(productRepository.findBySellerId(sellerId, PageRequest.of(page, size)));
    }

    // POST /api/v1/seller/products : Crear producto (estado PENDIENTE por defecto)
    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestParam UUID sellerId,
            @RequestBody Product productRequest) {
        
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));

        productRequest.setSeller(seller);
        productRequest.setStatus(Product.Status.PENDIENTE); // Requiere moderación
        Product savedProduct = productRepository.save(productRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }
}