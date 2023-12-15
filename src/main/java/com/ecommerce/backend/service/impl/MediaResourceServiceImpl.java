package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.constant.Constant;
import com.ecommerce.backend.dto.mediaResource.MediaResourceDto;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.mapper.MediaResourceMapper;
import com.ecommerce.backend.repository.MediaResourceRepository;
import com.ecommerce.backend.repository.ProductImageRepository;
import com.ecommerce.backend.service.CloudinaryService;
import com.ecommerce.backend.service.FileService;
import com.ecommerce.backend.service.MediaResourceService;
import com.ecommerce.backend.storage.entity.MediaResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.*;
import java.util.UUID;

@Service
public class MediaResourceServiceImpl implements MediaResourceService {

    @Autowired
    MediaResourceRepository mediaResourceRepository;

    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    FileService fileService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private MediaResourceMapper mediaResourceMapper;
//    @Override
//    public MediaResource createMediaResource(MultipartFile image) {
//        MediaResource mediaResource = new MediaResource();
//        Map<String,String> data = cloudinaryService.uploadFile(image);
//        for(Entry<String,?> entry : data.entrySet()){
//            if(entry.getKey().equals("url")){
//                mediaResource.setUrl(entry.getValue().toString());
//            }
//            if(entry.getValue().equals(Constant.MEDIA_RESOURCE_TYPE_IMAGE)){
//                mediaResource.setKind(Constant.MEDIA_RESOURCE_KIND_IMAGE);
//            }
//        }
//        return mediaResourceRepository.save(mediaResource);
//    }

    @Override
    public MediaResource createMediaResource(String path, MultipartFile image) {
        try {
           return uploadImage(path, image);
        }catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public List<MediaResourceDto> uploadFiles(String path, MultipartFile[] files) {
        List<MediaResource> mediaResources = new ArrayList<>();
        for (int i = 0; i < files.length; i++){
            try {
                mediaResources.add(uploadImage(path, files[i]));
            }catch (IOException e){
                e.printStackTrace();
                throw new RuntimeException("Error: " + e.getMessage());
            }
        }
        return mediaResourceMapper.fromEntityListToMediaResourceDtoAutoCompleteList(mediaResources);
    }

    public MediaResource uploadImage(String path, MultipartFile file) throws IOException {
        // File name
        String name = file.getOriginalFilename();

        // Random name generate file
        String randomID = UUID.randomUUID().toString();
        String generateFileName = randomID.concat(name.substring(name.lastIndexOf(".")));

        // Fullpath
        String filePath = path + File.separator + generateFileName;

        // Create folder if not created
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdir();
        }

        // File copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        MediaResource mediaResource = new MediaResource();
        mediaResource.setUrl("http://localhost:8484/v1/media-resource/" + generateFileName);
        mediaResource.setFileName(generateFileName);
        mediaResource.setKind(Constant.MEDIA_RESOURCE_KIND_IMAGE);
        return mediaResourceRepository.save(mediaResource);
    }

    @Override
    public Resource load(String path, String filename) {
        MediaResource mediaResource = mediaResourceRepository.findByFileName(filename);
        if(mediaResource == null){
            throw new NotFoundException("Not found file name");
        }

        try {
            Path file = Paths.get(path + mediaResource.getFileName());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public boolean delete(String path, String filename) {
        MediaResource mediaResource = mediaResourceRepository.findByFileName(filename);
        if(mediaResource == null){
            throw new NotFoundException("Not found file name");
        }
        productImageRepository.deleteAllByMediaResourceId(mediaResource.getId());
        mediaResourceRepository.deleteById(mediaResource.getId());
        try {
            Path file = Paths.get(path + mediaResource.getFileName());
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public InputStream getResource(String path, String filename) {
        MediaResource mediaResource = mediaResourceRepository.findByFileName(filename);
        if(mediaResource == null){
            throw new NotFoundException("Not found file name");
        }
        try{
            return new FileInputStream(path + mediaResource.getFileName());
        }catch (Exception e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
