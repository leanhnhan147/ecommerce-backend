package com.ecommerce.backend.storage.entity;

import com.ecommerce.backend.storage.base.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String avatar;
    @Column(columnDefinition = "text")
    private String description;

    private Double minPrice;
    private Double maxPrice;
    private Double minDiscountedPrice;
    private Double maxDiscountedPrice;
    private Integer discount;
    private Integer soldCount;
    private Integer ratingCount;
    private Integer rating1;
    private Integer rating2;
    private Integer rating3;
    private Integer rating4;
    private Integer rating5;
    private Double averageRating;
    private Integer stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<ProductVariation> productVariations;
}
