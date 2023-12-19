package Cours.Centre.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private LocalDate registrationDate;
    private String grade;
    private float amount;
    private String phoneNumber;
    private float balance;
    private boolean active;
    @OneToMany
    private List<Course> courses;
    @OneToMany
    private List<Payment> paiements;





}
