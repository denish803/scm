package com.scm.services_implement;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.services.ImageService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Log4j2
public class ImageServiceImplement implements ImageService {

    @Autowired
    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile multipartFile, String fileName) {
        try {
            byte[] b = new byte[multipartFile.getInputStream().available()];

            try {
                cloudinary.uploader().upload(b, ObjectUtils.asMap(
                        "public_id", fileName
                ));
                return this.getImageURL(fileName);
            } catch (Exception e) {
                log.error("Image Service Cloudinary Uploader : {}", e.getLocalizedMessage());
                return null;
            }

        } catch (IOException e) {
            log.error("ImageServiceImplement : ", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getImageURL(String fileName) {

        return cloudinary
                .url()
                .transformation(new Transformation<>())
                .generate(fileName);
    }
}
