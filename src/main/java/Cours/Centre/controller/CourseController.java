package Cours.Centre.controller;

import Cours.Centre.models.Course;
import Cours.Centre.models.Student;
import Cours.Centre.models.Teacher;
import Cours.Centre.services.CourseService;
import Cours.Centre.services.StudentService;
import Cours.Centre.services.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final StudentService studentService;
    private final TeacherService teacherService;

    // ADD COURSE TO STUDENT
    @PostMapping("/student/{id}/add")
    public ResponseEntity<Student> addCourseToStudent (@PathVariable Long id, Course course){
        Student optionalStudent = studentService.findStudentById(id)
                .orElseThrow(()-> new EntityNotFoundException("Student Not Found !"));
        Student studentWithCourse = courseService.addCourseToStudent(optionalStudent.getId(),course);
        return ResponseEntity.ok(studentWithCourse);
    }

    //ADD COURSE TO TEACHER
    @PostMapping("/teacher/{id}/add")
    public ResponseEntity<Teacher> addCourseToTeacher (@PathVariable Long id, Course course){
        Teacher optionalTeacher = teacherService.findTeacherById(id)
                .orElseThrow(()-> new EntityNotFoundException("Teacher Not Found"));
        Teacher teachWithCourse = courseService.addCourseToTeacher(optionalTeacher.getId(),course);
        return ResponseEntity.ok(teachWithCourse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Course>> findCourseById (@PathVariable Long id){
        return ResponseEntity.ok(courseService.findCoursById(id));
    }
}
