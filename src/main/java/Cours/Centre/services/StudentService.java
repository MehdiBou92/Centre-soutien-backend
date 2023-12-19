package Cours.Centre.services;

import Cours.Centre.models.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Student saveStudent(Student student);
    Optional<Student> findStudentById (Long id);
    List<Student> findAllStudents();

}
