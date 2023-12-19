package Cours.Centre.controllerTest;

import Cours.Centre.controller.CourseController;
import Cours.Centre.models.Course;
import Cours.Centre.models.Student;
import Cours.Centre.models.Teacher;
import Cours.Centre.models.enums.CoursName;
import Cours.Centre.services.CourseService;
import Cours.Centre.services.StudentService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    @InjectMocks
    private CourseController courseController;
    @Mock
    private CourseService courseService;
    @Mock
    private StudentService studentService;
    @Mock
    TeacherService teacherService;

    @Test
    public void givenStudentIdAndCourse_whenAddCourseToStudent_ThenReturnStudentWithCourse(){

        Course course = new Course();
        course.setId(1L);

        Student student = new Student();
        student.setId(1L);
        student.setName("StudentTest");
        student.setCourses(new ArrayList<>());
        student.getCourses().add(course);

        given(studentService.findStudentById(student.getId())).willReturn(Optional.of(student));

        given(courseService.addCourseToStudent(student.getId(),course)).willReturn(student);

        ResponseEntity<Student> result = courseController.addCourseToStudent(student.getId(),course);

        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK,result.getStatusCode());
        Assertions.assertEquals(1,result.getBody().getCourses().size());
    }

    @Test
    public void givenStudentIdAndCourse_whenAddCourseToStudent_ThenThrowStudentException(){

        Course course = new Course();
        course.setId(1L);

        Student student = new Student();
        student.setId(1L);
        student.setName("StudentTest");
        student.setCourses(new ArrayList<>());
        student.getCourses().add(course);

        given(studentService.findStudentById(student.getId())).willThrow(new EntityNotFoundException("Student Not Found"));

//        given(courseService.addCourseToStudent(student.getId(),course)).willReturn(student);

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,()->
                courseController.addCourseToStudent(student.getId(),course));

        Assertions.assertEquals("Student Not Found", exception.getMessage());

    }

    @Test
    public void givenTeacherIdAndCourse_whenAddCourseToTeacher_ThenThrowTeacherException(){

        Course course = new Course();
        course.setId(1L);

       Teacher teacher = new Teacher();
       teacher.setId(1L);
       teacher.setName("TeacherTest");


        given(teacherService.findTeacherById(teacher.getId())).willThrow(new EntityNotFoundException("Teacher Not Found"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,()->
                courseController.addCourseToTeacher(teacher.getId(),course));

        Assertions.assertEquals("Teacher Not Found", exception.getMessage());

    }

    @Test
    public void givenTeacherIdAndCourse_whenAddCourseToTeacher_thenReturnTeacherWithCourse(){
        Course course = new Course();
        course.setId(1L);

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");
        teacher.setCourses(List.of(course));

        given(teacherService.findTeacherById(teacher.getId())).willReturn(Optional.of(teacher));
        given(courseService.addCourseToTeacher(teacher.getId(), course)).willReturn(teacher);

        ResponseEntity<Teacher> result = courseController.addCourseToTeacher(teacher.getId(),course);
        Assertions.assertNotNull(result);
        Assertions.assertEquals(HttpStatus.OK,result.getStatusCode());
        Assertions.assertEquals(1,result.getBody().getCourses().size());

    }

    @Test
    public void givenCourseIdWhenFindCourseByIdThenReturnCourse(){

        Course course = new Course();
        course.setId(1L);
        course.setCoursName(CoursName.FRENCH);

        when(courseService.findCoursById(course.getId())).thenReturn(Optional.of(course));

        ResponseEntity<Optional<Course>> optionalCourse = courseController.findCourseById(course.getId());

        Assertions.assertEquals(1L,optionalCourse.getBody().get().getId());
        Assertions.assertEquals(CoursName.FRENCH,optionalCourse.getBody().get().getCoursName());
        Assertions.assertEquals(HttpStatus.OK,optionalCourse.getStatusCode());
        verify(courseService, times(1)).findCoursById(course.getId());

    }
}
