package Cours.Centre.services;

import Cours.Centre.models.Course;
import Cours.Centre.models.Session;

public interface SessionService {

    Course addSession (Long teacherId, Long courseId, Session session);
    Session saveSession (Session session);
}
