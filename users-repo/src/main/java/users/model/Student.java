package users.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "Students")
public class Student extends User {

    @ManyToMany
    @JoinTable(
            name = "student_interest",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id"))
    @JsonIgnore
    private Set<Interest> interest = new HashSet<>();

    private String department;

    @OneToOne
    @JoinColumn(name = "first_choice_TEACHER_id")
    private Teacher firstChoice;

    @OneToOne
    @JoinColumn(name = "second_choice_teacher_id")
    private Teacher secondChoice;

    @OneToOne
    @JoinColumn(name = "third_choice_teacher_id")
    private Teacher thirdChoice;

    @ManyToOne
    @JoinColumn(name = "mentor_id")
    private Teacher mentor;

    private Double finalGrade;

    private String academicPaper;

}