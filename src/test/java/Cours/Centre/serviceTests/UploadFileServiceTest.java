package Cours.Centre.serviceTests;

import Cours.Centre.dao.UploadFileRepository;
import Cours.Centre.models.UploadFile;
import Cours.Centre.services.IMPL.UploadFileServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UploadFileServiceTest {

@InjectMocks
    private UploadFileServiceImpl uploadFileService;
@Mock
    private UploadFileRepository uploadFileRepository;


@Test
    public void givenFileWhenUploadFileThenReturnUploadedFileName(){

    MockMultipartFile mockMultipartFile = new MockMultipartFile(
            "file",
            "text.txt",
            "text/plain",
            "Hello, World!".getBytes()
    );
    when(uploadFileRepository.save(Mockito.any(UploadFile.class))).thenReturn(new UploadFile());

    String uploadedFile = uploadFileService.uploadFile(mockMultipartFile);

    Assertions.assertEquals("text.txt",uploadedFile);

}

@Test
    public void givenUploadedFileWhenGetFileByIdThenReturnFile(){
    UploadFile uploadFile = new UploadFile();
    uploadFile.setId(1L);
    uploadFile.setFileName("text.txt");

    when(uploadFileRepository.findById(uploadFile.getId())).thenReturn(Optional.of(uploadFile));

    Optional<UploadFile> optionalFile = uploadFileService.getFileById(uploadFile.getId());

     Assertions.assertTrue(optionalFile.isPresent());
     Assertions.assertEquals(1L,optionalFile.get().getId());
     Assertions.assertEquals("text.txt",optionalFile.get().getFileName());


}

}
