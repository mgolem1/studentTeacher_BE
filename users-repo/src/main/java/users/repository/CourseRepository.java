package users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import users.model.Courses;

public interface CourseRepository extends JpaRepository<Courses, Long> {
}
