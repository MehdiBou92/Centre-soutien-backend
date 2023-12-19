package Cours.Centre.services;

import java.time.Month;
import java.util.Map;

public interface StatsService {

    Integer getNumberOfActiveStudents();
    Map<String, Double> getTotalRevenuePerStudent();
    Map<Month,Double> getTotalRevenuePerMonth();
}
