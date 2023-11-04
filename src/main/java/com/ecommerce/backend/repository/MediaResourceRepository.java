package com.ecommerce.backend.repository;

import com.ecommerce.backend.storage.entity.MediaResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaResourceRepository extends JpaRepository<MediaResource, Long> {
    MediaResource findByFileName(String fileName);
}
