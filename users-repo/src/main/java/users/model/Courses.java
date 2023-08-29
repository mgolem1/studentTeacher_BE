package users.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Courses extends BaseEntity{

    private String name;

    @ManyToMany(mappedBy = "courses")
    //@JsonManagedReference(value = "courses")
    private Set<Teacher> teachers = new HashSet<>();
}
