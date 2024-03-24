package dev.mehdi.connectly.controller;

import dev.mehdi.connectly.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class StorageController {
    private final FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<Long> upload(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(fileStorageService.save(file));
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> download(@PathVariable Long id) {
        byte[] data = fileStorageService.loadPicture(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + id + ".jpg\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(data);
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        byte[] data = fileStorageService.loadPicture(id);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(data);
    }
}
