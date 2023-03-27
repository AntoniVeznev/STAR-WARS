package com.example.star_wars_project.service;

import com.example.star_wars_project.utils.CloudinaryImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CloudinaryService {
    CloudinaryImage upload(MultipartFile multipartFile) throws IOException;

    boolean delete(String publicId);

}
