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

    @OneToOne(cascade = CascadeType.MERGE)
    private Teacher firstChoice;

    @OneToOne(cascade = CascadeType.MERGE)
    private Teacher secondChoice;

    @OneToOne(cascade = CascadeType.MERGE)
    private Teacher thirdChoice;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "mentor_id")
    @JsonIgnore
    private Teacher mentor;

    private Double finalGrade;

    private String academicPaper;

}