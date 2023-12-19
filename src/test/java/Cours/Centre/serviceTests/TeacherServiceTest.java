package Cours.Centre.serviceTests;

import Cours.Centre.dao.TeacherRepository;
import Cours.Centre.models.Teacher;
import Cours.Centre.services.IMPL.TeacherServiceImpl;
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
public class TeacherServiceTest {

    @InjectMocks
    private TeacherServiceImpl teacherService;
    @Mock
    private TeacherRepository teacherRepository;

    @Test
    public void givenTeacherWhenSaveTeacherThenReturnTeacher(){
        //GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");

        when(teacherRepository.save(teacher)).thenReturn(teacher);

        //WHEN
        Teacher savedTeacher = teacherService.saveTeacher(teacher);

        //THEN
        Assertions.assertEquals(1L,savedTeacher.getId());
        Assertions.assertEquals("TeacherTest",savedTeacher.getName());

    }

    @Test
    public void givenIdWhenFindTeacherByIdThenReturnTeacher(){
        //GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");

        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));

        //WHEN
        Optional<Teacher> optionalTeacher = teacherService.findTeacherById(teacher.getId());

        //THEN
        Assertions.assertEquals(1L,optionalTeacher.get().getId());

    }

    @Test
    public void givenMultipleTeachersWhenFindAllTeachersThenReturnListOfTeachers(){
        //GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");

        Teacher teacher2 = new Teacher();
        teacher2.setId(2L);
        teacher2.setName("TeacherTest2");

        List<Teacher> teachers = List.of(teacher,teacher2);

        when(teacherRepository.findAll()).thenReturn(teachers);

        //WHEN
        List<Teacher> allTeachers = teacherService.findAllTeachers();

        //THEN
        Assertions.assertEquals(2,allTeachers.size());
        Assertions.assertEquals(1L,allTeachers.get(0).getId());

    }

}
