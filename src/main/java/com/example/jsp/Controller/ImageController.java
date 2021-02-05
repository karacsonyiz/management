package com.example.jsp.Controller;


import com.example.jsp.GeneratedEntity.ImageEntity;
import com.example.jsp.GeneratedEntityRepository.ImageRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;


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
        } catch (Exception e) {
            return null;
        }
    }

    @RequestMapping(value = "/getImage/{id}", method = RequestMethod.GET)
    public String getImage(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<ImageEntity> image = imageRepository.findById(id);
        if(!image.isPresent()){
            return null;
        }
        response.getOutputStream().write(image.get().getContent());
        response.getOutputStream().close();
        return null;
    }

    @RequestMapping(value = "/getImageLabel/{id}", method = RequestMethod.GET)
    public String getImageLabel(@PathVariable Integer id){
        ImageEntity image = imageRepository.findById(id).get();
        return image.getName();
    }
}
