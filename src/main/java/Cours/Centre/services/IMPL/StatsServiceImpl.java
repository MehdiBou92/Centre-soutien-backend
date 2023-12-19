package Cours.Centre.services.IMPL;

import Cours.Centre.models.Payment;
import Cours.Centre.models.Student;
import Cours.Centre.services.PaymentService;
import Cours.Centre.services.StatsService;
import Cours.Centre.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final StudentService studentService;
    private final PaymentService paymentService;


    public Integer getNumberOfActiveStudents() {
        // Retreive all students :
        List<Student> students = studentService.findAllStudents();

        // Filter Active Students :
        List<Student> activeStudents = students.stream()
                .filter(Student::isActive)
                .toList();

        return activeStudents.size();
    }


    public Map<String, Double> getTotalRevenuePerStudent() {

        // Retreive all students :
        List<Student> students = studentService.findAllStudents();
        Map<String, Double> revenuePerStudent = new HashMap<>();

       for(Student student : students){

           // Get the student :
           String studentName = student.getName();

           // Get sum of Paiements :
           List<Payment> paymentsForStudent = student.getPaiements();
           Double totalPaymentsPerStudent = paymentsForStudent.stream()
                   .mapToDouble(Payment::getAmount)
                   .sum();
           // Attach the elements to the Map :
           revenuePerStudent.put(studentName,totalPaymentsPerStudent);
       }
       return revenuePerStudent;
    }

    public Map<Month, Double> getTotalRevenuePerMonth() {
        // Initiate the Map:
        Map<Month, Double> revenuePerMonth = new HashMap<>();

        // total Revenue:
        List<Payment> payments = paymentService.findAllPayments();

        // Get the Month & Total Revenu:
        for (Payment payment : payments) {
            Month monthPayment = Month.of(payment.getPaymentDate().getMonth().getValue());

            // Check if the map already contains an entry for the month
            // If yes, add the payment amount to the existing total
            // If no, initialize the total with the current payment amount (merge)
            revenuePerMonth.merge(monthPayment, (double) payment.getAmount(), Double::sum);
        }

        return revenuePerMonth;
    }





}
