package com.ecommerce.site.admin.storage.entity;

import com.ecommerce.site.admin.storage.base.Auditable;
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
@Table(name = "product_variation")
public class ProductVariation extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private Double discountedPrice;
    private Integer discount;
    private Integer soldCount;
    private Integer stock;
    private Integer state;  // 1: available  2: sold out    3: lock

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToMany(mappedBy = "productVariation")
    private List<ProductVariationOptionValue> productVariationOptionValues;
}
