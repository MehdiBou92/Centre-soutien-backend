package Cours.Centre.services;

import Cours.Centre.models.Teacher;

import java.util.List;
import java.util.Optional;

public interface TeacherService {

    Teacher saveTeacher (Teacher teacher);
    Optional<Teacher> findTeacherById (Long id);
    List<Teacher> findAllTeachers ();
}
