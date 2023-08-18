package users.app.rest.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import users.dto.InterestDTO;
import users.exceptions.AppException;
import users.service.InterestServices;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"}, allowCredentials = "true")
@RequestMapping("/api/interest")
public class InterestController {

    private final InterestServices interestServices;

    public InterestController(InterestServices interestServices) {
        this.interestServices = interestServices;
    }

    @GetMapping
    public List<InterestDTO> getInterest() throws AppException {
        return interestServices.getInterest();
    }
}
