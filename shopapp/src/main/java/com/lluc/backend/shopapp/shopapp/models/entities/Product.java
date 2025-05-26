package com.lluc.backend.shopapp.shopapp.models.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "Product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("product-translation") // Nombre único para esta relación
    private List<ProductTranslation> translations;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference("category-product") // Nombre único para esta relación
    private Category category;

    @Getter
    @Setter
    @ManyToMany
    @JoinTable(
        name = "product_sustainable_categories",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "sustainable_category_id")
    )
    private List<SustainableCategory> sustainableCategories; // Lista de categorías sostenibles

    @Getter
    @Setter
    private BigDecimal tax; // Impuestos aplicados

    @Getter
    @Setter
    @ElementCollection
    private List<String> photos; // Lista de URLs de fotos del producto

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "company_id")
    @JsonBackReference // Marca esta relación como la parte "inversa"
    private Company company;

    @Getter
    @Setter
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("product-pricing") // Nombre único para esta relación
    private List<Pricing> pricing;

    @Getter
    @Setter
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaAlta;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("product-review") // Marca esta relación como la parte "propietaria"
    private List<Review> reviews;
    
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("product-report") // Marca esta relación como la parte "propietaria"
    private List<Report> reports;

    @PrePersist
    protected void onCreate() {
        if (this.fechaAlta == null) {
            this.fechaAlta = LocalDateTime.now(); // Establece la fecha actual al crear el producto
        }
    }

    @Getter @Setter private Boolean active = true; // Indica si el producto está activo o no
}