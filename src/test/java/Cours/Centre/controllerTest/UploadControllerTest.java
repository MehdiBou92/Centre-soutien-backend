package Cours.Centre.controllerTest;

import Cours.Centre.controller.UploadController;
import Cours.Centre.models.UploadFile;
import Cours.Centre.services.UploadFileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UploadControllerTest {

    @InjectMocks
    private UploadController uploadController;
    @Mock
    private UploadFileService uploadFileService;


    @Test
    public void givenFileToUploadWhenAddFileThenReturnSucessMessage(){

        MockMultipartFile file = new MockMultipartFile(
                "file",
                "text.txt".getBytes()
        );

        given(uploadFileService.uploadFile(file)).willReturn(file.getName());

        ResponseEntity<String> response = uploadController.addFile(file);

        Assertions.assertEquals("File uploaded successfully: file",response.getBody());
        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());

    }


    @Test
    public void givenMultipleFile_whenGetAllFiles_ThenReturnAllUploadedFiles(){

        UploadFile uploadFile = new UploadFile();
        uploadFile.setFileName("fileTest");
        UploadFile uploadFile2 = new UploadFile();
        uploadFile2.setFileName("fileTest2");

        List<UploadFile> files = List.of(uploadFile,uploadFile2);
        given(uploadFileService.files()).willReturn(files);

        ResponseEntity<List<UploadFile>> response = uploadController.getAllFiles();

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(2,response.getBody().size());
        Assertions.assertEquals("fileTest2",response.getBody().get(1).getFileName());

    }

    @Test
    public void givenUploadFileWhenFindFileByIdThenReturnFile(){
        UploadFile uploadFile = new UploadFile();
        uploadFile.setId(1L);
        uploadFile.setFileName("fileTest");

        given(uploadFileService.getFileById(uploadFile.getId())).willReturn(Optional.of(uploadFile));

        ResponseEntity<Optional<UploadFile>> response = uploadController.findFileById(uploadFile.getId());

        Assertions.assertEquals(HttpStatus.OK,response.getStatusCode());
        Assertions.assertEquals(1L,response.getBody().get().getId());
        verify(uploadFileService,times(1)).getFileById(uploadFile.getId());

    }
}
