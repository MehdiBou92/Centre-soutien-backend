package Cours.Centre.serviceTests;

import Cours.Centre.models.Payment;
import Cours.Centre.models.Student;
import Cours.Centre.services.IMPL.StatsServiceImpl;
import Cours.Centre.services.PaymentService;
import Cours.Centre.services.StudentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatsServiceTest {

    @InjectMocks
    private StatsServiceImpl statsService;
    @Mock
    private StudentService studentService;
    @Mock
    private PaymentService paymentService;


    @Test
    public void givenMultipleStudentsWhenGetNumberOfActiveStudentsThenreturnActiveStudents(){

        Student student = new Student();
        student.setName("StudentTest");
        student.setActive(true);

        Student student2 = new Student();
        student2.setName("StudentTest2");
        student2.setActive(true);

        Student student3 = new Student();
        student3.setName("StudentTest3");
        student3.setActive(false);

        List<Student> studentList = List.of(student,student2,student3);
        when(studentService.findAllStudents()).thenReturn(studentList);

        Integer activeStudents = statsService.getNumberOfActiveStudents();

        Assertions.assertEquals(2,activeStudents);

    }

    @Test
    public void givenMultipleStudents_WhenGetTotalRevenuePerStudent_ThenReturnMapOfStudentAndRevenue(){


        Payment payment = new Payment();
        payment.setAmount(1000);

        Payment payment2 = new Payment();
        payment2.setAmount(100);

        Payment payment3 = new Payment();
        payment3.setAmount(500);

        Payment payment4 = new Payment();
        payment4.setAmount(600);

        Student student = new Student();
        student.setName("StudentTest");
        student.setActive(true);
        student.setPaiements(new ArrayList<>());
        student.getPaiements().add(payment);
        student.getPaiements().add(payment2);
        student.getPaiements().add(payment3);

        Student student2 = new Student();
        student2.setName("StudentTest2");
        student2.setActive(true);
        student2.setPaiements(new ArrayList<>());
        student2.getPaiements().add(payment4);

        List<Student> studentList = List.of(student,student2);
        when(studentService.findAllStudents()).thenReturn(studentList);

        Map<String,Double> revenuePerStudent = statsService.getTotalRevenuePerStudent();

        // LOG FOR DEBUG :
        revenuePerStudent.forEach((std, rev) -> System.out.println(std + " : " + rev ));


        Assertions.assertEquals(2,revenuePerStudent.size());
        Assertions.assertEquals(1600d,revenuePerStudent.get("StudentTest"));
        Assertions.assertEquals(600d,revenuePerStudent.get("StudentTest2"));

    }

    @Test
    public void givenMultiplePaymentsWhenGetTotalRevenuePerMonthThenReturnMapOfMonthAndRevenue(){

        Payment payment = new Payment();
        payment.setAmount(1000);
        payment.setPaymentDate(LocalDate.of(2023,10,10));

        Payment payment2 = new Payment();
        payment2.setAmount(100);
        payment2.setPaymentDate(LocalDate.of(2023,10,10));

        Payment payment3 = new Payment();
        payment3.setAmount(500);
        payment3.setPaymentDate(LocalDate.of(2023,9,10));

        Payment payment4 = new Payment();
        payment4.setAmount(600);
        payment4.setPaymentDate(LocalDate.of(2023,8,10));

        List<Payment> payments = List.of(payment,payment2,payment3,payment4);
        when(paymentService.findAllPayments()).thenReturn(payments);

        Map<Month,Double> revenuePerMonth = statsService.getTotalRevenuePerMonth();
        // logs for debug
        revenuePerMonth.forEach((m,r)-> System.out.println(m + " : " + r));

        Assertions.assertEquals(revenuePerMonth.get(Month.OCTOBER),1100);
        Assertions.assertEquals(revenuePerMonth.get(Month.SEPTEMBER),500);
        Assertions.assertEquals(revenuePerMonth.get(Month.AUGUST),600);

    }
}

