package Cours.Centre.controllerTest;

import Cours.Centre.controller.TeacherController;
import Cours.Centre.models.Teacher;
import Cours.Centre.services.TeacherService;
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
public class TeacherControllerTest {

    @InjectMocks
    private TeacherController teacherController;
    @Mock
    private TeacherService teacherService;

    @Test
    public void givenTeacherId_WhenFindTeacherById_ThenReturnTeacher(){
        //GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");

        given(teacherService.findTeacherById(teacher.getId())).willReturn(Optional.of(teacher));

        ResponseEntity<Teacher> results = teacherController.findTeacherById(teacher.getId());

        Assertions.assertEquals(HttpStatus.OK,results.getStatusCode());
        Assertions.assertEquals("TeacherTest",results.getBody().getName());
    }

    @Test
    public void givenTeacherId_WhenFindTeacherById_ThenThrowException(){
        //GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");

        given(teacherService.findTeacherById(teacher.getId())).willThrow(new EntityNotFoundException("Teacher Not Found"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,()->
                teacherController.findTeacherById(teacher.getId()));

        Assertions.assertEquals("Teacher Not Found",exception.getMessage());
    }

    @Test
    public void givenMultipleTeachers_whenFindAllTeachers_ThenReturnAllTeachers(){
        //GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setName("TeacherTest2");

        List<Teacher> teachers = List.of(teacher,teacher2);

        given(teacherService.findAllTeachers()).willReturn(teachers);

        ResponseEntity<List<Teacher>> results = teacherController.findAllTeachers();

        Assertions.assertEquals(results.getBody().size(),2);
        Assertions.assertEquals(HttpStatus.OK,results.getStatusCode());
        Assertions.assertEquals(results.getBody().get(1).getName(),"TeacherTest2");


    }

}
