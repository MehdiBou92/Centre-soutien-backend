package Cours.Centre.controllerTest;

import Cours.Centre.controller.SessionController;
import Cours.Centre.models.Course;
import Cours.Centre.models.Session;
import Cours.Centre.models.Student;
import Cours.Centre.models.Teacher;
import Cours.Centre.services.SessionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class SessionControllerTest {

    @InjectMocks
    private SessionController sessionController;
    @Mock
    private SessionService sessionService;

    @Test
    public void givenTeacherIdAndCourseId_WhenAddSession_ThenReturnCourseWithSessions(){

        Teacher teacher = new Teacher();
        teacher.setId(1L);

        Session session = new Session();
        session.setId(1L);

        Course course = new Course();
        course.setId(1L);
        course.setSessions(List.of(session));

        given(sessionService.addSession(teacher.getId(),course.getId(),session)).willReturn(course);

        ResponseEntity<Course> courseWithSession = sessionController.addSession(teacher.getId(),course.getId(),session);

        Assertions.assertEquals(Objects.requireNonNull(courseWithSession.getBody()).getSessions().size(),1);
        Assertions.assertEquals(HttpStatus.OK,courseWithSession.getStatusCode());



    }

}
