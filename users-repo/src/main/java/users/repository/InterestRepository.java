package users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import users.model.Interest;

public interface InterestRepository extends JpaRepository<Interest, Long> {
}
