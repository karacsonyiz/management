package com.example.jsp.Controller;

import com.example.jsp.GeneratedEntity.ImageEntity;
import com.example.jsp.GeneratedEntityRepository.ImageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;


@RestController
public class ImageController implements HandlerExceptionResolver {

    private ImageRepository imageRepository;

    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @RequestMapping(value = "/uploadImage", method = RequestMethod.POST)
    public ModelAndView uploadImage(@RequestParam(value = "file") MultipartFile multipartImage) throws IOException {
        ImageEntity dbImage = new ImageEntity();
        dbImage.setName(multipartImage.getName());
        dbImage.setContent(multipartImage.getBytes());
        try {
            imageRepository.save(dbImage);
        } catch (Exception e) {
            ModelAndView modelAndView = new ModelAndView("hello");
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
            return modelAndView;
        }
        return new ModelAndView("hello");
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

    @RequestMapping(value = "/getImageIdsByLimit/{limit}", method = RequestMethod.GET)
    public List<Integer> getImageIdsByLimit(@PathVariable Integer limit){
        List<Integer> imageIds = imageRepository.getImageIds();
        if(imageIds.size() < limit){
            return imageIds;
        }
        return imageRepository.getImageIds().subList(0,limit);
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView modelAndView = new ModelAndView("hello");
        if (ex instanceof MaxUploadSizeExceededException) {
            modelAndView.getModel().put("message", "File size exceeds limit!");
            modelAndView.setStatus(HttpStatus.BAD_REQUEST);
        }
        return modelAndView;
    }
}
