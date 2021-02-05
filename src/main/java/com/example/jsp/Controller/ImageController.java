package com.example.jsp.Controller;


import com.example.jsp.GeneratedEntity.ImageEntity;
import com.example.jsp.GeneratedEntityRepository.ImageRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class ImageController {

    private ImageRepository imageRepository;

    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public Integer uploadImage(@RequestParam(value = "file") MultipartFile multipartImage) throws IOException {
        ImageEntity dbImage = new ImageEntity();
        dbImage.setName(multipartImage.getName());
        dbImage.setContent(multipartImage.getBytes());
        try {
            return imageRepository.save(dbImage).getId();
        } catch(Exception e){
            return null;
        }
    }
}
