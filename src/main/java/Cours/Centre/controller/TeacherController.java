package Cours.Centre.controller;

import Cours.Centre.models.Teacher;
import Cours.Centre.services.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Teacher> findTeacherById (@PathVariable Long id){
        Teacher optionalTeacher = teacherService.findTeacherById(id)
                .orElseThrow(()-> new EntityNotFoundException("Teacher Not Found !"));
        return ResponseEntity.ok(optionalTeacher);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Teacher>> findAllTeachers (){
        return ResponseEntity.ok(teacherService.findAllTeachers());
    }
}
