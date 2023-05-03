package users.dto;

import lombok.*;

@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchStudentTeacherDTO {

    private StudentDTO student;

    private TeacherDTO teacher;
}
