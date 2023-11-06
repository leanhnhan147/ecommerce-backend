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
@Table(name = "option_value_image")
public class OptionValueImage extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "option_value_id")
    private OptionValue optionValue;

    @OneToOne
    @JoinColumn(name = "product_image_id")
    private ProductImage productImage;

    public OptionValueImage(OptionValue optionValue, ProductImage productImage) {
        this.optionValue = optionValue;
        this.productImage = productImage;
    }
}
