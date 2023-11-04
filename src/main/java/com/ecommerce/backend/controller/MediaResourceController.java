package com.ecommerce.backend.controller;

import com.ecommerce.backend.dto.ApiMessageDto;
import com.ecommerce.backend.service.MediaResourceService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/v1/media-resource")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
public class MediaResourceController {

    @Autowired
    MediaResourceService mediaResourceService;

    @Value("${file.path.product-images}")
    private String path;

    @GetMapping("/download/{filename:.+}")
    public ResponseEntity<Resource> download(@PathVariable String filename ) {
        Resource file = mediaResourceService.load(path, filename );
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @GetMapping("/delete/{filename:.+}")
    public ApiMessageDto<String> deleteFile(@PathVariable String filename) {
        ApiMessageDto apiMessageDto = new ApiMessageDto<>();
        mediaResourceService.delete(path, filename);
        apiMessageDto.setMessage("Delete file success");
        return apiMessageDto;
    }

    @GetMapping(value = "/{filename}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
        public void getFile(@PathVariable String filename, HttpServletResponse response) {
        try {
            InputStream resource = mediaResourceService.getResource(path, filename);
            response.setContentType(MediaType.IMAGE_JPEG_VALUE);
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            StreamUtils.copy(resource,response.getOutputStream());
        }catch (IOException e){
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
