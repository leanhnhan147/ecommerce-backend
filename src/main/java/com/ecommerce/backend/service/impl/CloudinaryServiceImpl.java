package com.ecommerce.backend.service.impl;

import com.cloudinary.Cloudinary;
import com.ecommerce.backend.service.CloudinaryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public Map uploadFile(MultipartFile file){
        try {
            Map data = cloudinary.uploader().upload(file.getBytes(), Map.of());
            return data;
        }catch (IOException e) {
            throw new RuntimeException("Image uploading fail.");
        }
    }
}
