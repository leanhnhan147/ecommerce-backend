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
@Table(name = "review_image")
public class ReviewImage extends Auditable<String> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

    @OneToOne
    @JoinColumn(name = "media_resource_id")
    private MediaResource mediaResource;

    public ReviewImage(Review review, MediaResource mediaResource){
        this.review = review;
        this.mediaResource = mediaResource;
    }
}
