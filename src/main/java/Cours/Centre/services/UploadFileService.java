package Cours.Centre.services;

import Cours.Centre.models.UploadFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface UploadFileService {

    String uploadFile(MultipartFile file)  ;
    List<UploadFile> files();

    Optional<UploadFile> getFileById (Long id);


}
