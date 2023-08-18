package users.service;

import org.springframework.stereotype.Service;
import users.dto.InterestDTO;
import users.exceptions.AppException;

import java.util.List;

@Service
public interface InterestServices {

    List<InterestDTO> getInterest() throws AppException;

}
