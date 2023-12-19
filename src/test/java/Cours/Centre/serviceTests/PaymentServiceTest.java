package Cours.Centre.serviceTests;

import Cours.Centre.dao.PaymentRepository;
import Cours.Centre.models.Payment;
import Cours.Centre.models.Student;
import Cours.Centre.models.Teacher;
import Cours.Centre.services.IMPL.PaymentServiceImpl;
import Cours.Centre.services.StudentService;
import Cours.Centre.services.TeacherService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @InjectMocks
    private PaymentServiceImpl paymentService;
    @Mock
    private StudentService studentService;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private TeacherService teacherService;


    @Test
    public void givenStudentIdAndPayment_WhenAddStudentPayement_ThenReturnValidPayment(){

        Student student = new Student();
        student.setId(1L);
        student.setName("StudentTest");
        student.setPaiements(new ArrayList<>());
        student.setBalance(150);

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(100);

        when(studentService.findStudentById(student.getId())).thenReturn(Optional.of(student));
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(studentService.saveStudent(student)).thenReturn(student);


        Payment validPayment = paymentService.addStudentPayement(student.getId(),payment);

        Assertions.assertNotNull(validPayment);
        Assertions.assertEquals(LocalDate.now(),validPayment.getPaymentDate());
        Assertions.assertEquals(1,student.getPaiements().size());
        Assertions.assertEquals(50,student.getBalance());
        Assertions.assertEquals(100,validPayment.getAmount());
    }

    @Test
    public void givenStudentIdAndPayment_WhenAddStudentPayement_ThenReturnStudentException() {

        Student student = new Student();
        student.setId(1L);
        student.setName("StudentTest");
        student.setPaiements(new ArrayList<>());
        student.setBalance(150);

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(100);

        when(studentService.findStudentById(student.getId())).thenThrow(new EntityNotFoundException("Student Not Found"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                paymentService.addStudentPayement(student.getId(), payment));

        Assertions.assertEquals("Student Not Found", exception.getMessage());
    }

    @Test
    public void givenStudentIdAndPayment_WhenAddStudentPayement_ThenReturnTeacherException() {

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(100);

        when(teacherService.findTeacherById(teacher.getId())).thenThrow(new EntityNotFoundException("Teacher Not Found"));

        EntityNotFoundException exception = Assertions.assertThrows(EntityNotFoundException.class, () ->
                    paymentService.addTeacherPayment(teacher.getId(), payment));

        Assertions.assertEquals("Teacher Not Found", exception.getMessage());
    }

    @Test
    public void givenStudentIdAndPayment_WhenAddStudentPayement_ThenReturnValidPaymentV2() {
        //GIVEN
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setName("TeacherTest");
        teacher.setBalance(400);
        teacher.setPaiements(new ArrayList<>());

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(100);

        when(teacherService.findTeacherById(teacher.getId())).thenReturn(Optional.of(teacher));
        when(paymentRepository.save(payment)).thenReturn(payment);
        when(teacherService.saveTeacher(teacher)).thenReturn(teacher);

        //WHEN

        Payment validTeacherPayment = paymentService.addTeacherPayment(teacher.getId(),payment);

        //THEN

        Assertions.assertNotNull(validTeacherPayment);
        Assertions.assertEquals(LocalDate.now(),validTeacherPayment.getPaymentDate());
        Assertions.assertEquals(1,teacher.getPaiements().size());
        Assertions.assertEquals(300,teacher.getBalance());

    }

    @Test
    public void givenMultiplesPaymentsWhenFindAllPaymentsThenReturnAllPayments(){

        Payment payment = new Payment();
        payment.setId(1L);
        payment.setAmount(100);

        Payment payment2 = new Payment();
        payment2.setId(2L);
        payment2.setAmount(200);

        List<Payment> payments = List.of(payment,payment2);
        when(paymentRepository.findAll()).thenReturn(payments);

        List<Payment> allPayments = paymentService.findAllPayments();

        Assertions.assertEquals(2,allPayments.size());
        Assertions.assertEquals(200,allPayments.get(1).getAmount());
    }

    }
