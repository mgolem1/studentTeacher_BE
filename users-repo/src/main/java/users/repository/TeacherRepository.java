package users.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import users.model.Teacher;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    Page<Teacher> findAll(Specification<Teacher> specification, Pageable pageable);

    Optional<Teacher> findById(Long id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE TEACHERS SET TEACHERS.STUDENTS_FOR_MENTORING=:studentId WHERE TEACHERS.ID=:teacherId", nativeQuery = true)
    void updateStudentMentor(@Param("teacherId") Long teacherId, @Param("studentId") Long studentId);

}
