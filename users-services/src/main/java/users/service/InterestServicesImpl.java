package users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import users.dto.InterestDTO;
import users.exceptions.AppException;
import users.mapper.InterestMapper;
import users.repository.InterestRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class InterestServicesImpl implements InterestServices{

    private final InterestRepository interestRepository;

    private final InterestMapper interestMapper;

    public InterestServicesImpl(InterestRepository interestRepository, InterestMapper interestMapper) {
        this.interestRepository = interestRepository;
        this.interestMapper = interestMapper;
    }

    @Override
    public List<InterestDTO> getInterest() throws AppException {
        return interestRepository.findAll().stream().map(interestMapper::interestToDTO).collect(Collectors.toList());
    }
}
