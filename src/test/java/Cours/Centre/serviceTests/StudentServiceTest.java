package Cours.Centre.serviceTests;

import Cours.Centre.dao.StudentRepository;
import Cours.Centre.models.Student;
import Cours.Centre.services.IMPL.StudentServiceImpl;
import Cours.Centre.services.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @InjectMocks
    private StudentServiceImpl studentService;
    @Mock
    private StudentRepository studentRepository;

    @Test
    public void givenStudentWhenSaveStudentThenReturnStudent(){
        //GIVEN
        Student student = new Student();
        student.setId(1L);
        student.setName("StudentTest");

        when(studentRepository.save(student)).thenReturn(student);

        //WHEN
        Student savedStudent = studentService.saveStudent(student);

        //THEN
        Assertions.assertEquals(1L,savedStudent.getId());
        Assertions.assertEquals("StudentTest",savedStudent.getName());

    }

    @Test
    public void givenIdWhenFindStudentByIdThenReturnStudent(){
        //GIVEN
        Student student = new Student();
        student.setId(1L);
        student.setName("StudentTest");

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));

        //WHEN
        Optional<Student> optionalStudent = studentService.findStudentById(student.getId());

        //THEN
        Assertions.assertEquals(1L,optionalStudent.get().getId());

    }

    @Test
    public void givenMultipleStudentsWhenFindAllStudentsThenReturnListOfStudents(){
        //GIVEN
        Student student = new Student();
        student.setId(1L);
        student.setName("StudentTest");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("StudentTest2");

        List<Student> students = List.of(student,student2);

        when(studentRepository.findAll()).thenReturn(students);

        //WHEN
        List<Student> allStudents = studentService.findAllStudents();

        //THEN
        Assertions.assertEquals(2,allStudents.size());
        Assertions.assertEquals(1L,allStudents.get(0).getId());

    }
}
