package Cours.Centre.models;

import Cours.Centre.models.enums.CoursName;
import Cours.Centre.services.TeacherService;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private CoursName coursName;
    private LocalDate courseRegisteryDate;
    private int price;
    @OneToMany
    private List<Session> sessions = new ArrayList<>();
    @ManyToOne
    private Teacher teacher;
}
