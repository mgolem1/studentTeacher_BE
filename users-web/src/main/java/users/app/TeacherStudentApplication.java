package users.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import users.model.Student;

@SpringBootApplication
@EnableWebMvc
@Configuration
@EntityScan(basePackageClasses = Student.class)
@ComponentScan(basePackages = {"users"})
@EnableJpaRepositories(basePackages = "users.repository")
public class TeacherStudentApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeacherStudentApplication.class, args);
    }

}
