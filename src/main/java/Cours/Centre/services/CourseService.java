package Cours.Centre.services;

import Cours.Centre.models.Course;
import Cours.Centre.models.Student;
import Cours.Centre.models.Teacher;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    Course saveCourse (Course course);
    Optional<Course> findCoursById(Long id);
    List<Course> findAllCourses();
    Student addCourseToStudent (Long id, Course course);
    Teacher addCourseToTeacher (Long id, Course course);
}
