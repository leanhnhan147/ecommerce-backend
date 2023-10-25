package com.ecommerce.backend.service;

import com.ecommerce.backend.storage.entity.MediaResource;
import org.springframework.web.multipart.MultipartFile;

public interface MediaResourceService {

    MediaResource createMediaResource(MultipartFile image);
}
