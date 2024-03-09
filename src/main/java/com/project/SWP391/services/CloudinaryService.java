package com.project.SWP391.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.project.SWP391.repositories.LaundryServiceRepository;
import com.project.SWP391.security.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final LaundryServiceRepository laundryServiceRepository;
    @Autowired
    private Cloudinary cloudinaryConfig;

    public String uploadFile(MultipartFile file, Long id) {
        try {
            var laundry = laundryServiceRepository.findById(id).orElseThrow();
            File uploadedFile = convertMultiPartToFile(file);
            Map uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            if(laundry.getImageBanner() != null){
                deletedFile(laundry.getImageBanner());
                laundry.setImageBanner(uploadResult.get("url").toString());
            }else{
                laundry.setImageBanner(uploadResult.get("url").toString());
            }

            laundryServiceRepository.save(laundry);
            return uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    public void deleteFile(Long id){
//        var laundry = laundryServiceRepository.findById(id).orElseThrow();
//        if(laundry.getImageBanner().isEmpty() || laundry.getImageBanner() == null){
//            throw new NullPointerException();
//        }else{
//            deletedFile(laundry.getImageBanner());
//        }
//
//    }

    private void deletedFile(String pubId) {
        String id = Utils.getPubId(pubId);
        System.out.println(id);
        try {
            Map uploadResult = cloudinaryConfig.uploader().destroy(id, ObjectUtils.emptyMap());
            //return uploadResult.toString();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }
}

