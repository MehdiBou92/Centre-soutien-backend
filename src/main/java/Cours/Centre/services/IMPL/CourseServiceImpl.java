package Cours.Centre.services.IMPL;

import Cours.Centre.dao.CourseRepository;
import Cours.Centre.models.Course;
import Cours.Centre.models.Student;
import Cours.Centre.models.Teacher;
import Cours.Centre.models.enums.CoursName;
import Cours.Centre.services.CourseService;
import Cours.Centre.services.StudentService;
import Cours.Centre.services.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final StudentService studentService;
    private final TeacherService teacherService;

    final int PACKAGE_PRICE_STUDENT = 300;
    final int NORMAL_PRICE_STUDENT = 120;
    final int PRICE_TEACHER_PER_COURSE = 80;

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }


    public Optional<Course> findCoursById(Long id) {
        return courseRepository.findById(id);
    }


    public List<Course> findAllCourses() {
        return courseRepository.findAll();
    }


    public Student addCourseToStudent(Long id, Course course) {
        // Find the student :
        Student student = studentService.findStudentById(id)
                .orElseThrow(()-> new EntityNotFoundException("Student Not Found"));

        // Date Registery  :
        course.setCourseRegisteryDate(LocalDate.now());

        // Set the Price :
       course.setPrice(NORMAL_PRICE_STUDENT);

       // Update the student balance :
        student.setBalance(student.getBalance() + course.getPrice());

       // Add and Save Course :
        courseRepository.save(course);
        student.getCourses().add(course);
        studentService.saveStudent(student);

        return student;
    }


    public Teacher addCourseToTeacher(Long id, Course course) {
        // Find the teacher :
        Teacher teacher = teacherService.findTeacherById(id)
                .orElseThrow(()-> new EntityNotFoundException(("Teacher Not Found")));

        // Date Registery :
        course.setCourseRegisteryDate(LocalDate.now());

        // Set the price :
        course.setPrice(PRICE_TEACHER_PER_COURSE);

        // Add and Save :
        courseRepository.save(course);
        teacher.getCourses().add(course);
        teacherService.saveTeacher(teacher);

        return teacher;
    }
}
