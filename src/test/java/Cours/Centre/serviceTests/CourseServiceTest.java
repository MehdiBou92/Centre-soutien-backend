package Cours.Centre.serviceTests;

import Cours.Centre.dao.CourseRepository;
import Cours.Centre.models.Course;
import Cours.Centre.models.Student;
import Cours.Centre.models.Teacher;
import Cours.Centre.models.enums.CoursName;
import Cours.Centre.services.CourseService;
import Cours.Centre.services.IMPL.CourseServiceImpl;
import Cours.Centre.services.StudentService;
import Cours.Centre.services.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @InjectMocks
    private CourseServiceImpl courseService;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private StudentService studentService;
    @Mock
    private TeacherService teacherService;

    @Test
    public void givenCourseWhenSaveCourseThenRreturnCourse() {
        // GIVEN
        CoursName coursName = CoursName.FRENCH;

        Course course = new Course();
        course.setId(1L);
        course.setCoursName(coursName);

        when(courseRepository.save(course)).thenReturn(course);

        //WHEN

        Course savedCourse = courseService.saveCourse(course);

        // THEN
        Assertions.assertNotNull(savedCourse);
        Assertions.assertEquals(1L, savedCourse.getId());
        Assertions.assertEquals(coursName, savedCourse.getCoursName());

    }


    @Test
    public void givenCourseIdWhenFindCourseByIdThenReturnCourse() {
        // GIVEN
        CoursName coursName = CoursName.FRENCH;
        Course course = new Course();
        course.setId(1L);
        course.setCoursName(coursName);

        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));

        //WHEN
        Optional<Course> foundCourse = courseService.findCoursById(course.getId());

        //THEN
        Assertions.assertNotNull(foundCourse);
        Assertions.assertEquals(1L, foundCourse.get().getId());
        Assertions.assertEquals(coursName, foundCourse.get().getCoursName());

    }


    @Test
    public void givenMultipleCoursesWhenFindAllCoursesThenReturnListOfCourses(){
        // GIVEN
        CoursName coursName = CoursName.FRENCH;
        Course course = new Course();
        course.setId(1L);
        course.setCoursName(coursName);

        Course course2 = new Course();
        course2.setId(2L);
        course2.setCoursName(coursName);

        List<Course> courses = List.of(course,course2);

        when(courseRepository.findAll()).thenReturn(courses);

        // WHEN

        List<Course> allCourses = courseService.findAllCourses();

        // THEN

        Assertions.assertNotNull(allCourses);
        Assertions.assertEquals(2,allCourses.size());
        Assertions.assertEquals(1L,allCourses.get(0).getId());

    }


    @Test
    public void givenStudentIdAndCourse_WhenAddCourseToStudent_ThenReturnStudentWithCourse(){
        // GIVEN
        Student student = new Student();
        student.setId(1L);
        student.setName("StudentTest");
        student.setCourses(new ArrayList<>());

        CoursName coursName = CoursName.FRENCH;
        Course course = new Course();
        course.setId(1L);
        course.setCoursName(coursName);

        when(studentService.findStudentById(student.getId())).thenReturn(Optional.of(student));
        when(courseRepository.save(course)).thenReturn(course);

        // WHEN

        Student studentCoursed = courseService.addCourseToStudent(student.getId(),course);

        // THEN
        Assertions.assertNotNull(studentCoursed);
        Assertions.assertEquals(LocalDate.now(),studentCoursed.getCourses().get(0).getCourseRegisteryDate());
        Assertions.assertEquals(1,studentCoursed.getCourses().size());
        Assertions.assertEquals(120,studentCoursed.getCourses().get(0).getPrice());
        Assertions.assertEquals(120,studentCoursed.getBalance());

    }

    @Test
    public void givenStudentIdAndCourse_WhenAddCourseToStudent_ThenThrowEntityNotFoundException() {
        // GIVEN
        Student student = new Student();
        student.setId(1L);
        student.setName("StudentTest");
        student.setCourses(new ArrayList<>());

        CoursName coursName = CoursName.FRENCH;
        Course course = new Course();
        course.setId(1L);
        course.setCoursName(coursName);

        when(studentService.findStudentById(student.getId())).thenThrow(new EntityNotFoundException("Student Not Found"));

        // WHEN

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,
                ()-> courseService.addCourseToStudent(student.getId(),course) );

        Assertions.assertEquals("Student Not Found", exception.getMessage());
    }


    @Test
    public void givenTeacherIdAndCourse_WhenAddCourseToTeacher_ThenReturnTeacherWithCourse(){
        //GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");
        teacher.setCourses(new ArrayList<>());

        CoursName coursName = CoursName.FRENCH;
        Course course = new Course();
        course.setId(1L);
        course.setCoursName(coursName);

        when(teacherService.findTeacherById(teacher.getId())).thenReturn(Optional.of(teacher));
        when(courseRepository.save(course)).thenReturn(course);
        when(teacherService.saveTeacher(teacher)).thenReturn(teacher);

        // WHEN

        Teacher teacherWithCourse = courseService.addCourseToTeacher(teacher.getId(),course);

        //THEN
        Assertions.assertNotNull(teacherWithCourse);
        Assertions.assertEquals(1L,teacherWithCourse.getId());
        Assertions.assertEquals(1,teacherWithCourse.getCourses().size());
        Assertions.assertEquals(coursName,teacherWithCourse.getCourses().get(0).getCoursName());
        Assertions.assertEquals(LocalDate.now(),teacherWithCourse.getCourses().get(0).getCourseRegisteryDate());
        Assertions.assertEquals(80,teacherWithCourse.getCourses().get(0).getPrice());
        verify(teacherService,times(1)).findTeacherById(teacher.getId());
    }

    @Test
    public void givenTeacherIdAndCourse_WhenAddCourseToTeacher_ThenThrowEntityNotFoundException() {
        //GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");
        teacher.setCourses(new ArrayList<>());

        CoursName coursName = CoursName.FRENCH;
        Course course = new Course();
        course.setId(1L);
        course.setCoursName(coursName);

        when(teacherService.findTeacherById(teacher.getId())).thenThrow(new EntityNotFoundException("Teacher Not Found"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class,
                () -> courseService.addCourseToTeacher(teacher.getId(), course));

        Assertions.assertEquals("Teacher Not Found",exception.getMessage());

    }
}
