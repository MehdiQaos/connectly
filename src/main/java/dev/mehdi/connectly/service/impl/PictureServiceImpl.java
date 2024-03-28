package dev.mehdi.connectly.service.impl;

import dev.mehdi.connectly.model.Picture;
import dev.mehdi.connectly.repository.PictureRepository;
import dev.mehdi.connectly.service.PictureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class PictureServiceImpl implements PictureService {
    private final Path rootLocation = Paths.get("upload-dir");
    private final PictureRepository pictureRepository;

    @Override
    public Long save(MultipartFile file) {
        Picture picture = new Picture();
        try {
            byte[] data = file.getBytes();
            picture.setData(data);
            picture = pictureRepository.save(picture);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return picture.getId();
    }

    @Override
    public byte[] loadPicture(Long id) {
        Picture picture = pictureRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Picture not found"));
        return picture.getData();
    }

    @Override
    public void deletePicture(Long id) {
        pictureRepository.deleteById(id);
    }

//    @Override
//    public String store(MultipartFile file) {
//        try {
//            if (file.getOriginalFilename() == null) {
//                throw new ValidationException("Original filename is null");
//            }
//            System.out.println("original name: " + file.getOriginalFilename());
//            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
//            return rootLocation.resolve(file.getOriginalFilename()).toString();
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
//        }
//    }
//
//    @Override
//    public Resource load(String filename) {
//        try {
//            Path file = rootLocation.resolve(filename);
//            Resource resource = new UrlResource(file.toUri());
//            if (resource.exists() || resource.isReadable()) {
//                return resource;
//            } else {
//                throw new RuntimeException("Could not read file: " + filename);
//            }
//        } catch (MalformedURLException e) {
//            throw new RuntimeException("Could not read file: " + filename, e);
//        }
//    }
}
