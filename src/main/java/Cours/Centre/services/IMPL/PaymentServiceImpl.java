package Cours.Centre.services.IMPL;

import Cours.Centre.dao.PaymentRepository;
import Cours.Centre.models.Payment;
import Cours.Centre.models.Student;
import Cours.Centre.models.Teacher;
import Cours.Centre.services.PaymentService;
import Cours.Centre.services.StudentService;
import Cours.Centre.services.TeacherService;
import ch.qos.logback.core.net.SyslogOutputStream;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final StudentService studentService;
    private final PaymentRepository paymentRepository;
    private final TeacherService teacherService;

    @Transactional
    public Payment addStudentPayement(Long studentId, Payment payment) {
        // Student can make advance payments also, we won't be checking the balance
        // Retreive the student By Id :
        Student optionalStudent = studentService.findStudentById(studentId)
                .orElseThrow(()-> new EntityNotFoundException("Student Not Found"));

        // Set the paymentDate :
        payment.setPaymentDate(LocalDate.now());

        // Update the student Balance :
        System.out.println(payment.getAmount());
        float newBalance = optionalStudent.getBalance() - payment.getAmount();
        optionalStudent.setBalance(newBalance);

        // Save & Add :
        paymentRepository.save(payment);
        optionalStudent.getPaiements().add(payment);
        studentService.saveStudent(optionalStudent);

        return payment;
    }

    @Transactional
    public Payment addTeacherPayment(Long teacherId, Payment payment) {
        // Retreive the teacher :
        Teacher optionalTeacher = teacherService.findTeacherById(teacherId)
                .orElseThrow(()-> new EntityNotFoundException("Teacher Not Found !"));

        // Check Teacher Balance :
        if(optionalTeacher.getBalance()>0){

            // PayementDate :
            payment.setPaymentDate(LocalDate.now());

            // Update the Balance :
            float newBalance = optionalTeacher.getBalance() - payment.getAmount();
            optionalTeacher.setBalance(newBalance);

            // Save & Add :
            paymentRepository.save(payment);
            optionalTeacher.getPaiements().add(payment);
            teacherService.saveTeacher(optionalTeacher);
        }

        return payment;
    }


    public List<Payment> findAllPayments() {
        return paymentRepository.findAll();
    }


}
