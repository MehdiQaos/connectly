package dev.mehdi.connectly.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    Long save(MultipartFile file);

    byte[] loadPicture(Long id);

//    String store(MultipartFile file);

//    Resource load(String filename);
}
