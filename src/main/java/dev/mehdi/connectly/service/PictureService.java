package dev.mehdi.connectly.service;

import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
    Long save(MultipartFile file);

    byte[] loadPicture(Long id);

    void deletePicture(Long id);

//    String store(MultipartFile file);

//    Resource load(String filename);
}
