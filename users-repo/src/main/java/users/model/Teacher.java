package users.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@PrimaryKeyJoinColumn(name = "user_id")
@Table(name = "Teachers")
public class Teacher extends User {

    @ManyToMany
    @JoinTable(
            name = "teacher_interest",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "interest_id")
    )
    @JsonIgnore
    private Set<Interest> interest = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "course_teacher",
            joinColumns = @JoinColumn(name = "teacher_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonIgnore
    private Set<Courses> courses = new HashSet<>();

    private Integer numberOfStudent;

    @OneToMany(mappedBy = "mentor", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Student> studentsForMentoring = new HashSet<>();

}
