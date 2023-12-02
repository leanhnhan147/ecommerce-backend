package com.ecommerce.backend.storage.entity;

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
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String receiverName;
    private String receiverPhone;
    private String addressDetail;
    private Integer isDefault;

    @ManyToOne
    @JoinColumn(name = "ward_id")
    private Nation ward;

    @ManyToOne
    @JoinColumn(name = "district_id")
    private Nation district;

    @ManyToOne
    @JoinColumn(name = "province_id")
    private Nation province;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
