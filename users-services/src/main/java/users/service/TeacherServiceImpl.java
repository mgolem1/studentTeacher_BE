package users.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import users.dto.TeacherDTO;
import users.exceptions.AppError;
import users.exceptions.AppException;
import users.mapper.InterestMapper;
import users.mapper.TeacherMapper;
import users.model.*;
import users.repository.RoleRepository;
import users.repository.TeacherRepository;
import users.specification.TeacherSearchCriteria;
import users.specification.TeacherSearchSpecification;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    private final RoleRepository roleRepository;

    private final TeacherMapper teacherMapper;

    private final InterestMapper interestMapper;

    public TeacherServiceImpl(TeacherRepository teacherRepository, RoleRepository roleRepository, TeacherMapper teacherMapper, InterestMapper interestMapper) {
        this.roleRepository = roleRepository;
        this.teacherMapper = teacherMapper;
        this.teacherRepository = teacherRepository;
        this.interestMapper = interestMapper;
    }

    private boolean hasRole(String role) {
        Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>)
                SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        boolean hasRole = false;
        for (GrantedAuthority authority : authorities) {
            hasRole = authority.getAuthority().equals(role);
            if (hasRole) {
                break;
            }
        }
        return hasRole;
    }

    @Override
    public Page<TeacherDTO> teacherList(TeacherSearchCriteria criteria, Pageable pageable) throws AppException {
        if(hasRole("STUDENT")){
            throw new AppException(AppError.UNAUTHORIZED);
        }
        return teacherRepository.findAll(TeacherSearchSpecification.findTeacher(criteria), pageable).map(teacherMapper::toDTO);
    }

    @Override
    public TeacherDTO getTeacherById(Long id) throws AppException {

        if(hasRole("STUDENT")){
            throw new AppException(AppError.UNAUTHORIZED);
        }

        Optional<Teacher> teacherById = teacherRepository.findById(id);

        if (!teacherById.isPresent()) {
            throw new AppException(AppError.STUDENT_NOT_FOUND);
        }

        TeacherDTO teacherDTO = teacherMapper.toDTO(teacherRepository.save(teacherById.get()));
        return teacherDTO;
    }

    @Override
    public TeacherDTO createTeacher(TeacherDTO teacherDTO) throws AppException {
        if(!hasRole("STUDENT")){
            throw new AppException(AppError.UNAUTHORIZED);
        }

        Teacher teacher = teacherRepository.findById(Long.parseLong(teacherDTO.getId())).get();

        if (teacher==null) {
            throw new AppException(AppError.ALREADY_EXIST);
        }

        if (teacher.getFirstName() == null || teacher.getLastName() == null || teacher.getUsername()==null || teacher.getPassword()==null) {
            throw new AppException(AppError.BAD_REQUEST);
        }
        Role r=roleRepository.findByName("TEACHER");
        Set<Role>role=new HashSet<>();
        role.add(r);

        teacher.setFirstName(teacherDTO.getFirstName());
        teacher.setLastName(teacherDTO.getLastName());
        teacher.setUsername(teacherDTO.getUsername());
        teacher.setPassword(teacherDTO.getPassword());
        teacher.setRoles(role);

        teacherRepository.save(teacher);

        return teacherMapper.toDTO(teacher);
    }

    @Override
    public TeacherDTO updateTeacher(Long id, TeacherDTO teacherDTO) throws AppException {
        if(!hasRole("STUDENT") && !hasRole("ADMIN")){
            throw new AppException(AppError.UNAUTHORIZED);
        }

        Teacher teacher=teacherRepository.findById(id).get();

        if(teacher==null){
            throw new AppException(AppError.TEACHER_NOT_FOUND);
        }

        if(teacherDTO.getFirstName()!=null){
            teacher.setFirstName(teacherDTO.getFirstName());
        }
        if(teacherDTO.getLastName()!=null){
            teacher.setLastName(teacherDTO.getLastName());
        }
        if(teacherDTO.getNumberOfStudent()!=null){
            teacher.setNumberOfStudent(teacherDTO.getNumberOfStudent());
        }

        if(teacherDTO.getAddress()!=null){
            if(teacher.getAddress()==null){
                teacher.setAddress(new Address());
            }

            teacher.getAddress().setCity(teacherDTO.getAddress().getCity());
            teacher.getAddress().setStreet(teacherDTO.getAddress().getStreet());
            teacher.getAddress().setZipCode(teacherDTO.getAddress().getZipCode());
            teacher.getAddress().setStreetNumber(teacherDTO.getAddress().getStreetNumber());

        }



        if(teacherDTO.getInterest()!=null){
            Set<Interest> interest=teacherDTO.getInterest().stream().map(i->interestMapper.fromDTO(i)).collect(Collectors.toSet());
            teacher.setInterest(interest);
        }

        teacherRepository.save(teacher);

        TeacherDTO saveUserDTO = teacherMapper.toDTO(teacher);

        return saveUserDTO;
    }
}
