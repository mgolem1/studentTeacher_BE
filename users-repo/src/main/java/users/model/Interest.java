package users.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Interest extends BaseEntity {

    private String name;

    @ManyToMany(mappedBy = "interest")
    private Set<Student> student;

    @ManyToMany(mappedBy = "interest")
    private Set<Teacher> teacher;
}
