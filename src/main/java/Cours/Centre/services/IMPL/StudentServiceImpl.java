package Cours.Centre.services.IMPL;

import Cours.Centre.dao.StudentRepository;
import Cours.Centre.models.Student;
import Cours.Centre.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public Student saveStudent(Student student) {
        return studentRepository.save(student);
    }


    public Optional<Student> findStudentById(Long id) {
        return studentRepository.findById(id);
    }


    public List<Student> findAllStudents() {
        return studentRepository.findAll();
    }
}
