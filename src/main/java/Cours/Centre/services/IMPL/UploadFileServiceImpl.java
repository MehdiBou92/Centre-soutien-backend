package Cours.Centre.services.IMPL;

import Cours.Centre.dao.UploadFileRepository;
import Cours.Centre.models.UploadFile;
import Cours.Centre.services.UploadFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UploadFileServiceImpl implements UploadFileService {

    private static final String UPLOAD_DIR = "C:/uploads" ;

    private final UploadFileRepository uploadFileRepository;

    public String uploadFile(MultipartFile file)   {

        UploadFile uploadFile = new UploadFile();
        uploadFile.setFilePath(UPLOAD_DIR);
        uploadFile.setFileName(file.getOriginalFilename());

        uploadFileRepository.save(uploadFile);

        return uploadFile.getFileName();
    }

    public List<UploadFile> files() {
        return uploadFileRepository.findAll();
    }

    public Optional<UploadFile> getFileById(Long id) {
        return uploadFileRepository.findById(id);
    }


}




