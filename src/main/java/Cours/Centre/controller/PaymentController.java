package Cours.Centre.controller;

import Cours.Centre.models.Payment;
import Cours.Centre.models.Student;
import Cours.Centre.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/student/{id}/pay")
    public ResponseEntity<Payment> makeStudentPayment (@PathVariable Long id, Payment payment){
        Payment savedPayement = paymentService.addStudentPayement(id,payment);
        return ResponseEntity.ok(savedPayement);
    }
}
