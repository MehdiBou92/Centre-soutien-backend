package Cours.Centre.dao;

import Cours.Centre.models.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile,Long> {
}
