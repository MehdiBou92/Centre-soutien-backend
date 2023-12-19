package Cours.Centre.controllerTest;

import Cours.Centre.controller.RegistrationController;
import Cours.Centre.models.Registration;
import Cours.Centre.models.Student;
import Cours.Centre.models.Teacher;
import Cours.Centre.services.RegistrationService;
import Cours.Centre.services.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.http.HttpClient;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class RegistrationControllerTest {

    @InjectMocks
    private RegistrationController registrationController;
    @Mock
    private RegistrationService registrationService;

    @Test
    public void givenStudentAndRegistration_WhenRegisterStudent_ThenReturnRegisteredStudent(){
        //GIVEN
        Student student = new Student();
        student.setId(1L);
        student.setName("StudentTest");

        Registration registration = new Registration();
        registration.setId(1L);

        given(registrationService.addRegisterStudent(student,registration)).willReturn(student);

        ResponseEntity<Student> result = registrationController.registerStudent(student,registration);

        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(HttpStatus.OK,result.getStatusCode());
        Assertions.assertEquals("StudentTest",result.getBody().getName());
    }


    @Test
    public void givenTeacherAndRegistration_WhenRegisterTeacher_ThenReturnRegisteredTeacher(){
        //GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");

        Registration registration = new Registration();
        registration.setId(1L);

        given(registrationService.addRegisterTeacher(teacher,registration)).willReturn(teacher);

        ResponseEntity<Teacher> result = registrationController.registerTeacher(teacher,registration);

        Assertions.assertNotNull(result.getBody());
        Assertions.assertEquals(HttpStatus.OK,result.getStatusCode());
        Assertions.assertEquals("TeacherTest",result.getBody().getName());
    }

    @Test
    public void givenMultipleRegistrations_WhenFindAllRegistrations_ThenReturnAllRegistrations(){


        Registration registration = new Registration();
        registration.setId(1L);
        Registration registration2 = new Registration();
        registration2.setId(2L);

        List<Registration> registrations = List.of(registration,registration2);

        given(registrationService.findAllRegistrations()).willReturn(registrations);

        ResponseEntity<List<Registration>> result = registrationController.findAllRegistrations();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK,result.getStatusCode());
        Assertions.assertEquals(2,result.getBody().size());
        Assertions.assertEquals(2L,result.getBody().get(1).getId());



    }

}
