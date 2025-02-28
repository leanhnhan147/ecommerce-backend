package com.ecommerce.backend.storage.entity;

import com.ecommerce.backend.storage.base.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_image")
public class ProductImage extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne
    @JoinColumn(name = "media_resource_id")
    private MediaResource mediaResource;

    @OneToOne(mappedBy = "productImage")
    private OptionValueImage optionValueImage;

    public ProductImage(Product product, MediaResource mediaResource) {
        this.product = product;
        this.mediaResource = mediaResource;
    }
}
