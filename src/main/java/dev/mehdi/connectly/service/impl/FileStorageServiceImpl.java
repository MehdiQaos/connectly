package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.service.FileStorageService;
import jakarta.validation.ValidationException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageServiceImpl implements FileStorageService {
    private final Path rootLocation = Paths.get("upload-dir");

    @Override
    public String store(MultipartFile file) {
        try {
            if (file.getOriginalFilename() == null) {
                throw new ValidationException("Original filename is null");
            }
            System.out.println("original name: " + file.getOriginalFilename());
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
            return rootLocation.resolve(file.getOriginalFilename()).toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Could not read file: " + filename, e);
        }
    }
}
