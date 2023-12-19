package Cours.Centre.serviceTests;

import Cours.Centre.dao.SessionRepository;
import Cours.Centre.models.Course;
import Cours.Centre.models.Session;
import Cours.Centre.models.Teacher;
import Cours.Centre.models.enums.CoursName;
import Cours.Centre.services.CourseService;
import Cours.Centre.services.IMPL.SessionServiceImpl;
import Cours.Centre.services.SessionService;
import Cours.Centre.services.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @InjectMocks
    private SessionServiceImpl sessionService;
    @Mock
    private SessionRepository sessionRepository;
    @Mock
    private TeacherService teacherService;
    @Mock
    private CourseService courseService;

    @Test
    public void givenTeacherIdAndCourseIdAndSession_WhenAddSession_ThenReturnCourseWithSession(){
        //GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");
        teacher.setCourses(new ArrayList<>());
        teacher.setBalance(0f);

        CoursName coursName = CoursName.FRENCH;
        Course course = new Course();
        course.setId(1L);
        course.setPrice(80);
        course.setCoursName(coursName);

        Session session = new Session();
        session.setId(1L);

        when(teacherService.findTeacherById(teacher.getId())).thenReturn(Optional.of(teacher));
        when(courseService.findCoursById(course.getId())).thenReturn(Optional.of(course));

        //WHEN

        Course courseWithSession = sessionService.addSession(teacher.getId(),course.getId(),session);

        // THEN
        Assertions.assertNotNull(courseWithSession);
        Assertions.assertEquals(LocalDate.now(),courseWithSession.getSessions().get(0).getSessionDate());
        Assertions.assertEquals(1,courseWithSession.getSessions().get(0).getCount());
        Assertions.assertEquals(20,teacher.getBalance());

    }

    @Test
    public void givenTeacherIdAndCourseIdAndSession_WhenAddSession_ThenReturnTeacherException() {
        //GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");
        teacher.setCourses(new ArrayList<>());
        teacher.setBalance(0f);

        CoursName coursName = CoursName.FRENCH;
        Course course = new Course();
        course.setId(1L);
        course.setPrice(80);
        course.setCoursName(coursName);

        Session session = new Session();
        session.setId(1L);

        when(teacherService.findTeacherById(teacher.getId())).thenThrow(new EntityNotFoundException("Teacher Not Found"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                sessionService.addSession(teacher.getId(), course.getId(), session));

        Assertions.assertEquals("Teacher Not Found", exception.getMessage());
    }


    @Test
    public void givenTeacherIdAndCourseIdAndSession_WhenAddSession_ThenReturnCourseException() {
        //GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");
        teacher.setCourses(new ArrayList<>());
        teacher.setBalance(0f);

        CoursName coursName = CoursName.FRENCH;
        Course course = new Course();
        course.setId(1L);
        course.setPrice(80);
        course.setCoursName(coursName);

        Session session = new Session();
        session.setId(1L);

        when(teacherService.findTeacherById(teacher.getId())).thenReturn(Optional.of(teacher));
        when(courseService.findCoursById(course.getId())).thenThrow(new EntityNotFoundException("Course Not Found"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                sessionService.addSession(teacher.getId(), course.getId(), session));

        Assertions.assertEquals("Course Not Found", exception.getMessage());

    }


    @Test
    public void givenSessionWhenSaveSessionThenReturnSession(){
        Session session = new Session();
        session.setId(1L);

        when(sessionRepository.save(session)).thenReturn(session);

        Session savedSession = sessionService.saveSession(session);

        Assertions.assertNotNull(savedSession);
        Assertions.assertEquals(1L,savedSession.getId());



    }
}
