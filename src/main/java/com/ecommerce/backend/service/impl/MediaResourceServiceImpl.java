package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.constant.Constant;
import com.ecommerce.backend.exception.NotFoundException;
import com.ecommerce.backend.repository.MediaResourceRepository;
import com.ecommerce.backend.service.CloudinaryService;
import com.ecommerce.backend.service.FileService;
import com.ecommerce.backend.service.MediaResourceService;
import com.ecommerce.backend.storage.entity.MediaResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.Map.*;

@Service
public class MediaResourceServiceImpl implements MediaResourceService {

    @Autowired
    MediaResourceRepository mediaResourceRepository;

    @Autowired
    FileService fileService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Value("${file.path}")
    private String path;

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
    public MediaResource createMediaResource(MultipartFile image) {
        String filePath;
        try {
            filePath = fileService.uploadImage(path, image);
        }catch (IOException e){
            e.printStackTrace();
            throw new NotFoundException("Not found file");
        }
        MediaResource mediaResource = new MediaResource();
        mediaResource.setUrl(filePath);
        mediaResource.setKind(Constant.MEDIA_RESOURCE_KIND_IMAGE);
        return mediaResourceRepository.save(mediaResource);
    }
}
