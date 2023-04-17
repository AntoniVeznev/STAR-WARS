package com.example.star_wars_project.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CloudinaryImageTest {
    @Test
    public void testGettersAndSetters() {
        String url = "https://res.cloudinary.com/demo/image/upload/sample.jpg";
        String publicId = "sample";

        CloudinaryImage image = new CloudinaryImage();
        image.setUrl(url);
        image.setPublicId(publicId);

        assertEquals(url, image.getUrl(), "The URL should be " + url);
        assertEquals(publicId, image.getPublicId(), "The public ID should be " + publicId);
    }
}
