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
@Table(name = "orders")
public class Order extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double shippingPrice;
    private Integer paymentMethod;
    private Integer state;
    private String receiverName;
    private String receiverPhone;
    private String receiverAddress;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
