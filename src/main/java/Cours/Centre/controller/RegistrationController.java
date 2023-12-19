package Cours.Centre.controller;

import Cours.Centre.models.Registration;
import Cours.Centre.models.Student;
import Cours.Centre.models.Teacher;
import Cours.Centre.services.RegistrationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;


    @PostMapping("/student/add")
    public ResponseEntity<Student> registerStudent (@RequestBody Student student, Registration registration){
        Student savedStudent = registrationService.addRegisterStudent(student,registration);
        return ResponseEntity.ok(savedStudent);
    }

    @PostMapping("/teacher/add")
        public ResponseEntity<Teacher> registerTeacher (@RequestBody Teacher teacher, Registration registration){
        Teacher savedTeacher = registrationService.addRegisterTeacher(teacher,registration);
        return ResponseEntity.ok(savedTeacher);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Registration>> findAllRegistrations(){
        return ResponseEntity.ok(registrationService.findAllRegistrations());
    }

}
