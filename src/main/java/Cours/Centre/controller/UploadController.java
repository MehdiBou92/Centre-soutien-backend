package Cours.Centre.controller;

import Cours.Centre.models.UploadFile;
import Cours.Centre.services.UploadFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/upload")
public class UploadController {



        private final UploadFileService uploadFileService;

    public UploadController(UploadFileService uploadFileService) {
        this.uploadFileService = uploadFileService;
    }

    @PostMapping
        public ResponseEntity<String> addFile(@RequestParam("file") MultipartFile file) {

                String fileName = uploadFileService.uploadFile(file);
                return ResponseEntity.ok("File uploaded successfully: " + fileName);

        }

    @GetMapping("/all")
    public ResponseEntity<List<UploadFile>> getAllFiles () {
        return ResponseEntity.ok(uploadFileService.files());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<UploadFile>> findFileById(@PathVariable Long id){
        Optional<UploadFile> optionalFile = uploadFileService.getFileById(id);
            return ResponseEntity.ok(optionalFile);
    }
}
