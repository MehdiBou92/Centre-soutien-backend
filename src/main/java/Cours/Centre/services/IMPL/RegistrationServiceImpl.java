package Cours.Centre.services.IMPL;

import Cours.Centre.dao.RegistrationRepository;
import Cours.Centre.models.Registration;
import Cours.Centre.models.Student;
import Cours.Centre.models.Teacher;
import Cours.Centre.services.RegistrationService;
import Cours.Centre.services.StudentService;
import Cours.Centre.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final RegistrationRepository registrationRepository;
    private final StudentService studentService;
    private final TeacherService teacherService;


    public Student addRegisterStudent(Student student, Registration registration) {



        // Set the registry date:
        registration.setRegistrationDate(LocalDate.now());
        student.setRegistrationDate(registration.getRegistrationDate());



        // Activate Student:
        student.setActive(true);


        registration.setStudent(student);
        registration.setAmount(student.getAmount());

        student.setBalance(student.getAmount());

        registrationRepository.save(registration);

        System.out.println(registration.getStudent().getName());
        System.out.println(student.getName());
     studentService.saveStudent(student);

        // Return the updated student with populated fields
        return student;
    }


    public Teacher addRegisterTeacher(Teacher teacher, Registration registration) {

        // Set Date :
        registration.setRegistrationDate(LocalDate.now());
        teacher.setHiringDate(registration.getRegistrationDate());

        // Set Balance to 0 :
        teacher.setBalance(0f);

        // Save & Add :
        registrationRepository.save(registration);
        registration.setTeacher(teacher);
        teacherService.saveTeacher(teacher);

        return teacher;
    }

    public List<Registration> findAllRegistrations() {
        return registrationRepository.findAll();
    }

    public List<Registration> findAllStudentRegistrations(){
        List<Registration> allRegistrations = registrationRepository.findAll();
        return allRegistrations.stream()
                .filter(registration -> registration.getStudent().getId() > 0)
                .toList();
    }

}
