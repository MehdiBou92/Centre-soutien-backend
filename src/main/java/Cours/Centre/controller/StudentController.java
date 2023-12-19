package Cours.Centre.controller;

import Cours.Centre.models.Student;
import Cours.Centre.services.StudentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudentById (@PathVariable Long id){
        Student optionalStudent = studentService.findStudentById(id)
                .orElseThrow(()-> new EntityNotFoundException("Student Not Found"));
        return ResponseEntity.ok(optionalStudent);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Student>> findAllStudents () {
        return ResponseEntity.ok(studentService.findAllStudents());
    }

    @PostMapping("/add")
    public ResponseEntity<Student> addStudent (@RequestBody Student student){
        Student savedStudent = studentService.saveStudent(student);
        return ResponseEntity.ok(savedStudent);
    }


}
