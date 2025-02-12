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
@Table(name = "pricing_strategy")
public class PricingStrategy extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;
    private Double discountedPrice;
    @Column(unique = true)
    private String sku;
    private Date startDate;
    private Date endDate;
    private Integer state;

    @ManyToOne
    @JoinColumn(name = "product_variation_id")
    private ProductVariation productVariation;

    @ManyToOne
    @JoinColumn(name = "inventory_entry_id")
    private InventoryEntry inventoryEntry;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
