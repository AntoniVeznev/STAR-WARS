package com.example.star_wars_project.service.impl;

import com.cloudinary.Cloudinary;
import com.example.star_wars_project.utils.CloudinaryImage;
import com.example.star_wars_project.service.CloudinaryService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {
    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";
    private static final String PUBLIC_ID = "public_id";
    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public CloudinaryImage upload(MultipartFile multipartFile) throws IOException {

        File tempFile = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);
        CloudinaryImage result;

        try {
            @SuppressWarnings("unchecked")
            Map<String, String> uploadResult = cloudinary.uploader().upload(tempFile, Map.of());

            String url = uploadResult.getOrDefault(URL, "https://as1.ftcdn.net/v2/jpg/01/35/88/24/1000_F_135882430_6Ytw6sJKC5jg8ovh18XjAHuMXcS7mlai.jpg");
            String publicId = uploadResult.getOrDefault(PUBLIC_ID, "");

            result = new CloudinaryImage();
            result.setPublicId(publicId);
            result.setUrl(url);

        } finally {
            tempFile.delete();
        }
        return result;
    }

    @Override
    public boolean delete(String publicId) {
        try {
            this.cloudinary.uploader().destroy(publicId, Map.of());
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}