package Cours.Centre.services;

import Cours.Centre.models.Payment;

import java.util.List;

public interface PaymentService {

    Payment addStudentPayement (Long studentId,Payment payment);
    Payment addTeacherPayment (Long teacherId,Payment payment);
    List<Payment> findAllPayments();

}
