package Cours.Centre.services.IMPL;

import Cours.Centre.dao.SessionRepository;
import Cours.Centre.models.Course;
import Cours.Centre.models.Session;
import Cours.Centre.models.Teacher;
import Cours.Centre.services.CourseService;
import Cours.Centre.services.SessionService;
import Cours.Centre.services.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final TeacherService teacherService;
    private final CourseService courseService;

    public Course addSession(Long teacherId, Long courseId, Session session) {
        // Get the Teacher and Course :
        Teacher optionalTeacher = teacherService.findTeacherById(teacherId)
                .orElseThrow(()-> new EntityNotFoundException("Teacher Not Found"));
        Course optionalCourse = courseService.findCoursById(courseId)
                .orElseThrow(()-> new EntityNotFoundException("Course Not Found"));

        // Set the session date :
        session.setSessionDate(LocalDate.now());

        // Update session count :
        session.setCount(session.getCount()+1);

        // update the teacher Balance :
        updateTeacherBalanceBySession(optionalTeacher, optionalCourse);

        // Attach the session to the course :
        sessionRepository.save(session);
        optionalCourse.getSessions().add(session);
        courseService.saveCourse(optionalCourse);

        return optionalCourse;
    }

    private static void updateTeacherBalanceBySession(Teacher optionalTeacher, Course optionalCourse) {
        float sessionPrice = optionalCourse.getPrice()/4f;
        optionalTeacher.setBalance(sessionPrice);
    }

    public Session saveSession(Session session) {
        return sessionRepository.save(session);
    }
}
