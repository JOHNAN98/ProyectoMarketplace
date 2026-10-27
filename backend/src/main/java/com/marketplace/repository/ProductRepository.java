package com.marketplace.repository;

import com.marketplace.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Page<Product> findByStatus(Product.Status status, Pageable pageable);
    Optional<Product> findBySlug(String slug);
    Page<Product> findBySellerId(UUID sellerId, Pageable pageable);
}