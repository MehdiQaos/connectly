package dev.mehdi.connectly.repository;

import dev.mehdi.connectly.model.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}