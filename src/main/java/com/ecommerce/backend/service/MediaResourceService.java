package com.ecommerce.backend.service;

import com.ecommerce.backend.dto.mediaResource.MediaResourceDto;
import com.ecommerce.backend.storage.entity.MediaResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

public interface MediaResourceService {

    MediaResource createMediaResource(String path, MultipartFile image);

    List<MediaResourceDto> uploadFiles(String path, MultipartFile[] images);

    Resource load(String path, String filename);

    boolean delete(String path, String filename);

    InputStream getResource(String path, String filename);
}
