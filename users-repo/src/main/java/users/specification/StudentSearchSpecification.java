package users.specification;

import org.springframework.data.jpa.domain.Specification;
import users.model.Student;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class StudentSearchSpecification {
    public static Specification<Student> findUsers(final StudentSearchCriteria criteria) {

        return (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> restrictions = new ArrayList<>();
            String firstName = criteria.getFirstName();
            String lastName = criteria.getLastName();
            String odjel = criteria.getOdjel();
            String interes = criteria.getInterest();

            if (firstName != null && !firstName.trim().isEmpty()) {
                restrictions.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + firstName.toLowerCase() + "%")
                );
            }

            if (lastName != null && !lastName.trim().isEmpty()) {
                restrictions.add(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + lastName.toLowerCase() + "%")
                );
            }

            criteriaQuery.distinct(true);

            return criteriaBuilder.and(restrictions.toArray(new Predicate[0]));
        };
    }
}
