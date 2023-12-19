package Cours.Centre.controllerTest;

import Cours.Centre.controller.StudentController;
import Cours.Centre.models.Student;
import Cours.Centre.services.StudentService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @InjectMocks
    private StudentController studentController;
    @Mock
    private StudentService studentService;

    @Test
    public void givenStudentId_WhenFindStudentById_ThenReturnStudent(){
        //GIVEN
        Student student = new Student();
        student.setId(1L);
        student.setName("StudentTest");

        given(studentService.findStudentById(student.getId())).willReturn(Optional.of(student));

        ResponseEntity<Student> results = studentController.findStudentById(student.getId());

        Assertions.assertEquals(HttpStatus.OK,results.getStatusCode());
        Assertions.assertEquals("StudentTest",results.getBody().getName());
    }

    @Test
    public void givenStudentId_WhenFindStudentById_ThenThrowException(){
        //GIVEN
        Student student = new Student();
        student.setId(1L);
        student.setName("StudentTest");

        given(studentService.findStudentById(student.getId())).willThrow(new EntityNotFoundException("Student Not Found"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,()->
                studentController.findStudentById(student.getId()));

        Assertions.assertEquals("Student Not Found",exception.getMessage());
    }

    @Test
    public void givenMultipleStudents_whenFindAllStudents_ThenReturnAllStudents(){
        //GIVEN
        Student student = new Student();
        student.setId(1L);
        student.setName("StudentTest");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("StudentTest2");

        List<Student> students = List.of(student,student2);

        given(studentService.findAllStudents()).willReturn(students);

        ResponseEntity<List<Student>> results = studentController.findAllStudents();

        Assertions.assertEquals(results.getBody().size(),2);
        Assertions.assertEquals(HttpStatus.OK,results.getStatusCode());
        Assertions.assertEquals(results.getBody().get(1).getName(),"StudentTest2");


    }

}
