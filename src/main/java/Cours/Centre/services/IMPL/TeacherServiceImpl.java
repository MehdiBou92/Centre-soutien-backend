package Cours.Centre.services.IMPL;

import Cours.Centre.dao.TeacherRepository;
import Cours.Centre.models.Teacher;
import Cours.Centre.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    public Teacher saveTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }


    public Optional<Teacher> findTeacherById(Long id) {
        return teacherRepository.findById(id);
    }


    public List<Teacher> findAllTeachers() {
        return teacherRepository.findAll();
    }
}
