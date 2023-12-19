package Cours.Centre.serviceTests;

import Cours.Centre.dao.RegistrationRepository;
import Cours.Centre.models.Registration;
import Cours.Centre.models.Student;
import Cours.Centre.models.Teacher;
import Cours.Centre.services.IMPL.RegistrationServiceImpl;
import Cours.Centre.services.RegistrationService;
import Cours.Centre.services.StudentService;
import Cours.Centre.services.TeacherService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RegistrationServiceTest {

    @InjectMocks
    private RegistrationServiceImpl registrationService;

    @Mock
    private StudentService studentService;
    @Mock
    private TeacherService teacherService;
    @Mock
    private RegistrationRepository registrationRepository;

    @Test
    public void givenTeacherWhenAddRegisterTeacherThenReturnRegisteredTeacher() {
        //GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");

        Registration registration = new Registration();
        registration.setId(1L);
        registration.setRegistrationDate(LocalDate.now());

        when(registrationRepository.save(any(Registration.class))).thenReturn(registration);
        when(teacherService.saveTeacher(teacher)).thenReturn(teacher);

        //WHEN
        Teacher registeredTeacher = registrationService.addRegisterTeacher(teacher, registration);

        //Assertions:
        Assertions.assertEquals(registeredTeacher, registration.getTeacher());
        Assertions.assertEquals(0f, registeredTeacher.getBalance());
        Assertions.assertEquals(registeredTeacher.getHiringDate(), registration.getRegistrationDate());

    }

    @Test
    public void givenStudentAndRegistration_WhenAddRegisterStudent_thenReturnRegistredStudent(){

        //GIVEN
        Student student = new Student();
        student.setId(1L);
        student.setName("StudentTest");

        Registration registration = new Registration();
        registration.setId(1L);
        registration.setAmount(100);
        registration.setStudent(student);

        when(registrationRepository.save(registration)).thenReturn(registration);
        when(studentService.saveStudent(student)).thenReturn(student);

        Student registredStudent = registrationService.addRegisterStudent(student,registration);

        Assertions.assertNotNull(registredStudent);
        Assertions.assertEquals(1L,registredStudent.getId());
        Assertions.assertEquals(registredStudent.getRegistrationDate(),LocalDate.now());
        Assertions.assertEquals(100,registredStudent.getBalance());
        Assertions.assertTrue(registredStudent.isActive());
    }

    @Test
    public void givenMultipleRegistrations_whenFindAllRegistrations_ThenReturnAllRegistrations(){

        Registration registration = new Registration();
        registration.setId(1L);

        Registration registration2 = new Registration();
        registration2.setId(2L);

        List<Registration>  registrations = List.of(registration,registration2);

        when(registrationRepository.findAll()).thenReturn(registrations);
        List<Registration> result = registrationService.findAllRegistrations();

        Assertions.assertEquals(2,result.size());
        Assertions.assertEquals(1L,result.get(0).getId());
    }

}
