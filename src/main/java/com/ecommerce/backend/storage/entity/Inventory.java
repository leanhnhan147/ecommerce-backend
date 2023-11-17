package com.ecommerce.backend.storage.entity;

import com.ecommerce.backend.storage.base.Auditable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double originalPrice;
    private Integer quantity;
    @Column(unique = true)
    private String sku;
    private Date importTime;

    @ManyToOne
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
