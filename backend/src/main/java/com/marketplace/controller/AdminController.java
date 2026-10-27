package com.marketplace.controller;

import com.marketplace.model.Product;
import com.marketplace.model.Seller;
import com.marketplace.repository.ProductRepository;
import com.marketplace.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;

    // PATCH /api/v1/admin/sellers/{sellerId}/verify : Aprobar o rechazar vendedor
    @PatchMapping("/sellers/{sellerId}/verify")
    public ResponseEntity<Seller> verifySeller(
            @PathVariable UUID sellerId,
            @RequestParam boolean approve) {
        
        return sellerRepository.findById(sellerId)
                .map(seller -> {
                    seller.setIsApproved(approve);
                    return ResponseEntity.ok(sellerRepository.save(seller));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // PATCH /api/v1/admin/products/{id}/approval : Moderar y aprobar/rechazar producto
    @PatchMapping("/products/{id}/approval")
    public ResponseEntity<Product> moderateProduct(
            @PathVariable UUID id,
            @RequestParam Product.Status status) {
        
        return productRepository.findById(id)
                .map(product -> {
                    product.setStatus(status);
                    return ResponseEntity.ok(productRepository.save(product));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}