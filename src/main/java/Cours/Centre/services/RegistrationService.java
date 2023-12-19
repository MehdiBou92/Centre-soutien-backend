package Cours.Centre.services;

import Cours.Centre.models.Registration;
import Cours.Centre.models.Student;
import Cours.Centre.models.Teacher;

import java.util.List;
import java.util.Optional;

public interface RegistrationService {

    Student addRegisterStudent (Student student, Registration registration);
    Teacher addRegisterTeacher (Teacher teacher, Registration registration);

    List<Registration> findAllRegistrations();
    List<Registration> findAllStudentRegistrations();

}
