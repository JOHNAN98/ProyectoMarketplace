package com.marketplace.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "sellers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    @Column(name = "store_name", nullable = false, length = 100)
    private String storeName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "tax_id", nullable = false, length = 50)
    private String taxId;

    @Builder.Default
    @Column(name = "is_approved")
    private Boolean isApproved = false;

    @Column(name = "created_at", insertable = false, updatable = false)
    private OffsetDateTime createdAt;
}