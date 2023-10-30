package com.ecommerce.backend.service.impl;

import com.ecommerce.backend.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
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
        return filePath;
    }
}
