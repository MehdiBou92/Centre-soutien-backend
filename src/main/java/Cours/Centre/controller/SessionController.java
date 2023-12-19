package Cours.Centre.controller;

import Cours.Centre.models.Course;
import Cours.Centre.models.Session;
import Cours.Centre.services.CourseService;
import Cours.Centre.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping("/{teacherId}/{courseId}/add")
    public ResponseEntity<Course> addSession (
            @PathVariable Long teacherId,
            @PathVariable Long courseId,
            @RequestBody Session session){

        Course courseWithSession = sessionService.addSession(teacherId,courseId,session);
        return ResponseEntity.ok(courseWithSession);
    }
}
